---
name: durum
description: Wiki'deki tüm iddiaların (claims) tazelik, kaynak ve doğrulama durumunu raporlar.
---

# Durum Komutu Talimatı (İddia Bazlı)

DURUM.md'yi elle düzenleme; `python3 scripts/check_claims.py --durum` çalıştır.

## Yapılacak İşlemler

Durum kontrolü doğrudan `python3 scripts/check_claims.py` veya `DURUM.md` dosyasını otomatik üretmek için `python3 scripts/check_claims.py --durum` komutu çalıştırılarak yapılır:
```bash
python3 scripts/check_claims.py --durum
```

1. `wiki/` dizini altındaki tüm notları (`*.md`, `README.md` hariç) tara (veya `scripts/check_claims.py` çalıştır).
2. Her notun `claims` listesindeki atomik iddiaları oku.
3. Her iddia için şu sütunlardan oluşan detaylı iddia tablosunu üret:
   - **İddia ID**: Benzersiz iddia kimliği (ör. `claim-agp-version`, `claim-ui-no-material-icons`)
   - **İddia Metni**: İddianın özeti veya ifade ettiği somut değer
   - **Bulunduğu Not**: İlgili wiki dosyası (ör. `wiki/android-stack.md`)
   - **Kaynak (`source_url`)**: Doğrulama kaynağı URL'si (`PROJE_KARARI` için `null`)
   - **Güven (`confidence`)**: `VERIFIED` | `PROJE_KARARI` | `INFERRED` | `UNVERIFIED`
   - **verified_at**: En son doğrulama tarihi
   - **review_at**: Son geçerlilik tarihi
   - **BAYAT mı**: `bugün > review_at` ise `EVET (BAYAT)`, değilse `HAYIR (GÜNCEL)`
4. Tablonun altına özet iddia istatistiklerini ekle:
   - Kontrol edilen iddia sayısı (VERIFIED & source_url olanlar) / Toplam iddia sayısı
   - `VERIFIED` iddia sayısı
   - `PROJE_KARARI` iddia sayısı
   - `INFERRED` iddia sayısı
   - `UNVERIFIED` (kaynaksız / doğrulanmamış) iddia sayısı
   - Bayat iddia sayısı (`bugün > review_at`)
   - Not bazında genel sağlık özeti
5. **KURAL:** DURUM.md'yi elle düzenleme; `python3 scripts/check_claims.py --durum` çalıştır.
