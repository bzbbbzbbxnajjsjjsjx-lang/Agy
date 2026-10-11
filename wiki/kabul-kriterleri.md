---
title: Mimari ve Kabul Kriterleri
tags: [kabul]
source_url: https://developer.android.com/topic/architecture
verified_at: 2026-10-12
review_at: 2027-01-10
confidence: VERIFIED
---

# Mimari ve Kabul Kriterleri

Bu dosya, bu depoda üretilecek tüm Android uygulamalarının sağlaması gereken temel kalite ve kabul kriterlerini tanımlar. Bir görev bu kriterler karşılanmadan tamamlandı ("done") kabul edilemez.

## Temel Kabul Kriterleri

1. **Bağımlılık ve Sürüm Yönetimi:**
   - Tüm kütüphane ve eklenti sürümleri istisnasız `gradle/libs.versions.toml` (Version Catalog) dosyasında tutulmalıdır.
   - Compose bağımlılıkları `compose-bom` platformu üzerinden yönetilmeli, alt Compose kütüphanelerinde (ui, material3 vb.) tek tek sürüm belirtilmemelidir.

2. **UI ve Ana İş Parçacığı (Main Thread) Hijyeni:**
   - Hiçbir `@Composable` fonksiyon içerisinde doğrudan pahalı işlemler (dosya I/O, veritabanı okuma/yazma, ağ çağrısı, ağır hesaplamalar) yürütülemez.
   - Bu işlemler Coroutine Dispatchers (ör. `Dispatchers.IO`, `Dispatchers.Default`) üzerinde çalışan ViewModel veya repository katmanlarına delege edilmelidir.

3. **Durum Korunumu (State Preservation):**
   - Ekran döndürme, dil değişimi veya sistem tarafından sürecin yeniden başlatılması (process death) durumlarında kullanıcı arayüzü durumu kaybolmamalıdır.
   - Durumlar `rememberSaveable` veya ViewModel içerisinde `StateFlow` / `SavedStateHandle` kullanılarak korunmalıdır.

4. **Doğrulama ve Derlenebilirlik:**
   - Kod değişiklikleri sonrası `./gradlew assembleDebug` hatasız derlenmeli veya derlenemediyse doğrulanmadığı açıkça raporlanmalıdır.
