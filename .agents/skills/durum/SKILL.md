---
name: durum
description: Wiki'deki tüm iddiaların (claims) tazelik, kaynak ve doğrulama durumunu raporlar.
---

# Durum Komutu Talimatı (İddia Bazlı)

Bu skill yalnızca okuma yapar, hiçbir dosyayı değiştirmez.

## Yapılacak İşlemler

1. `wiki/` dizini altındaki tüm notları (`*.md`, `README.md` hariç) tara.
2. Her notun `claims` listesindeki atomik iddiaları oku.
3. Her iddia için şu sütunlardan oluşan detaylı iddia tablosunu üret:
   - **İddia ID**: Benzersiz iddia kimliği (ör. `agp-version`, `ui-no-raw-hex`)
   - **İddia Metni**: İddianın özeti veya ifade ettiği somut değer
   - **Bulunduğu Not**: İlgili wiki dosyası (ör. `wiki/android-stack.md`)
   - **Kaynak (`source_url`)**: Doğrulama kaynağı URL'si
   - **Güven (`confidence`)**: `VERIFIED` | `INFERRED` | `UNVERIFIED`
   - **verified_at**: En son doğrulama tarihi
   - **review_at**: Son geçerlilik tarihi
   - **BAYAT mı**: `bugün > review_at` ise `EVET (BAYAT)`, değilse `HAYIR (GÜNCEL)`
4. Tablonun altına özet iddia istatistiklerini ekle:
   - Toplam iddia sayısı
   - `VERIFIED` iddia sayısı
   - `INFERRED` iddia sayısı
   - `UNVERIFIED` (kaynaksız / doğrulanmamış) iddia sayısı
   - Bayat iddia sayısı (`bugün > review_at`)
   - Not bazında genel sağlık özeti
5. **KURAL:** Hiçbir şeyi DEĞİŞTİRME — bu komut sadece okur ve raporlar.
