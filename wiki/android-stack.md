---
title: Android Teknoloji Yığını ve Sabitlenmiş Sürümler
tags: [stack, surum]
source_url: https://developer.android.com/build/releases/gradle-plugin
verified_at: 2026-10-11 # En eski satır doğrulama tarihi (satır bazında doğrulama)
review_at: 2027-01-10
confidence: VERIFIED
verification_mode: row_level # Satır bazında doğrulama
---

# Android Teknoloji Yığını ve Sabitlenmiş Sürümler

Doğrulama: Satır bazında münferit resmî kaynaklar üzerinden yürütülmüştür.  
Son Doğrulama: 2026-10-11 · Yeniden Doğrulama: 2027-01-10

## Sabitlenmiş Sürümler (İddia ve Satır Bazlı Doğrulama)

| Bileşen / Kütüphane | Sürüm | Kaynak | Durum | Not / Kapsam |
| :--- | :---: | :--- | :---: | :--- |
| **Android Gradle Plugin (AGP)** | `9.4.0` | `https://developer.android.com/build/releases/gradle-plugin` | `VERIFIED` | Derleme altyapısı |
| **Gradle (min uyumlu)** | `9.6.0` | `https://developer.android.com/build/releases/gradle-plugin` | `VERIFIED` | Build runner uyumluluğu |
| **JDK** | `17` | `https://developer.android.com/build/releases/gradle-plugin` | `VERIFIED` | Hedef Java ortamı |
| **Kotlin** | `2.4.21` | `https://kotlinlang.org/docs/releases.html` | `VERIFIED` | Temel dil sürümü |
| **compileSdk / targetSdk** | `37` | `https://developer.android.com/build/releases/gradle-plugin` | `VERIFIED` | "The maximum API level that AGP 9.4 supports is API level 37" |
| **minSdk** | `24` | Proje kararı | `PROJE KARARI` | Asgari SDK (Dış doğrulama yok) |
| **Compose BOM** | `2026.09.00` | `https://developer.android.com/develop/ui/compose/bom/bom-mapping` | `VERIFIED` | Compose 1.12.x eşlemesi |
| **material3** | `1.4.0` | `https://developer.android.com/jetpack/androidx/releases/compose-material3` | `VERIFIED` | Stabil |
| **material3-adaptive** | `1.3.0` | `https://developer.android.com/jetpack/androidx/releases/compose-material3-adaptive` | `VERIFIED` | androidx/versions dizininden teyitli |
| **core-ktx** | `1.19.1` | `https://developer.android.com/jetpack/androidx/releases/core` | `VERIFIED` | AndroidX çekirdek eklentisi |
| **lifecycle** | `2.11.0` | `https://developer.android.com/jetpack/androidx/releases/lifecycle` | `VERIFIED` | Yaşam döngüsü yönetimi |
| **activity** | `1.13.0` | `https://developer.android.com/jetpack/androidx/releases/activity` | `VERIFIED` | Activity Compose entegrasyonu |

> [!WARNING]
> `material3`'ün "expressive" API'leri 1.4.0'dan ÇIKARILDI ve hiç stabil olmadı. Kullanma. "Compose 3" diye bir sürüm yok — kastedilen Material 3'tür.

## Resmî Kaynaklar (Yalnızca Bunları Kullan)

### Android & Jetpack
- https://developer.android.com/build/releases/gradle-plugin
- https://developer.android.com/develop/ui/compose/bom/bom-mapping
- https://developer.android.com/jetpack/androidx/releases/compose-material3
- https://developer.android.com/jetpack/androidx/versions
- https://developer.android.com/jetpack/androidx/releases/compose-material3-adaptive
- https://developer.android.com/jetpack/androidx/releases/core
- https://developer.android.com/jetpack/androidx/releases/lifecycle
- https://developer.android.com/jetpack/androidx/releases/activity

### Kotlin
- https://kotlinlang.org/docs/releases.html

### Material 3
- https://m3.material.io/blog/material-is-compose-first
- https://fonts.google.com/icons

> [!IMPORTANT]
> Blog, forum, Medium, StackOverflow sürüm kaynağı DEĞİLDİR. Sürüm için yalnız yukarıdaki resmî sayfalar kullanılır. Bir örnek depodan kod kalıbı alabilirsin ama sürümünü alma.
