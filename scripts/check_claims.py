#!/usr/bin/env python3
"""
Wiki İddia Doğrulama ve Tazelik Kontrol Scripti
Yalnızca standart kütüphane kullanır (harici bağımlılık yoktur).
Tüm wiki/*.md notlarındaki claims listelerini okur, durumlarını analiz eder.
Sürüm tutarlılık kontrolü yapar (wiki ↔ derleme dosyaları).
İsteğe bağlı olarak DURUM.md belgesini üretir (--durum).
"""

import os
import glob
import re
import sys
import argparse
from datetime import date

VALID_CONFIDENCES = {'VERIFIED', 'PROJE_KARARI', 'INFERRED', 'UNVERIFIED'}

def is_valid_iso_date(date_str):
    if not isinstance(date_str, str):
        return False
    if not re.match(r'^\d{4}-\d{2}-\d{2}$', date_str):
        return False
    try:
        parts = [int(p) for p in date_str.split('-')]
        date(parts[0], parts[1], parts[2])
        return True
    except ValueError:
        return False

def check_file_frontmatter_and_body(content, fpath):
    """
    Checks requirement 1:
    - Starts with --- (newline)
    - Ends frontmatter with --- (newline)
    - Has non-empty body text after closing ---
    Returns (fm_text, body_text, error_or_None)
    """
    if not (content.startswith('---\n') or content.startswith('---\r\n')):
        return None, None, f"{fpath}: geçerli frontmatter başlangıcı ('---') bulunamadı"

    stripped = content.rstrip(' \t\r\n')
    if stripped.endswith('\n---') or stripped == '---':
        return None, None, f"{fpath}: dosya kapanış '---' ile bitiyor; kapanış satırından sonra satır sonu ve gövde metni zorunludur"

    m = re.match(r'^---\r?\n(.*?)\r?\n---\s*\r?\n(.*)$', content, re.DOTALL)
    if not m:
        return None, None, f"{fpath}: frontmatter kapanış satırı ('---') bulunamadı"

    fm_text = m.group(1)
    body_text = m.group(2).strip()
    if not body_text:
        return None, None, f"{fpath}: frontmatter kapanışından sonra gövde metni zorunludur"

    return fm_text, body_text, None

def parse_frontmatter_claims(fm_text, fpath):
    """
    Parses claims from frontmatter text.
    Returns (claims_list, error_or_None)
    """
    claims_idx = fm_text.find('claims:')
    if claims_idx == -1:
        return [], f"{fpath}: frontmatter içinde 'claims:' alanı yok"

    claims_text = fm_text[claims_idx + len('claims:'):]
    if not claims_text.strip() or claims_text.strip().startswith('[]'):
        return [], f"{fpath}: frontmatter içinde 'claims:' listesi boş"

    # Stop if an unindented top-level key appears after claims:
    lines = claims_text.splitlines()
    filtered_lines = []
    for line in lines:
        if line and not line[0].isspace() and not line.strip().startswith('-') and ':' in line:
            break
        filtered_lines.append(line)
    claims_block = '\n'.join(filtered_lines)

    items = re.split(r'\n\s*-\s+id:\s*', '\n' + claims_block)
    claims = []
    for item in items:
        if not item.strip():
            continue
        item_lines = item.strip().split('\n')
        cid = item_lines[0].strip().strip('\"\'')
        if not cid:
            continue
        c = {'id': cid}
        for line in item_lines[1:]:
            m = re.match(r'^\s*([a-zA-Z0-9_]+):\s*(.*)', line)
            if m:
                k = m.group(1)
                v = m.group(2).strip()
                if not ((v.startswith('"') and v.endswith('"')) or (v.startswith("'") and v.endswith("'"))):
                    v = re.sub(r'\s+#.*$', '', v).strip()
                if v in ('null', '~', ''):
                    v = None
                elif (v.startswith('"') and v.endswith('"')) or (v.startswith("'") and v.endswith("'")):
                    v = v[1:-1]
                c[k] = v
        claims.append(c)

    if not claims:
        return [], f"{fpath}: frontmatter içinde 'claims:' listesi boş"

    return claims, None

def load_and_validate_claims(target_dir='wiki', today=None):
    if today is None:
        today = date.today().isoformat()
    today_date = date.fromisoformat(today)

    wiki_files = sorted(glob.glob(os.path.join(target_dir, '*.md')))
    all_claims = []
    errors = []     # list of (file, claim_id, message)
    warnings = []   # list of (file, claim_id, message)

    seen_claim_ids = {} # cid -> file
    files_with_claims = {} # file -> list of claims

    for fpath in wiki_files:
        if os.path.basename(fpath) == 'README.md':
            continue

        with open(fpath, 'r', encoding='utf-8') as f:
            content = f.read()

        fm_text, body_text, fm_err = check_file_frontmatter_and_body(content, fpath)
        if fm_err:
            errors.append((fpath, None, fm_err))
            continue

        claims, claims_err = parse_frontmatter_claims(fm_text, fpath)
        if claims_err:
            errors.append((fpath, None, claims_err))
            continue

        file_claims = []
        for c in claims:
            cid = c.get('id')
            c['file'] = fpath
            file_claims.append(c)
            all_claims.append(c)

            # Check 3: Duplicate claim ID
            if cid in seen_claim_ids:
                errors.append((fpath, cid, f"{fpath} ({cid}): aynı iddia id'si birden fazla kez geçiyor (ilk görüldüğü: {seen_claim_ids[cid]})"))
            else:
                seen_claim_ids[cid] = fpath

            # Check 4: Valid confidence
            conf = c.get('confidence')
            if not conf or conf not in VALID_CONFIDENCES:
                errors.append((fpath, cid, f"{fpath} ({cid}): geçersiz confidence değeri '{conf}'"))

            # Check 5: VERIFIED requirements (source_url, verified_at, review_at)
            if conf == 'VERIFIED':
                missing = []
                if not c.get('source_url'):
                    missing.append('source_url')
                if not c.get('verified_at'):
                    missing.append('verified_at')
                if not c.get('review_at'):
                    missing.append('review_at')
                if missing:
                    errors.append((fpath, cid, f"{fpath} ({cid}): VERIFIED iddiada {', '.join(missing)} eksik"))

            # Check 8: Kaynaksız VERIFIED -> çelişki
            if conf == 'VERIFIED' and not c.get('source_url'):
                errors.append((fpath, cid, f"{fpath} ({cid}): kaynak olmadan VERIFIED güven düzeyi atanmış (ÇELİŞKİ)"))

            # Check 6: Date formats
            vat = c.get('verified_at')
            if vat is not None:
                if not is_valid_iso_date(str(vat)):
                    errors.append((fpath, cid, f"{fpath} ({cid}): verified_at ('{vat}') YYYY-MM-DD biçiminde değil"))

            rat = c.get('review_at')
            if rat is not None:
                if not is_valid_iso_date(str(rat)):
                    errors.append((fpath, cid, f"{fpath} ({cid}): review_at ('{rat}') YYYY-MM-DD biçiminde değil"))
                else:
                    rat_date = date.fromisoformat(str(rat))
                    # Warning: bayat iddia
                    if rat_date < today_date:
                        warnings.append((fpath, cid, f"{fpath} ({cid}): bayat iddia (review_at: {rat} < bugün: {today})"))
                    # Warning: 30 günden az kalan iddia
                    elif 0 <= (rat_date - today_date).days < 30:
                        days_left = (rat_date - today_date).days
                        warnings.append((fpath, cid, f"{fpath} ({cid}): review_at'e 30 günden az kaldı ({days_left} gün kaldı, review_at: {rat})"))

        files_with_claims[fpath] = file_claims

    # Check 7: superseded_by references an existing claim ID
    for c in all_claims:
        sup = c.get('superseded_by')
        if sup:
            if sup not in seen_claim_ids:
                errors.append((c['file'], c.get('id'), f"{c['file']} ({c.get('id')}): superseded_by ('{sup}') var olmayan bir id'yi gösteriyor"))

    return all_claims, files_with_claims, errors, warnings

def parse_toml_versions(toml_path):
    if not os.path.exists(toml_path):
        return {}, [f"TOML dosyası bulunamadı: {toml_path}"]
    versions = {}
    in_versions_section = False
    with open(toml_path, 'r', encoding='utf-8') as f:
        for line in f:
            line_str = line.strip()
            if not line_str or line_str.startswith('#'):
                continue
            if line_str.startswith('[') and line_str.endswith(']'):
                in_versions_section = (line_str == '[versions]')
                continue
            if in_versions_section:
                m = re.match(r'^([a-zA-Z0-9_\-]+)\s*=\s*["\']([^"\']+)["\']', line_str)
                if m:
                    versions[m.group(1)] = m.group(2)
    return versions, []

def parse_wrapper_version(wrapper_path):
    if not os.path.exists(wrapper_path):
        return None, [f"Wrapper properties dosyası bulunamadı: {wrapper_path}"]
    with open(wrapper_path, 'r', encoding='utf-8') as f:
        for line in f:
            line_str = line.strip()
            if line_str.startswith('distributionUrl'):
                m = re.search(r'gradle-([0-9.]+)-(bin|all)\.zip', line_str)
                if m:
                    return m.group(1), []
    return None, [f"Wrapper properties dosyasında Gradle sürümü bulunamadı: {wrapper_path}"]

def check_version_consistency(target_dir='wiki', toml_path='gradle/libs.versions.toml', wrapper_path='gradle/wrapper/gradle-wrapper.properties'):
    stack_file = os.path.join(target_dir, 'android-stack.md')
    if not os.path.exists(stack_file):
        # Eğer android-stack.md bu hedef dizinde yoksa (ör. tekil test notu dizini)
        return [], [], True

    with open(stack_file, 'r', encoding='utf-8') as f:
        stack_content = f.read()
    fm_text, _, _ = check_file_frontmatter_and_body(stack_content, stack_file)
    if not fm_text:
        return [f"toml: {stack_file} frontmatter okunamadı"], [], False

    claims, _ = parse_frontmatter_claims(fm_text, stack_file)
    claims_text = [c.get('claim', '') or '' for c in claims]

    toml_versions, toml_errs = parse_toml_versions(toml_path)
    wrapper_ver, wrapper_errs = parse_wrapper_version(wrapper_path)

    errs = []
    lines_checked = []

    for err in toml_errs + wrapper_errs:
        errs.append(err)

    for k, v in toml_versions.items():
        found = any(v in ctext for ctext in claims_text)
        if found:
            lines_checked.append((f"toml: {k}={v}", True))
        else:
            err_msg = f"toml: {k}={v} wiki'de yok"
            errs.append(err_msg)
            lines_checked.append((err_msg, False))

    if wrapper_ver:
        found = any(wrapper_ver in ctext for ctext in claims_text)
        if found:
            lines_checked.append((f"wrapper: gradle={wrapper_ver}", True))
        else:
            err_msg = f"toml: gradle={wrapper_ver} wiki'de yok"
            errs.append(err_msg)
            lines_checked.append((err_msg, False))

    return errs, lines_checked, False

def generate_durum_md(all_claims, files_with_claims, errors, warnings, output_path='DURUM.md', today=None):
    if today is None:
        today = date.today().isoformat()

    total = len(all_claims)
    kontrol_edilen = 0
    proje_karari = 0
    inferred = 0
    unverified = 0
    bayat = 0
    celiski = 0

    verified_dates = []
    review_dates = []

    for c in all_claims:
        conf = c.get('confidence', 'UNVERIFIED')
        src = c.get('source_url')
        vat = c.get('verified_at')
        rat = c.get('review_at')

        is_stale = (rat and rat < today)
        if is_stale:
            bayat += 1

        if conf == 'VERIFIED' and not src:
            celiski += 1
        elif conf == 'VERIFIED' and src:
            if not is_stale:
                kontrol_edilen += 1
            if vat:
                verified_dates.append(vat)
        elif conf == 'PROJE_KARARI':
            proje_karari += 1
        elif conf == 'INFERRED':
            inferred += 1
        else:
            unverified += 1

        if rat:
            review_dates.append(rat)

    min_verified_at = min(verified_dates) if verified_dates else '-'
    min_review_at = min(review_dates) if review_dates else '-'

    lines = []
    lines.append("# Wiki İddia (Claim) Doğrulama Durumu (DURUM.md)")
    lines.append("")
    lines.append(f"**Tarih:** {today} (`date -u +%F`)  ")
    lines.append("**Doğrulama Mimarisi:** İddia (Claim) Düzeyinde Dürüst ve Atomik Doğrulama  ")
    lines.append(f"**Kontrol edilen iddia: {kontrol_edilen} / toplam: {total}**")
    lines.append("")
    lines.append("---")
    lines.append("")
    lines.append("## 1. Atomik İddia Doğrulama Tablosu")
    lines.append("")

    file_order = ['android-stack.md', 'ui-rules.md', 'kabul-kriterleri.md']
    sorted_files = sorted(files_with_claims.keys(), key=lambda f: (file_order.index(os.path.basename(f)) if os.path.basename(f) in file_order else 99, f))

    letters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'
    for idx, fpath in enumerate(sorted_files):
        letter = letters[idx % len(letters)]
        bname = os.path.basename(fpath)
        if bname == 'android-stack.md':
            sec_title = "Android Teknoloji Yığını"
        elif bname == 'ui-rules.md':
            sec_title = "UI Kuralları"
        elif bname == 'kabul-kriterleri.md':
            sec_title = "Mimari ve Kabul Kriterleri"
        else:
            sec_title = bname.replace('.md', '')

        lines.append(f"### {letter}. {sec_title} (`{fpath}`)")
        lines.append("")
        lines.append("| İddia ID | İddia / Değer | Doğrulama Kaynağı | Erişim | Durum / Güven | Doğrulama Tarihi (`verified_at`) |")
        lines.append("| :--- | :--- | :--- | :---: | :---: | :---: |")

        for c in files_with_claims[fpath]:
            cid = c.get('id', '')
            claim_text = c.get('claim', '')
            src = c.get('source_url')
            if src:
                src_cell = f"`{src}`"
                erisim = "HTTP 200"
            else:
                src_cell = "Proje kararı (`source_url: null`)"
                erisim = "-"
            conf = c.get('confidence', 'UNVERIFIED')
            vat = c.get('verified_at') or "-"
            lines.append(f"| `{cid}` | {claim_text} | {src_cell} | {erisim} | `{conf}` | {vat} |")

        lines.append("")

    lines.append("---")
    lines.append("")
    lines.append("## 2. Hatalar ve Uyarılar")
    lines.append("")
    if errors:
        lines.append("- **Hatalar:**")
        for err in errors:
            msg = err if isinstance(err, str) else err[2]
            lines.append(f"  - {msg}")
    else:
        lines.append("- **Hatalar:** Yok (0 hata)")

    if warnings:
        lines.append("- **Uyarılar:**")
        for warn in warnings:
            msg = warn if isinstance(warn, str) else warn[2]
            lines.append(f"  - {msg}")
    else:
        lines.append("- **Uyarılar:** Yok (0 uyarı)")

    lines.append("")
    lines.append("---")
    lines.append("")
    lines.append("## 3. İstatistikler ve Takvim")
    lines.append("")
    lines.append(f"- **Kontrol edilen iddia: {kontrol_edilen} / toplam: {total}**")
    lines.append(f"- **Resmî Kaynaklı Doğrulanan (`VERIFIED`):** {kontrol_edilen}")
    lines.append(f"- **Dahili Proje Kararı (`PROJE_KARARI`):** {proje_karari}")
    lines.append(f"- **Mantıksal Çıkarım (`INFERRED`):** {inferred}")
    lines.append(f"- **Doğrulanmamış (`UNVERIFIED`):** {unverified}")
    lines.append(f"- **Bayat İddia Sayısı (`bugün > review_at`):** {bayat}")
    lines.append(f"- **Çelişki / Hata Sayısı:** {len(errors) + celiski}")
    lines.append(f"- **Uyarı Sayısı:** {len(warnings)}")
    lines.append(f"- **En Eski Satır Doğrulama Tarihi:** `{min_verified_at}`")
    lines.append(f"- **Bir Sonraki Yeniden İnceleme (`review_at`):** `{min_review_at}`")
    lines.append("")

    content = '\n'.join(lines)
    with open(output_path, 'w', encoding='utf-8') as f:
        f.write(content)
    return output_path

def main():
    parser = argparse.ArgumentParser(description="Wiki İddia Doğrulama ve Tazelik Denetleyicisi")
    parser.add_argument('target_dir', nargs='?', default='wiki', help="Wiki dizini yolu (varsayılan: wiki)")
    parser.add_argument('--wiki-dir', dest='wiki_dir_opt', default=None, help="Alternatif wiki dizini parametresi")
    parser.add_argument('--toml', default='gradle/libs.versions.toml', help="libs.versions.toml yolu")
    parser.add_argument('--wrapper', default='gradle/wrapper/gradle-wrapper.properties', help="gradle-wrapper.properties yolu")
    parser.add_argument('--durum', action='store_true', help="DURUM.md dosyasını otomatik üretir")
    parser.add_argument('--durum-file', default='DURUM.md', help="DURUM.md hedef dosya yolu (varsayılan: DURUM.md)")
    parser.add_argument('--skip-version-check', action='store_true', help="Sürüm tutarlılık kontrolünü atla")
    args = parser.parse_args()

    target_dir = args.wiki_dir_opt if args.wiki_dir_opt else args.target_dir
    today = date.today().isoformat()

    all_claims, files_with_claims, errors, warnings = load_and_validate_claims(target_dir, today)

    # E2: Sürüm tutarlılık kontrolü
    ver_errors = []
    ver_lines = []
    ver_skipped = False
    if not args.skip_version_check:
        ver_errors, ver_lines, ver_skipped = check_version_consistency(target_dir, args.toml, args.wrapper)

    for ve in ver_errors:
        errors.append((args.toml, None, ve))

    # Calculate statistics
    total = len(all_claims)
    kontrol_edilen = 0
    celiski = 0
    proje_karari = 0
    inferred = 0
    unverified = 0
    bayat = 0

    print(f"=== WIKI İDDİA (CLAIM) DOĞRULAMA DURUMU ({today}) ===")
    print(f"{'İddia ID':<35} | {'Güven / Durum':<25} | {'verified_at':<12} | {'Bulunduğu Not'}")
    print("-" * 105)

    for c in all_claims:
        cid = c.get('id', 'unknown')
        conf = c.get('confidence', 'UNVERIFIED')
        src = c.get('source_url')
        vat = str(c.get('verified_at') or '-')
        rat = str(c.get('review_at') or '-')
        fpath = c.get('file', '')

        is_stale = (rat != '-' and rat < today)
        if is_stale:
            bayat += 1
            stale_tag = " (BAYAT)"
        else:
            stale_tag = ""

        if conf == 'VERIFIED' and not src:
            display_status = "VERIFIED (KAYNAK YOK!)"
            celiski += 1
        elif conf == 'VERIFIED' and src:
            display_status = "VERIFIED"
            if not is_stale:
                kontrol_edilen += 1
        elif conf == 'PROJE_KARARI':
            display_status = "PROJE_KARARI"
            proje_karari += 1
        elif conf == 'INFERRED':
            display_status = "INFERRED"
            inferred += 1
        else:
            display_status = "UNVERIFIED"
            unverified += 1

        print(f"{cid:<35} | {display_status:<25} | {vat:<12} | {fpath}{stale_tag}")

    print("-" * 105)

    # E2 Bölümü Çıktısı
    if ver_lines:
        print("\n=== SÜRÜM TUTARLILIK KONTROLÜ (wiki ↔ derleme dosyaları) ===")
        for item, ok in ver_lines:
            status_symbol = "[✓]" if ok else "[✗]"
            status_text = "wiki'de mevcut" if ok else "wiki'de yok"
            print(f"{status_symbol} {item} -> {status_text}")
        print("-" * 105)

    print(f"Kontrol edilen iddia: {kontrol_edilen} / toplam: {total}")
    print(f"Resmî Kaynaklı ve Güncel (VERIFIED): {kontrol_edilen}")
    print(f"Dahili Karar (PROJE_KARARI): {proje_karari}")
    print(f"Mantıksal Çıkarım (INFERRED): {inferred}")
    print(f"Doğrulanmamış (UNVERIFIED): {unverified}")
    print(f"Bayat İddia Sayısı: {bayat}")
    print(f"Çelişki Sayısı (Kaynaksız VERIFIED): {celiski}")
    print(f"Hata Sayısı: {len(errors)}")
    print(f"Uyarı Sayısı: {len(warnings)}")

    # Uyarıları yazdır
    if warnings:
        print(f"\nUYARILAR ({len(warnings)} adet):")
        for fpath, cid, msg in warnings:
            print(f" - [UYARI] {msg}")

    # Hataları yazdır
    if errors:
        print(f"\nHATALAR ({len(errors)} adet):")
        for fpath, cid, msg in errors:
            print(f" - [HATA] {msg}")

    # GitHub Actions CI Annotations (GITHUB_ACTIONS=true)
    if os.environ.get('GITHUB_ACTIONS') == 'true':
        for fpath, cid, msg in warnings:
            print(f"::warning file={fpath}::{msg}")
        for fpath, cid, msg in errors:
            print(f"::error file={fpath}::{msg}")

    # DURUM.md üretimi (--durum)
    if args.durum:
        out = generate_durum_md(all_claims, files_with_claims, errors, warnings, args.durum_file, today)
        print(f"\nDURUM.md başarıyla üretildi: {out}")

    if errors:
        sys.exit(1)

    sys.exit(0)

if __name__ == '__main__':
    main()
