#!/usr/bin/env python3
"""
Wiki İddia Doğrulama ve Tazelik Kontrol Scripti
Tüm wiki/*.md notlarındaki claims listelerini okur, durumlarını analiz eder.
"""

import os
import glob
import re
import yaml
from datetime import datetime, date

def load_claims():
    wiki_files = glob.glob('wiki/*.md')
    all_claims = []
    
    for fpath in sorted(wiki_files):
        if os.path.basename(fpath) == 'README.md':
            continue
        with open(fpath, 'r', encoding='utf-8') as f:
            content = f.read()
        
        # Frontmatter parse
        fm_match = re.match(r'^---\s*\n(.*?)\n---\s*\n', content, re.DOTALL)
        if not fm_match:
            continue
        
        try:
            data = yaml.safe_load(fm_match.group(1))
        except Exception as e:
            print(f"Error parsing YAML in {fpath}: {e}")
            continue
        
        claims = data.get('claims', [])
        for c in claims:
            c['file'] = fpath
            all_claims.append(c)
            
    return all_claims

def main():
    today = date.today().isoformat()
    claims = load_claims()
    
    total = len(claims)
    verified = 0
    proje_karari = 0
    kaynak_duzeltildi = 0
    bulunamadi = 0
    erisfilemedi = 0
    bayat = 0
    
    print(f"=== WIKI İDDİA (CLAIM) DOĞRULAMA DURUMU ({today}) ===")
    print(f"{'İddia ID':<32} | {'Durum':<25} | {'verified_at':<12} | {'Bulunduğu Not'}")
    print("-" * 95)
    
    for c in claims:
        cid = c.get('id', 'unknown')
        status = c.get('status') or c.get('confidence', 'UNVERIFIED')
        vat = str(c.get('verified_at') or '-')
        rat = str(c.get('review_at') or '-')
        fpath = c.get('file', '')
        
        if rat != '-' and rat < today:
            is_stale = " (BAYAT)"
            bayat += 1
        else:
            is_stale = ""
            
        if status == 'VERIFIED':
            verified += 1
        elif 'kaynak düzeltildi' in str(status):
            kaynak_duzeltildi += 1
        elif status == 'PROJE_KARARI' or status == 'PROJE KARARI':
            proje_karari += 1
        elif status == 'BULUNAMADI':
            bulunamadi += 1
        elif status == 'ERİŞİLEMEDİ':
            erisfilemedi += 1
            
        print(f"{cid:<32} | {status:<25} | {vat:<12} | {fpath}{is_stale}")
        
    print("-" * 95)
    print(f"Kontrol edilen iddia: {total} / toplam iddia: {total}")
    print(f"VERIFIED: {verified}")
    print(f"AYNI (kaynak düzeltildi): {kaynak_duzeltildi}")
    print(f"PROJE KARARI: {proje_karari}")
    print(f"BULUNAMADI: {bulunamadi}")
    print(f"ERİŞİLEMEDİ: {erisfilemedi}")
    print(f"BAYAT İDDİA: {bayat}")

if __name__ == '__main__':
    main()
