---
title: Mimari ve Kabul Kriterleri
tags: [kabul, mimari]
last_reviewed: 2026-10-11
confidence: PARTIAL
verification_mode: claim_level
claims:
  - id: claim-arch-separation-of-concerns
    claim: "UI kodları ile iş/veri mantığı birbirinden ayrılmalıdır; Activity ve Composable'lar yalnızca UI çizimi ve kullanıcı etkileşiminden sorumludur."
    source_url: https://developer.android.com/topic/architecture
    verified_at: 2026-10-11
    review_at: 2027-01-10
    confidence: VERIFIED
    evidence: "The most important principle is separation of concerns: separating code into distinct classes with clear responsibilities."

  - id: claim-arch-drive-ui-models
    claim: "UI doğrudan veri oluşturmaz veya manipüle etmez; modelden gelen durum akışlarını gözlemler."
    source_url: https://developer.android.com/topic/architecture
    verified_at: 2026-10-11
    review_at: 2027-01-10
    confidence: VERIFIED
    evidence: "Another important principle is to drive your UI from data models, preferably persistent models."

  - id: claim-arch-ssot
    claim: "Veri modellerinin tek bir sahibi (Single Source of Truth - SSOT) olmalıdır; UI verinin sahibi olamaz."
    source_url: https://developer.android.com/topic/architecture
    verified_at: 2026-10-11
    review_at: 2027-01-10
    confidence: VERIFIED
    evidence: "When a new data type is defined in your app, assign a single source of truth (SSOT)..."

  - id: claim-arch-udf
    claim: "Durumlar ViewModel'den UI'a aşağı doğru akar, kullanıcı etkileşimleri ve olaylar UI'dan ViewModel'e yukarı doğru akar."
    source_url: https://developer.android.com/topic/architecture
    verified_at: 2026-10-11
    review_at: 2027-01-10
    confidence: VERIFIED
    evidence: "State flows down and events flow up."

  - id: claim-arch-layers
    claim: "Modern Android uygulama mimarisinde UI katmanı (presentation) ve Veri katmanı (data layer) olmak üzere iki temel katman bulunur."
    source_url: https://developer.android.com/topic/architecture
    verified_at: 2026-10-11
    review_at: 2027-01-10
    confidence: VERIFIED
    evidence: "In a typical app architecture, the UI layer gets the application data from the data layer or from the optional domain layer, which sits between the UI layer and the data layer."

  - id: claim-rule-no-composable-io
    claim: "Composable fonksiyonlar içinde doğrudan dosya/veritabanı I/O veya ağ çağrısı yapılamaz; arka plan işleri Coroutine Dispatchers ve ViewModel'e devredilmelidir."
    source_url: null
    verified_at: null
    review_at: 2027-01-10
    confidence: PROJE_KARARI
    evidence: "Depo içi main-thread hijyeni ve performans kabul standardı; dış doğrulama iddia edilmez."

  - id: claim-rule-version-catalog
    claim: "Tüm kütüphane ve eklenti sürümleri istisnasız gradle/libs.versions.toml dosyasında tutulmalıdır."
    source_url: null
    verified_at: null
    review_at: 2027-01-10
    confidence: PROJE_KARARI
    evidence: "Depo içi bağımlılık tekilleştirme standardı."

  - id: claim-rule-compose-bom-platform
    claim: "Compose bağımlılıkları platform(libs.androidx.compose.bom) ile sürüm belirtilmeden eklenmelidir."
    source_url: null
    verified_at: null
    review_at: 2027-01-10
    confidence: PROJE_KARARI
    evidence: "Depo içi sürüm çakışması engelleme standardı."

  - id: claim-rule-state-preservation
    claim: "Ekran döndürme ve süreç sonlanmasında (process death) durum rememberSaveable veya StateFlow/SavedStateHandle ile korunmalıdır."
    source_url: null
    verified_at: null
    review_at: 2027-01-10
    confidence: PROJE_KARARI
    evidence: "Depo içi durum sürekliliği kabul kriteri."

  - id: claim-rule-assemble-debug
    claim: "Kod değişiklikleri sonrası ./gradlew assembleDebug hatasız derlenmeli veya derlenmediği açıkça belirtilmelidir."
    source_url: null
    verified_at: null
    review_at: 2027-01-10
    confidence: PROJE_KARARI
    evidence: "Değişmez kural 3 gereği derleme doğrulaması standardı."
---

# Mimari ve Kabul Kriterleri

Bu dosya, bu depoda üretilecek tüm Android uygulamalarının sağlaması gereken temel kalite ve kabul kriterlerini tanımlar. İddialar resmî kılavuz ve dahili proje kararları olarak ikiye ayrılmıştır.

---

## 1. Resmî Rehberden (developer.android.com/topic/architecture)

| İddia ID | İlke / Kural | Kaynak | Durum | Alıntı / Kanıt |
| :--- | :--- | :--- | :---: | :--- |
| `claim-arch-separation-of-concerns` | **Endişelerin Ayrımı** | `developer.android.com/topic/architecture` | `VERIFIED` | *"The most important principle is separation of concerns: separating code into distinct classes with clear responsibilities."* |
| `claim-arch-drive-ui-models` | **UI'ı Modelden Sürmek** | `developer.android.com/topic/architecture` | `VERIFIED` | *"Another important principle is to drive your UI from data models, preferably persistent models."* |
| `claim-arch-ssot` | **Tek Doğruluk Kaynağı (SSOT)** | `developer.android.com/topic/architecture` | `VERIFIED` | *"When a new data type is defined in your app, assign a single source of truth (SSOT)..."* |
| `claim-arch-udf` | **Tek Yönlü Veri Akışı (UDF)** | `developer.android.com/topic/architecture` | `VERIFIED` | *"State flows down and events flow up."* |
| `claim-arch-layers` | **Katman Ayrımı (UI Layer / Data Layer)** | `developer.android.com/topic/architecture` | `VERIFIED` | *"In a typical app architecture, the UI layer gets the application data from the data layer or from the optional domain layer, which sits between the UI layer and the data layer."* |

---

## 2. Proje Kuralları (Dış Doğrulama Yok — Dahili Standartlar)

| İddia ID | Standart | Kaynak | Durum | Gerekçe / Açıklama |
| :--- | :--- | :---: | :---: | :--- |
| `claim-rule-no-composable-io` | Composable içinde doğrudan I/O veya ağ çağrısı yasaktır; ViewModel kullanılır. | - | `PROJE KARARI` | Main thread hijyeni ve takılma (jank) engelleme |
| `claim-rule-version-catalog` | Sürümler yalnızca `gradle/libs.versions.toml` dosyasında tutulur. | - | `PROJE KARARI` | Sürüm tekilleştirme standardı |
| `claim-rule-compose-bom-platform` | Compose bağımlılıkları `compose-bom` platformu ile eklenir. | - | `PROJE KARARI` | Alt kütüphane sürüm çakışmalarını önleme |
| `claim-rule-state-preservation` | Durumlar `rememberSaveable` ve ViewModel ile korunur. | - | `PROJE KARARI` | Kullanıcı deneyimi ve süreç sonlanması dayanıklılığı |
| `claim-rule-assemble-debug` | Değişiklik sonrası `./gradlew assembleDebug` hatasız derlenmelidir. | - | `PROJE KARARI` | Değişmez kural 3 gereği derleme doğrulaması |
