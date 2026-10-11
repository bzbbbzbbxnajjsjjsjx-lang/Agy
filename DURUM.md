# Wiki ve Sistem Doğrulama Durumu (DURUM.md)

**Tarih:** 2026-10-12  
**Genel Sağlık:** Tüm wiki notları resmî kaynaklarından doğrulanmıştır (`confidence: VERIFIED`).

---

## 1. Wiki Doğrulama Tablosu

| Not | Kaynak (`source_url`) | Erişim | Sonuç | Notun `verified_at` Değeri |
| :--- | :--- | :---: | :---: | :---: |
| [`wiki/android-stack.md`](wiki/android-stack.md) | `https://developer.android.com/build/releases/gradle-plugin` | **BAŞARILI** (HTTP 200) | **AYNI** (AGP 9.4.0, Gradle 9.6.0 teyit edildi) | 2026-10-12 |
| [`wiki/ui-rules.md`](wiki/ui-rules.md) | `https://developer.android.com/jetpack/androidx/releases/compose-material3` | **BAŞARILI** (HTTP 200) | **FARKLI** (`source_url` resmî compose-material3 sürüm sayfasına güncellendi; Material Symbols XML ve Material Icons kaldırma uyarısı teyit edildi) | 2026-10-12 |
| [`wiki/kabul-kriterleri.md`](wiki/kabul-kriterleri.md) | `https://developer.android.com/topic/architecture` | **BAŞARILI** (HTTP 200) | **AYNI** (Separation of concerns, UDF, UI/Data katman ayrımı teyit edildi) | 2026-10-12 |

---

## 2. Doğrulayamadıklarım (Erişilemeyen Kaynaklar)

*Doğrulanamayan kaynak bulunmamaktadır.* İncelenen 3 resmî Android dokümantasyon adresine de HTTP 200 ile başarıyla erişilmiş, ham HTML içerikleri taranarak tüm mimari kısıt ve sürüm iddiaları doğrulanmıştır.

---

## 3. Wiki Not İstatistikleri

- **Toplam Not Sayısı:** 3
- **Doğrulanmış Notlar (`VERIFIED`):** 3 (%100)
- **Kaynaksız Notlar:** 0
- **Bayat Notlar (`bugün > review_at`):** 0

---

## 4. Bir Sonraki Yeniden Doğrulama Tarihi

- **Planlanan Gözden Geçirme (`review_at`):** `2027-01-10`
