#!/usr/bin/env python3
"""
Wiki İddia Doğrulama ve Tazelik Kontrol Scripti
Yalnızca standart kütüphane kullanır (harici bağımlılık yoktur).
Tüm wiki/*.md notlarındaki claims listelerini okur, durumlarını analiz eder.
"""

import os
import glob
import re
import sys
from datetime import date

def parse_frontmatter_claims(content):
    fm_match = re.match(r'^---\s*\n(.*?)\n---\s*\n', content, re.DOTALL)
    if not fm_match:
        return []
    fm = fm_match.group(1)
    
    claims_idx = fm.find('claims:')
    if claims_idx == -1:
        return []
    claims_text = fm[claims_idx + len('claims:'):]
    
    items = re.split(r'\n\s*-\s+id:\s*', claims_text)
    claims = []
    for item in items:
        if not item.strip():
            continue
        lines = item.strip().split('\n')
        cid = lines[0].strip().strip('\"\'')
        c = {'id': cid}
        for line in lines[1:]:
            m = re.match(r'\s*([a-zA-Z0-9_]+):\s*(.*)', line)
            if m:
                k = m.group(1)
                v = m.group(2).strip()
                if v in ('null', '~', ''):
                    v = None
                elif (v.startswith('"') and v.endswith('"')) or (v.startswith("'") and v.endswith("'")):
                    v = v[1:-1]
                c[k] = v
        claims.append(c)
    return claims

def load_claims(target_dir='wiki'):
    wiki_files = sorted(glob.glob(os.path.join(target_dir, '*.md')))
    all_claims = []
    
    for fpath in wiki_files:
        if os.path.basename(fpath) == 'README.md':
            continue
        with open(fpath, 'r', encoding='utf-8') as f:
            content = f.read()
        
        claims = parse_frontmatter_claims(content)
        for c in claims:
            c['file'] = fpath
            all_claims.append(c)
            
    return all_claims

def main():
    target_dir = sys.argv[1] if len(sys.argv) > 1 else 'wiki'
    today = date.today().isoformat()
    claims = load_claims(target_dir)
    
    total = len(claims)
    kontrol_edilen = 0
    celiski = 0
    proje_karari = 0
    inferred = 0
    unverified = 0
    bayat = 0
    
    print(f"=== WIKI İDDİA (CLAIM) DOĞRULAMA DURUMU ({today}) ===")
    print(f"{'İddia ID':<35} | {'Güven / Durum':<25} | {'verified_at':<12} | {'Bulunduğu Not'}")
    print("-" * 105)
    
    for c in claims:
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
            
        # Çelişki kontrolü: VERIFIED ama source_url yok
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
    print(f"Kontrol edilen iddia: {kontrol_edilen} / toplam: {total}")
    print(f"Resmî Kaynaklı ve Güncel (VERIFIED): {kontrol_edilen}")
    print(f"Dahili Karar (PROJE_KARARI): {proje_karari}")
    print(f"Mantıksal Çıkarım (INFERRED): {inferred}")
    print(f"Doğrulanmamış (UNVERIFIED): {unverified}")
    print(f"Bayat İddia Sayısı: {bayat}")
    print(f"Çelişki Sayısı (Kaynaksız VERIFIED): {celiski}")
    
    if celiski > 0:
        print(f"\nHATA: {celiski} adet iddiada kaynak olmadan VERIFIED güven düzeyi atanmış (ÇELİŞKİ).")
        sys.exit(1)
        
    sys.exit(0)

if __name__ == '__main__':
    main()
