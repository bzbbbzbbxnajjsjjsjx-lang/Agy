---
name: durum
description: Wiki'nin tazelik durumunu gösterir ve notların geçerlilik tablosunu üretir.
---

# Durum Komutu Talimatı

Bu skill yalnızca okuma yapar, hiçbir dosyayı değiştirmez.

## Yapılacak İşlemler

1. `wiki/` dizini altındaki tüm notları (`*.md`, `README.md` hariç) oku.
2. Her notun frontmatter alanlarını analiz et ve şu sütunlardan oluşan bir tablo üret:
   - **not**: Dosya adı veya slug (ör. `wiki/android-stack.md`)
   - **confidence**: `VERIFIED` | `INFERRED` | `UNVERIFIED`
   - **source_url var mı**: Evet / Hayır
   - **verified_at**: En son doğrulama tarihi
   - **review_at**: Yeniden gözden geçirme tarihi
   - **BAYAT mı**: `bugün > review_at` ise `EVET (BAYAT)`, değilse `HAYIR (GÜNCEL)`
3. Tablonun altına özet istatistikleri ekle:
   - Kaç not `VERIFIED`?
   - Kaç not kaynaksız?
   - Kaç not bayat?
4. **KURAL:** Hiçbir şeyi DEĞİŞTİRME — bu komut sadece okur ve raporlar.
