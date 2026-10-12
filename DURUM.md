# Wiki İddia (Claim) Doğrulama Durumu (DURUM.md)

**Tarih:** 2026-10-12 (`date -u +%F`)  
**Doğrulama Mimarisi:** İddia (Claim) Düzeyinde Dürüst ve Atomik Doğrulama  
**Kontrol edilen iddia: 17 / toplam: 27**

---

## 1. Atomik İddia Doğrulama Tablosu

### A. Android Teknoloji Yığını (`wiki/android-stack.md`)

| İddia ID | İddia / Değer | Doğrulama Kaynağı | Erişim | Durum / Güven | Doğrulama Tarihi (`verified_at`) |
| :--- | :--- | :--- | :---: | :---: | :---: |
| `claim-agp-version` | Android Gradle Plugin (AGP) sabitlenmiş sürümü 9.4.0'dır. | `https://developer.android.com/build/releases/gradle-plugin` | HTTP 200 | `VERIFIED` | 2026-10-11 |
| `claim-gradle-version` | AGP 9.4.0 ile asgari uyumlu Gradle sürümü 9.6.0'dır. | `https://developer.android.com/build/releases/gradle-plugin` | HTTP 200 | `VERIFIED` | 2026-10-11 |
| `claim-jdk-version` | Derleme için gerekli JDK hedef ortamı JDK 17'dir. | `https://developer.android.com/build/releases/gradle-plugin` | HTTP 200 | `VERIFIED` | 2026-10-11 |
| `claim-kotlin-version` | Kotlin temel dil sürümü 2.4.21'dir. | `https://kotlinlang.org/docs/releases.html` | HTTP 200 | `VERIFIED` | 2026-10-11 |
| `claim-compose-bom` | Compose BOM sürümü 2026.09.00 olup Compose 1.12.x kütüphanelerine eşlenir. | `https://developer.android.com/develop/ui/compose/bom/bom-mapping` | HTTP 200 | `VERIFIED` | 2026-10-11 |
| `claim-material3-version` | Material 3 (androidx.compose.material3) stabil sürümü 1.4.0'dır. | `https://developer.android.com/jetpack/androidx/releases/compose-material3` | HTTP 200 | `VERIFIED` | 2026-10-11 |
| `claim-material3-adaptive` | material3-adaptive sürümü 1.3.0'dır. | `https://developer.android.com/jetpack/androidx/releases/compose-material3-adaptive` | HTTP 200 | `VERIFIED` | 2026-10-11 |
| `claim-core-ktx` | core-ktx sürümü 1.19.1'dir. | `https://developer.android.com/jetpack/androidx/releases/core` | HTTP 200 | `VERIFIED` | 2026-10-11 |
| `claim-lifecycle` | lifecycle (lifecycle-runtime-ktx) sürümü 2.11.0'dır. | `https://developer.android.com/jetpack/androidx/releases/lifecycle` | HTTP 200 | `VERIFIED` | 2026-10-11 |
| `claim-activity` | activity (activity-compose) sürümü 1.13.0'dır. | `https://developer.android.com/jetpack/androidx/releases/activity` | HTTP 200 | `VERIFIED` | 2026-10-11 |
| `claim-compile-target-sdk` | compileSdk ve targetSdk hedef API seviyesi 37'dir. | Proje kararı (`source_url: null`) | - | `PROJE_KARARI` | - |
| `claim-min-sdk` | minSdk asgari desteklenen sürüm 24 (Android 7.0)'tür. | Proje kararı (`source_url: null`) | - | `PROJE_KARARI` | - |

### B. UI Kuralları (`wiki/ui-rules.md`)

| İddia ID | İddia / Değer | Doğrulama Kaynağı | Erişim | Durum / Güven | Doğrulama Tarihi (`verified_at`) |
| :--- | :--- | :--- | :---: | :---: | :---: |
| `claim-ui-no-material-icons` | material-icons-core ve material-icons-extended kütüphaneleri kullanımdan kaldırılmıştır, projeye eklenemez. | `https://developer.android.com/jetpack/androidx/releases/compose-material3` | HTTP 200 | `VERIFIED` | 2026-10-11 |
| `claim-ui-material-symbols-xml` | İkonlar için res/drawable altına Material Symbols Vector Drawable XML indirilerek kullanılmalıdır. | `https://developer.android.com/jetpack/androidx/releases/compose-material3` | HTTP 200 | `VERIFIED` | 2026-10-11 |
| `claim-ui-theme-raw-values` | Ham renk (Color(0xFF..)) ve ham dp/sp YALNIZ ui/theme/ altında tanımlanır (Color.kt, Type.kt, Dimens.kt). Composable'ların içinde yasak; oralarda MaterialTheme.colorScheme / MaterialTheme.typography / Dimens kullanılır. | Proje kararı (`source_url: null`) | - | `PROJE_KARARI` | - |
| `claim-ui-mandatory-preview` | Oluşturulan her Composable fonksiyonun bağımsız bir @Preview fonksiyonu olmak zorundadır. | Proje kararı (`source_url: null`) | - | `PROJE_KARARI` | - |
| `claim-ui-strings-xml` | UI içinde doğrudan sabit metin (hardcoded text) yazılamaz; tüm metinler res/values/strings.xml dosyasından stringResource ile çekilir. | Proje kararı (`source_url: null`) | - | `PROJE_KARARI` | - |

### C. Mimari ve Kabul Kriterleri (`wiki/kabul-kriterleri.md`)

| İddia ID | İddia / Değer | Doğrulama Kaynağı | Erişim | Durum / Güven | Doğrulama Tarihi (`verified_at`) |
| :--- | :--- | :--- | :---: | :---: | :---: |
| `claim-arch-separation-of-concerns` | UI kodları ile iş/veri mantığı birbirinden ayrılmalıdır; Activity ve Composable'lar yalnızca UI çizimi ve kullanıcı etkileşiminden sorumludur. | `https://developer.android.com/topic/architecture` | HTTP 200 | `VERIFIED` | 2026-10-11 |
| `claim-arch-drive-ui-models` | UI doğrudan veri oluşturmaz veya manipüle etmez; modelden gelen durum akışlarını gözlemler. | `https://developer.android.com/topic/architecture` | HTTP 200 | `VERIFIED` | 2026-10-11 |
| `claim-arch-ssot` | Veri modellerinin tek bir sahibi (Single Source of Truth - SSOT) olmalıdır; UI verinin sahibi olamaz. | `https://developer.android.com/topic/architecture` | HTTP 200 | `VERIFIED` | 2026-10-11 |
| `claim-arch-udf` | Durumlar ViewModel'den UI'a aşağı doğru akar, kullanıcı etkileşimleri ve olaylar UI'dan ViewModel'e yukarı doğru akar. | `https://developer.android.com/topic/architecture` | HTTP 200 | `VERIFIED` | 2026-10-11 |
| `claim-arch-layers` | Modern Android uygulama mimarisinde UI katmanı (presentation) ve Veri katmanı (data layer) olmak üzere iki temel katman bulunur. | `https://developer.android.com/topic/architecture` | HTTP 200 | `VERIFIED` | 2026-10-11 |
| `claim-rule-no-composable-io` | Composable fonksiyonlar içinde doğrudan dosya/veritabanı I/O veya ağ çağrısı yapılamaz; arka plan işleri Coroutine Dispatchers ve ViewModel'e devredilmelidir. | Proje kararı (`source_url: null`) | - | `PROJE_KARARI` | - |
| `claim-rule-version-catalog` | Tüm kütüphane ve eklenti sürümleri istisnasız gradle/libs.versions.toml dosyasında tutulmalıdır. | Proje kararı (`source_url: null`) | - | `PROJE_KARARI` | - |
| `claim-rule-compose-bom-platform` | Compose bağımlılıkları platform(libs.androidx.compose.bom) ile sürüm belirtilmeden eklenmelidir. | Proje kararı (`source_url: null`) | - | `PROJE_KARARI` | - |
| `claim-rule-state-preservation` | Ekran döndürme ve süreç sonlanmasında (process death) durum rememberSaveable veya StateFlow/SavedStateHandle ile korunmalıdır. | Proje kararı (`source_url: null`) | - | `PROJE_KARARI` | - |
| `claim-rule-assemble-debug` | Kod değişiklikleri sonrası ./gradlew assembleDebug hatasız derlenmeli veya derlenmediği açıkça belirtilmelidir. | Proje kararı (`source_url: null`) | - | `PROJE_KARARI` | - |

---

## 2. Hatalar ve Uyarılar

- **Hatalar:** Yok (0 hata)
- **Uyarılar:** Yok (0 uyarı)

---

## 3. İstatistikler ve Takvim

- **Kontrol edilen iddia: 17 / toplam: 27**
- **Resmî Kaynaklı Doğrulanan (`VERIFIED`):** 17
- **Dahili Proje Kararı (`PROJE_KARARI`):** 10
- **Mantıksal Çıkarım (`INFERRED`):** 0
- **Doğrulanmamış (`UNVERIFIED`):** 0
- **Bayat İddia Sayısı (`bugün > review_at`):** 0
- **Çelişki / Hata Sayısı:** 0
- **Uyarı Sayısı:** 0
- **En Eski Satır Doğrulama Tarihi:** `2026-10-11`
- **Bir Sonraki Yeniden İnceleme (`review_at`):** `2027-01-10`
