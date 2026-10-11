---
title: Mimari ve Kabul Kriterleri
tags: [kabul, mimari]
source_url: https://developer.android.com/topic/architecture
verified_at: 2026-10-11
review_at: 2027-01-10
confidence: INFERRED
---

# Mimari ve Kabul Kriterleri

Bu dosya, bu depoda üretilecek tüm Android uygulamalarının sağlaması gereken temel kalite ve kabul kriterlerini tanımlar. Mimari temeller resmî rehberden alınmış, yapılandırma ve derleme hijyeni ise proje kuralları olarak ayrıştırılmıştır.

---

## 1. Resmî Rehberden (developer.android.com/topic/architecture)

Aşağıdaki ilkeler doğrudan Android Resmî Mimari Kılavuzu'ndan teyit edilmiştir:

1. **Endişelerin Ayrımı (Separation of Concerns):**
   - *Alıntı:* `"The most important principle is separation of concerns: separating code into distinct classes with clear responsibilities."`
   - UI kodları ile veri/iş mantığı kesin olarak birbirinden ayrılmalıdır. Activity ve Composable'lar yalnızca UI çizimi ve kullanıcı etkileşimlerinden sorumlu olmalıdır.

2. **UI'ı Veri Modellerinden Sürmek (Drive UI from Data Models):**
   - *Alıntı:* `"Another important principle is to drive your UI from data models, preferably persistent models."`
   - UI katmanı doğrudan veri oluşturmaz veya manipüle etmez; modelden gelen durum akışlarını gözlemler.

3. **Tek Doğruluk Kaynağı (Single Source of Truth - SSOT):**
   - *Alıntı:* `"When a new data type is defined in your app, assign a single source of truth (SSOT)..."`
   - Veri modellerinin tek bir sahibi (repository/data source) olmalı; UI katmanı doğrudan verinin sahibi olmamalıdır.

4. **Tek Yönlü Veri Akışı (Unidirectional Data Flow - UDF):**
   - *Alıntı:* `"State flows down and events flow up."`
   - Durumlar (state) ViewModel'den UI'a doğru aşağı akar, kullanıcı eylemleri ve olaylar (events) UI'dan yukarı doğru aktarılır.

5. **Katman Ayrımı ve ViewModel:**
   - *Alıntı:* Modern app architecture recommends separating `"UI layer"` and `"Data layer"`.
   - Arka plan işleri, ağ veya veritabanı I/O işlemleri Composable içerisinde doğrudan yürütülmez; ViewModel ve Coroutine Dispatchers üzerinden yürütülür.

---

## 2. Proje Kuralları (Dış Doğrulama Yok — Dahili Standartlar)

Aşağıdaki maddeler bu deponun kendi standartları ve çalışma kurallarıdır; dış kaynaktan bağımsız olarak kabul kriteridir:

1. **Bağımlılık ve Sürüm Yönetimi (Version Catalog):**
   - Tüm kütüphane ve eklenti sürümleri istisnasız `gradle/libs.versions.toml` dosyasında tutulmalıdır.
   - Compose bağımlılıkları `compose-bom` platformu (`platform(libs.androidx.compose.bom)`) üzerinden yönetilmeli, alt kütüphanelere sürüm atanmamalıdır.

2. **Tasarım ve UI Standartları:**
   - Ham hex (`Color(0xFF...)`) ve ham dp/sp kod içinde gömülü olamaz; `MaterialTheme.colorScheme` ve tema tokenları kullanılır.
   - Her Composable için açık/koyu temayı doğrulayan `@Preview` fonksiyonu zorunludur.
   - Tüm metinler `res/values/strings.xml` dosyasından `stringResource` ile çekilir.

3. **Durum Korunumu (State Preservation):**
   - Ekran döndürme, dil değişimi veya süreç sonlanması (process death) durumlarında kullanıcı durumu `rememberSaveable` ve ViewModel `StateFlow` / `SavedStateHandle` ile korunmalıdır.

4. **Derleme ve Doğrulama Hijyeni:**
   - Kod değişiklikleri sonrası `./gradlew assembleDebug` hatasız derlenmeli veya derlenmediği açıkça raporlanmalıdır.
