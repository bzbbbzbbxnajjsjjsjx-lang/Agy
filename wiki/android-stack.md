---
title: Android Teknoloji Yığını ve Sabitlenmiş Sürümler
tags: [stack, surum]
source_url: https://developer.android.com/build/releases/gradle-plugin
verified_at: 2026-10-11 # En eski satır doğrulama tarihi (satır bazında doğrulama)
review_at: 2027-01-10
confidence: PARTIAL
verification_mode: claim_level
claims:
  - id: claim-agp-version
    claim: "Android Gradle Plugin (AGP) sabitlenmiş sürümü 9.4.0'dır."
    source_url: https://developer.android.com/build/releases/gradle-plugin
    verified_at: 2026-10-11
    review_at: 2027-01-10
    confidence: VERIFIED
    status: VERIFIED
    evidence: "Android Gradle plugin 9.4.0 (September 2026)"

  - id: claim-gradle-version
    claim: "AGP 9.4.0 ile asgari uyumlu Gradle sürümü 9.6.0'dır."
    source_url: https://developer.android.com/build/releases/gradle-plugin
    verified_at: 2026-10-11
    review_at: 2027-01-10
    confidence: VERIFIED
    status: VERIFIED
    evidence: "<td align=\"center\">9.6.0</td>"

  - id: claim-jdk-version
    claim: "Derleme için gerekli JDK hedef ortamı JDK 17'dir."
    source_url: https://developer.android.com/build/releases/gradle-plugin
    verified_at: 2026-10-11
    review_at: 2027-01-10
    confidence: VERIFIED
    status: VERIFIED
    evidence: "JDK 17 17 To learn more, see setting the JDK version"

  - id: claim-kotlin-version
    claim: "Kotlin temel dil sürümü 2.4.21'dir."
    source_url: https://kotlinlang.org/docs/releases.html
    verified_at: 2026-10-11
    review_at: 2027-01-10
    confidence: VERIFIED
    status: VERIFIED
    evidence: "To update to Kotlin 2.4.21, change the version of the Kotlin plugin"

  - id: claim-compose-bom
    claim: "Compose BOM sürümü 2026.09.00 olup Compose 1.12.x kütüphanelerine eşlenir."
    source_url: https://developer.android.com/develop/ui/compose/bom/bom-mapping
    verified_at: 2026-10-11
    review_at: 2027-01-10
    confidence: VERIFIED
    status: VERIFIED
    evidence: "<option selected>2026.09.00</option> (Compose 1.12.1 eşlemesi)"

  - id: claim-material3-version
    claim: "Material 3 (androidx.compose.material3) stabil sürümü 1.4.0'dır."
    source_url: https://developer.android.com/jetpack/androidx/releases/compose-material3
    verified_at: 2026-10-11
    review_at: 2027-01-10
    confidence: VERIFIED
    status: VERIFIED
    evidence: "androidx.compose.material3:material3:1.4.0"

  - id: claim-material3-adaptive
    claim: "material3-adaptive sürümü 1.3.0'dır."
    source_url: https://developer.android.com/jetpack/androidx/releases/compose-material3-adaptive
    verified_at: 2026-10-11
    review_at: 2027-01-10
    confidence: VERIFIED
    status: VERIFIED
    evidence: "androidx/versions dizini üzerinden bulundu: adaptive-*:1.3.0"

  - id: claim-core-ktx
    claim: "core-ktx sürümü 1.19.1'dir."
    source_url: https://developer.android.com/jetpack/androidx/releases/core
    verified_at: 2026-10-11
    review_at: 2027-01-10
    confidence: VERIFIED
    status: VERIFIED
    evidence: "androidx.core:core:1.19.1"

  - id: claim-lifecycle
    claim: "lifecycle (lifecycle-runtime-ktx) sürümü 2.11.0'dır."
    source_url: https://developer.android.com/jetpack/androidx/releases/lifecycle
    verified_at: 2026-10-11
    review_at: 2027-01-10
    confidence: VERIFIED
    status: VERIFIED
    evidence: "androidx/releases/lifecycle#2.11.0"

  - id: claim-activity
    claim: "activity (activity-compose) sürümü 1.13.0'dır."
    source_url: https://developer.android.com/jetpack/androidx/releases/activity
    verified_at: 2026-10-11
    review_at: 2027-01-10
    confidence: VERIFIED
    status: VERIFIED
    evidence: "androidx.activity:activity-compose:1.13.0"

  - id: claim-compile-target-sdk
    claim: "compileSdk ve targetSdk hedef API seviyesi 37'dir."
    source_url: null
    verified_at: null
    review_at: 2027-01-10
    confidence: PROJE_KARARI
    status: "PROJE KARARI"
    evidence: "Sayfada 'AGP 9.4 supports API level 37' desteği teyitli; 37 seçimi proje kararıdır."

  - id: claim-min-sdk
    claim: "minSdk asgari desteklenen sürüm 24 (Android 7.0)'tür."
    source_url: null
    verified_at: null
    review_at: 2027-01-10
    confidence: PROJE_KARARI
    status: "PROJE KARARI"
    evidence: "Depo içi hedef kitle kararı, dış doğrulama yok."
---

# Android Teknoloji Yığını ve Sabitlenmiş Sürümler

Doğrulama: Satır bazında münferit resmî kaynaklar üzerinden yürütülmüştür.  
Son Doğrulama: 2026-10-11 · Yeniden Doğrulama: 2027-01-10

## Sabitlenmiş Sürümler Tablosu

| Bileşen / Kütüphane | Sürüm | Kaynak | Durum | Not / Kapsam |
| :--- | :---: | :--- | :---: | :--- |
| **Android Gradle Plugin (AGP)** | `9.4.0` | `https://developer.android.com/build/releases/gradle-plugin` | `VERIFIED` | Sayfada: `Android Gradle plugin 9.4.0 (September 2026)` |
| **Gradle (min uyumlu)** | `9.6.0` | `https://developer.android.com/build/releases/gradle-plugin` | `VERIFIED` | Sayfada: `<td align="center">9.6.0</td>` |
| **JDK** | `17` | `https://developer.android.com/build/releases/gradle-plugin` | `VERIFIED` | Sayfada: `JDK 17 17 To learn more, see setting the JDK version` |
| **Kotlin** | `2.4.21` | `https://kotlinlang.org/docs/releases.html` | `VERIFIED` | Sayfada: `To update to Kotlin 2.4.21, change the version of the Kotlin plugin` |
| **Compose BOM** | `2026.09.00` | `https://developer.android.com/develop/ui/compose/bom/bom-mapping` | `VERIFIED` | Sayfada: `<option selected>2026.09.00</option>` (Compose 1.12.1 eşlemesi) |
| **material3** | `1.4.0` | `https://developer.android.com/jetpack/androidx/releases/compose-material3` | `VERIFIED` | Sayfada: `androidx.compose.material3:material3:1.4.0` |
| **material3-adaptive** | `1.3.0` | `https://developer.android.com/jetpack/androidx/releases/compose-material3-adaptive` | `VERIFIED` | `androidx/versions` dizininden bulundu; sayfada: `adaptive-*:1.3.0` |
| **core-ktx** | `1.19.1` | `https://developer.android.com/jetpack/androidx/releases/core` | `VERIFIED` | Sayfada: `androidx.core:core:1.19.1` |
| **lifecycle** | `2.11.0` | `https://developer.android.com/jetpack/androidx/releases/lifecycle` | `VERIFIED` | Sayfada: `androidx/releases/lifecycle#2.11.0` |
| **activity** | `1.13.0` | `https://developer.android.com/jetpack/androidx/releases/activity` | `VERIFIED` | Sayfada: `androidx.activity:activity-compose:1.13.0` |
| **compileSdk / targetSdk** | `37` | Proje kararı (AGP desteğine dayalı) | `PROJE KARARI` | Sayfada "AGP 9.4 supports API level 37" desteği yer alır; 37 seçimi proje kararıdır |
| **minSdk** | `24` | Proje kararı | `PROJE KARARI` | Asgari cihaz hedefleme kararıdır; dış doğrulama yoktur |

> [!WARNING]
> `material3`'ün "expressive" API'leri 1.4.0 stabil sürümünde yer almaz. Projede kullanılmamalıdır. "Compose 3" diye bir sürüm yoktur; kastedilen Material 3'tür.

## Resmî Kaynaklar

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
