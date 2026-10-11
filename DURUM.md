# Wiki İddia (Claim) Doğrulama Durumu (DURUM.md)

**Tarih:** 2026-10-11 (`date -u +%F`)  
**Doğrulama Mimarisi:** İddia (Claim) Düzeyinde Atomik Doğrulama  
**Kontrol edilen iddia: 27 / toplam iddia: 27**

---

## 1. Atomik İddia Doğrulama Tablosu

### A. Android Teknoloji Yığını (`wiki/android-stack.md`)

| İddia ID | İddia / Değer | Doğrulama Kaynağı | Erişim | Durum | Doğrulama Tarihi (`verified_at`) |
| :--- | :--- | :--- | :---: | :---: | :---: |
| `claim-agp-version` | AGP sabitlenmiş sürümü `9.4.0`'dır. | `https://developer.android.com/build/releases/gradle-plugin` | HTTP 200 | `VERIFIED` | 2026-10-11 |
| `claim-gradle-version` | Asgari uyumlu Gradle sürümü `9.6.0`'dır. | `https://developer.android.com/build/releases/gradle-plugin` | HTTP 200 | `VERIFIED` | 2026-10-11 |
| `claim-jdk-version` | Derleme hedef Java ortamı JDK `17`'dir. | `https://developer.android.com/build/releases/gradle-plugin` | HTTP 200 | `VERIFIED` | 2026-10-11 |
| `claim-kotlin-version` | Kotlin dil sürümü `2.4.21`'dir. | `https://kotlinlang.org/docs/releases.html` | HTTP 200 | `VERIFIED` | 2026-10-11 |
| `claim-compile-target-sdk` | compileSdk ve targetSdk hedef API `37`'dir. | `https://developer.android.com/build/releases/gradle-plugin` | HTTP 200 | `VERIFIED` | 2026-10-11 |
| `claim-min-sdk` | minSdk sürümü `24`'tür. | `PROJE_KARARI` | - | `PROJE KARARI` | - |
| `claim-compose-bom` | Compose BOM `2026.09.00` (Compose 1.12.x)'dir. | `https://developer.android.com/develop/ui/compose/bom/bom-mapping` | HTTP 200 | `VERIFIED` | 2026-10-11 |
| `claim-material3-version` | Material 3 stabil sürümü `1.4.0`'dır. | `https://developer.android.com/jetpack/androidx/releases/compose-material3` | HTTP 200 | `VERIFIED` | 2026-10-11 |
| `claim-material3-no-expressive` | Material 3 expressive API'leri 1.4.0 stabil sürümünde yoktur. | `https://developer.android.com/jetpack/androidx/releases/compose-material3` | HTTP 200 | `VERIFIED` | 2026-10-11 |
| `claim-material3-adaptive` | material3-adaptive sürümü `1.3.0`'dır. | `https://developer.android.com/jetpack/androidx/releases/compose-material3-adaptive` | HTTP 200 | `VERIFIED` | 2026-10-11 |
| `claim-core-ktx` | core-ktx sürümü `1.19.1`'dir. | `https://developer.android.com/jetpack/androidx/releases/core` | HTTP 200 | `VERIFIED` | 2026-10-11 |
| `claim-lifecycle` | lifecycle-runtime-ktx sürümü `2.11.0`'dır. | `https://developer.android.com/jetpack/androidx/releases/lifecycle` | HTTP 200 | `VERIFIED` | 2026-10-11 |
| `claim-activity` | activity-compose sürümü `1.13.0`'dır. | `https://developer.android.com/jetpack/androidx/releases/activity` | HTTP 200 | `VERIFIED` | 2026-10-11 |

### B. UI Kuralları (`wiki/ui-rules.md`)

| İddia ID | İddia / Kural | Doğrulama Kaynağı | Erişim | Durum | Doğrulama Tarihi (`verified_at`) |
| :--- | :--- | :--- | :---: | :---: | :---: |
| `claim-ui-no-material-icons` | `material-icons-*` kullanımı kaldırılmıştır, yasaktır. | `https://developer.android.com/jetpack/androidx/releases/compose-material3` | HTTP 200 | **AYNI (kaynak düzeltildi)** | 2026-10-11 |
| `claim-ui-material-symbols-xml` | İkonlar Material Symbols XML olarak indirilir. | `https://developer.android.com/jetpack/androidx/releases/compose-material3` | HTTP 200 | **AYNI (kaynak düzeltildi)** | 2026-10-11 |
| `claim-ui-theme-raw-values` | Ham renk ve dp/sp yalnızca `ui/theme/` altında tanımlanır, Composable'da yasaktır. | `PROJE_KARARI` | - | `PROJE KARARI` | - |
| `claim-ui-mandatory-preview` | Her Composable için açık/koyu `@Preview` zorunludur. | `https://developer.android.com/develop/ui/compose/tooling/previews` | HTTP 200 | `VERIFIED` | 2026-10-11 |
| `claim-ui-strings-xml` | Hardcoded metin yasaktır; strings.xml zorunludur. | `https://developer.android.com/guide/topics/resources/string-resource` | HTTP 200 | `VERIFIED` | 2026-10-11 |

### C. Mimari ve Kabul Kriterleri (`wiki/kabul-kriterleri.md`)

| İddia ID | Kapsam / İlke | Doğrulama Kaynağı | Erişim | Durum | Doğrulama Tarihi (`verified_at`) |
| :--- | :--- | :--- | :---: | :---: | :---: |
| `claim-arch-separation-of-concerns` | Separation of Concerns (Endişelerin ayrımı ilkesi). | `https://developer.android.com/topic/architecture` | HTTP 200 | `VERIFIED` | 2026-10-11 |
| `claim-arch-drive-ui-models` | Drive UI from data models (UI modelden sürülür). | `https://developer.android.com/topic/architecture` | HTTP 200 | `VERIFIED` | 2026-10-11 |
| `claim-arch-ssot` | Single source of truth (Tek doğruluk kaynağı). | `https://developer.android.com/topic/architecture` | HTTP 200 | `VERIFIED` | 2026-10-11 |
| `claim-arch-udf` | Unidirectional Data Flow (Tek yönlü veri akışı). | `https://developer.android.com/topic/architecture` | HTTP 200 | `VERIFIED` | 2026-10-11 |
| `claim-arch-layer-viewmodel` | UI Layer & Data Layer ayrımı; Composable içinde I/O yapılmaz. | `https://developer.android.com/topic/architecture` | HTTP 200 | `VERIFIED` | 2026-10-11 |
| `claim-rule-version-catalog` | Sürümler yalnızca `gradle/libs.versions.toml`'da tutulur. | `PROJE_KARARI` | - | `PROJE KARARI` | - |
| `claim-rule-compose-bom-platform` | Compose bağımlılıkları `platform(compose-bom)` ile eklenir. | `PROJE_KARARI` | - | `PROJE KARARI` | - |
| `claim-rule-state-preservation` | Durumlar `rememberSaveable` ve ViewModel ile korunur. | `PROJE_KARARI` | - | `PROJE KARARI` | - |
| `claim-rule-assemble-debug` | Değişiklik sonrası `./gradlew assembleDebug` derlenmelidir. | `PROJE_KARARI` | - | `PROJE KARARI` | - |

---

## 2. Doğrulayamadıklarım (BULUNAMADI / ERİŞİLEMEDİ)

- **Harici Kaynakta Bulunamayan veya Erişilemeyen:** `0` (Taranan 21 dış kaynak iddiasının tamamına HTTP 200 ile erişildi ve sayfa metninde teyit edildi).
- **Harici Doğrulamaya Tabi Tutulmayan Proje Kararları (6 iddia):**
  1. `claim-min-sdk`: minSdk 24 tercihi.
  2. `claim-ui-theme-raw-values`: Ham renk ve dp/sp değerlerinin yalnızca `ui/theme/` içinde tutulması kuralı.
  3. `claim-rule-version-catalog`: `libs.versions.toml` tekillik standardı.
  4. `claim-rule-compose-bom-platform`: BOM platform zorunluluğu standardı.
  5. `claim-rule-state-preservation`: rememberSaveable / StateFlow standardı.
  6. `claim-rule-assemble-debug`: assembleDebug derleme standardı.

---

## 3. İstatistikler ve Takvim

- **Toplam İddia Sayısı:** 27
- **Doğrulanan Harici İddia (`VERIFIED`):** 19
- **Kaynağı Düzeltilip Doğrulanan (`AYNI (kaynak düzeltildi)`):** 2
- **Dahili Proje Kararı (`PROJE KARARI`):** 6
- **Bulunamayan / Erişilemeyen (`BULUNAMADI / ERİŞİLEMEDİ`):** 0
- **Bayat İddia Sayısı (`bugün > review_at`):** 0
- **Bir Sonraki Yeniden İnceleme (`review_at`):** `2027-01-10`
