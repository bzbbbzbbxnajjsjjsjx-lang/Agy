# Wiki ve Sistem Doğrulama Durumu (DURUM.md)

**Tarih:** 2026-10-11 (`date -u +%F`)  
**Doğrulama Yaklaşımı:** İddia ve Satır Bazlı Atomik Doğrulama  
**Kontrol edilen iddia: 14 / toplam iddia: 14**

---

## 1. İddia ve Satır Bazlı Doğrulama Tablosu

### A. Android Teknoloji Yığını (`wiki/android-stack.md`)

| İddia / Satır | Değer | Doğrulama Kaynağı | Erişim | Durum | Doğrulama Tarihi (`verified_at`) |
| :--- | :---: | :--- | :---: | :---: | :---: |
| **AGP** | `9.4.0` | `https://developer.android.com/build/releases/gradle-plugin` | HTTP 200 | `VERIFIED` | 2026-10-11 |
| **Gradle (min uyumlu)** | `9.6.0` | `https://developer.android.com/build/releases/gradle-plugin` | HTTP 200 | `VERIFIED` | 2026-10-11 |
| **JDK** | `17` | `https://developer.android.com/build/releases/gradle-plugin` | HTTP 200 | `VERIFIED` | 2026-10-11 |
| **Kotlin** | `2.4.21` | `https://kotlinlang.org/docs/releases.html` | HTTP 200 | `VERIFIED` | 2026-10-11 |
| **compileSdk / targetSdk** | `37` | `https://developer.android.com/build/releases/gradle-plugin` | HTTP 200 | `VERIFIED` | 2026-10-11 |
| **minSdk** | `24` | Proje kararı (Dış doğrulama yok) | - | `PROJE KARARI` | - |
| **Compose BOM** | `2026.09.00` | `https://developer.android.com/develop/ui/compose/bom/bom-mapping` | HTTP 200 | `VERIFIED` | 2026-10-11 |
| **material3** | `1.4.0` | `https://developer.android.com/jetpack/androidx/releases/compose-material3` | HTTP 200 | `VERIFIED` | 2026-10-11 |
| **material3-adaptive** | `1.3.0` | `https://developer.android.com/jetpack/androidx/releases/compose-material3-adaptive` | HTTP 200 | `VERIFIED` | 2026-10-11 |
| **core-ktx** | `1.19.1` | `https://developer.android.com/jetpack/androidx/releases/core` | HTTP 200 | `VERIFIED` | 2026-10-11 |
| **lifecycle** | `2.11.0` | `https://developer.android.com/jetpack/androidx/releases/lifecycle` | HTTP 200 | `VERIFIED` | 2026-10-11 |
| **activity** | `1.13.0` | `https://developer.android.com/jetpack/androidx/releases/activity` | HTTP 200 | `VERIFIED` | 2026-10-11 |

### B. UI Kuralları (`wiki/ui-rules.md`)

| İddia / Kural | Kaynak | Erişim | Durum | `verified_at` |
| :--- | :--- | :---: | :---: | :---: |
| Material Symbols XML zorunluluğu, Material Icons'ın kaldırılması, Token kullanımı | `https://developer.android.com/jetpack/androidx/releases/compose-material3` | HTTP 200 | **AYNI (kaynak düzeltildi)** | 2026-10-11 |

### C. Mimari ve Kabul Kriterleri (`wiki/kabul-kriterleri.md`)

| Kapsam | Kaynak | Erişim | Durum | `verified_at` |
| :--- | :--- | :---: | :---: | :---: |
| **Resmî Rehber:** Separation of concerns, Drive UI from data models, SSOT, UDF, UI/Data katmanları | `https://developer.android.com/topic/architecture` | HTTP 200 | `VERIFIED` | 2026-10-11 |
| **Proje Kuralları:** Version catalog, BOM platform, assembleDebug, token/preview zorunluluğu | Proje standartları | - | `PROJE KARARI` (`INFERRED`) | 2026-10-11 |

---

## 2. Doğrulayamadıklarım (BULUNAMADI / ERİŞİLEMEDİ)

- **Dış Kaynakta Bulunamayan veya Erişilemeyen:** `0` (Tüm dış resmî kaynaklar HTTP 200 döndürmüş ve ilgili sürümler sayfa metninde teyit edilmiştir).
- **Dış Kaynağı Olmayan / Proje Kararı Olan Maddeler:**
  1. `minSdk 24`: Proje hedef kitlesi kararıdır, dış resmî kütüphane sürüm belgesinde zorunluluk olarak yer almaz.
  2. `wiki/kabul-kriterleri.md` içerisindeki "Proje kuralları" (libs.versions.toml, BOM platformu, assembleDebug zorunluluğu): Depo içi mimari ve derleme disiplini kararlarıdır; dış kaynak yerine `INFERRED` olarak işaretlenmiştir.

---

## 3. Yeniden Doğrulama Takvimi

- **En Eski Satır Doğrulama Tarihi:** `2026-10-11`
- **Bir Sonraki Yeniden Doğrulama (`review_at`):** `2027-01-10`
