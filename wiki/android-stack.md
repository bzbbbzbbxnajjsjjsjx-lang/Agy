---
title: Android Teknoloji Yığını ve Sabitlenmiş Sürümler
tags: [stack, surum]
source_url: https://developer.android.com/build/releases/gradle-plugin
verified_at: 2026-10-12
review_at: 2027-01-10
confidence: VERIFIED
---

# Android Teknoloji Yığını ve Sabitlenmiş Sürümler

Doğrulama: 2026-10-12 · Kaynak: developer.android.com
Yeniden doğrulama: 2027-01-10

## Sabitlenmiş Sürümler

| Bileşen / Kütüphane | Sürüm | Not / Kapsam |
| :--- | :--- | :--- |
| **Android Gradle Plugin (AGP)** | `9.4.0` | Derleme altyapısı |
| **Gradle** | `9.6.0` | Build runner |
| **Kotlin** | `2.4.21` | Temel dil sürümü |
| **JDK** | `17` | Hedef Java ortamı |
| **compileSdk / targetSdk** | `37` | Hedef SDK |
| **minSdk** | `24` | Asgari SDK |
| **Compose BOM** | `2026.09.00` | Compose 1.12.x eşlemesi |
| **material3** | `1.4.0` | Stabil |
| **material3-adaptive** | `1.3.0` | Adaptif düzenler |
| **core-ktx** | `1.19.1` | AndroidX çekirdek eklentisi |
| **lifecycle** | `2.11.0` | Yaşam döngüsü yönetimi |
| **activity** | `1.13.0` | Activity Compose entegrasyonu |

> [!WARNING]
> `material3`'ün "expressive" API'leri 1.4.0'dan ÇIKARILDI ve hiç stabil olmadı. Kullanma. "Compose 3" diye bir sürüm yok — kastedilen Material 3'tür.

## Resmî Kaynaklar (Yalnızca Bunları Kullan)

### Android (developer.android.com)
- https://developer.android.com/build/releases/gradle-plugin
- https://developer.android.com/develop/ui/compose/bom/bom-mapping
- https://developer.android.com/jetpack/androidx/releases/compose-material3
- https://developer.android.com/jetpack/androidx/versions
- https://developer.android.com/jetpack/androidx/releases/core
- https://developer.android.com/jetpack/androidx/releases/lifecycle
- https://developer.android.com/jetpack/androidx/releases/activity

### Kotlin
- https://kotlinlang.org/docs/releases.html

### Material 3
- https://m3.material.io/blog/material-is-compose-first
- https://fonts.google.com/icons

### Resmî Örnek Depolar (Kalıp İçin — Sürüm Kaynağı DEĞİL)
- https://github.com/android/nowinandroid
- https://github.com/android/compose-samples
- https://github.com/android/architecture-samples
- https://github.com/androidx/androidx

> [!IMPORTANT]
> Blog, forum, Medium, StackOverflow sürüm kaynağı DEĞİLDİR. Sürüm için yalnız yukarıdaki resmî sayfalar kullanılır. Bir örnek depodan kod kalıbı alabilirsin ama sürümünü alma.
