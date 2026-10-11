#!/usr/bin/env python3
"""
Wiki İddia Doğrulama ve Tazelik Kontrol Scripti
Yalnızca standart kütüphane kullanır (harici bağımlılık yoktur).
Tüm wiki/*.md notlarındaki claims listelerini okur, durumlarını analiz eder.
"""

import os
import glob
import re
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

def load_claims():
    wiki_files = sorted(glob.glob('wiki/*.md'))
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
    today = date.today().isoformat()
    claims = load_claims()
    
    total = len(claims)
    kontrol_edilen = 0
    proje_karari = 0
    inferred = 0
    unverified = 0
    bayat = 0
    
    print(f"=== WIKI İDDİA (CLAIM) DOĞRULAMA DURUMU ({today}) ===")
    print(f"{'İddia ID':<35} | {'Güven / Durum':<20} | {'verified_at':<12} | {'Bulunduğu Not'}")
    print("-" * 100)
    
    for c in claims:
        cid = c.get('id', 'unknown')
        conf = c.get('confidence', 'UNVERIFIED')
        src = c.get('source_url')
        vat = str(c.get('verified_at') or '-')
        rat = str(c.get('review_at') or '-')
        fpath = c.get('file', '')
        
        if rat != '-' and rat < today:
            is_stale = " (BAYAT)"
            bayat += 1
        else:
            is_stale = ""
            
        if conf == 'VERIFIED' and src:
            kontrol_edilen += 1
        elif conf == 'PROJE_KARARI':
            proje_karari += 1
        elif conf == 'INFERRED':
            inferred += 1
        else:
            unverified += 1
            
        print(f"{cid:<35} | {conf:<20} | {vat:<12} | {fpath}{is_stale}")
        
    print("-" * 100)
    print(f"Kontrol edilen iddia: {kontrol_edilen} / toplam: {total}")
    print(f"Resmî Kaynaklı (VERIFIED): {kontrol_edilen}")
    print(f"Dahili Karar (PROJE_KARARI): {proje_karari}")
    print(f"Mantıksal Çıkarım (INFERRED): {inferred}")
    print(f"Doğrulanmamış (UNVERIFIED): {unverified}")
    print(f"Bayat İddia: {bayat}")

if __name__ == '__main__':
    main()
