---
title: Android Teknoloji Yığını ve Sabitlenmiş Sürümler
tags: [stack, surum]
last_reviewed: 2026-10-11
confidence: VERIFIED
verification_mode: claim_level
claims:
  - id: claim-agp-version
    claim: "Android Gradle Plugin (AGP) sürümü 9.4.0'dır."
    source_url: https://developer.android.com/build/releases/gradle-plugin
    verified_at: 2026-10-11
    review_at: 2027-01-10
    confidence: VERIFIED
    evidence: "Android Gradle plugin 9.4.0 (September 2026)"

  - id: claim-gradle-version
    claim: "AGP 9.4.0 ile asgari uyumlu Gradle sürümü 9.6.0'dır."
    source_url: https://developer.android.com/build/releases/gradle-plugin
    verified_at: 2026-10-11
    review_at: 2027-01-10
    confidence: VERIFIED
    evidence: "<td align=\"center\">9.6.0</td>"

  - id: claim-jdk-version
    claim: "Derleme için gerekli JDK hedef ortamı JDK 17'dir."
    source_url: https://developer.android.com/build/releases/gradle-plugin
    verified_at: 2026-10-11
    review_at: 2027-01-10
    confidence: VERIFIED
    evidence: "JDK 17 17 To learn more, see setting the JDK version"

  - id: claim-kotlin-version
    claim: "Kotlin temel dil sürümü 2.4.21'dir."
    source_url: https://kotlinlang.org/docs/releases.html
    verified_at: 2026-10-11
    review_at: 2027-01-10
    confidence: VERIFIED
    evidence: "To update to Kotlin 2.4.21, change the version of the Kotlin plugin"

  - id: claim-compile-target-sdk
    claim: "compileSdk ve targetSdk hedef API seviyesi 37'dir."
    source_url: https://developer.android.com/build/releases/gradle-plugin
    verified_at: 2026-10-11
    review_at: 2027-01-10
    confidence: VERIFIED
    evidence: "The maximum API level that Android Gradle plugin 9.4 supports is API level 37."

  - id: claim-min-sdk
    claim: "minSdk asgari desteklenen sürüm 24 (Android 7.0)'tür."
    source_url: PROJE_KARARI
    verified_at: null
    review_at: 2027-01-10
    confidence: PROJE_KARARI
    evidence: "Depo içi hedef kitle kararı, dış doğrulama yok."

  - id: claim-compose-bom
    claim: "Compose BOM sürümü 2026.09.00 olup Compose 1.12.x kütüphanelerine eşlenir."
    source_url: https://developer.android.com/develop/ui/compose/bom/bom-mapping
    verified_at: 2026-10-11
    review_at: 2027-01-10
    confidence: VERIFIED
    evidence: "<option selected>2026.09.00</option> -> Compose 1.12.1"

  - id: claim-material3-version
    claim: "Material 3 (androidx.compose.material3) stabil sürümü 1.4.0'dır."
    source_url: https://developer.android.com/jetpack/androidx/releases/compose-material3
    verified_at: 2026-10-11
    review_at: 2027-01-10
    confidence: VERIFIED
    evidence: "androidx.compose.material3:material3:1.4.0"

  - id: claim-material3-no-expressive
    claim: "Material 3 expressive API'leri 1.4.0 sürümünden çıkarılmıştır, stabil değildir."
    source_url: https://developer.android.com/jetpack/androidx/releases/compose-material3
    verified_at: 2026-10-11
    review_at: 2027-01-10
    confidence: VERIFIED
    evidence: "Expressive API adjustments in preview/alpha, excluded from 1.4.0 stable."

  - id: claim-material3-adaptive
    claim: "material3-adaptive sürümü 1.3.0'dır."
    source_url: https://developer.android.com/jetpack/androidx/releases/compose-material3-adaptive
    verified_at: 2026-10-11
    review_at: 2027-01-10
    confidence: VERIFIED
    evidence: "androidx.compose.material3.adaptive:adaptive-*:1.3.0"

  - id: claim-core-ktx
    claim: "core-ktx sürümü 1.19.1'dir."
    source_url: https://developer.android.com/jetpack/androidx/releases/core
    verified_at: 2026-10-11
    review_at: 2027-01-10
    confidence: VERIFIED
    evidence: "androidx.core:core:1.19.1 / core-ktx:1.19.1"

  - id: claim-lifecycle
    claim: "lifecycle (lifecycle-runtime-ktx) sürümü 2.11.0'dır."
    source_url: https://developer.android.com/jetpack/androidx/releases/lifecycle
    verified_at: 2026-10-11
    review_at: 2027-01-10
    confidence: VERIFIED
    evidence: "androidx.lifecycle:lifecycle-runtime-ktx:2.11.0"

  - id: claim-activity
    claim: "activity (activity-compose) sürümü 1.13.0'dır."
    source_url: https://developer.android.com/jetpack/androidx/releases/activity
    verified_at: 2026-10-11
    review_at: 2027-01-10
    confidence: VERIFIED
    evidence: "androidx.activity:activity-compose:1.13.0"
---

# Android Teknoloji Yığını ve Sabitlenmiş Sürümler

Bu nottaki her teknik parametre bağımsız bir **iddia (claim)** olarak modellenmiş olup kendi resmî kaynağından ayrı ayrı doğrulanmaktadır.

## Sabitlenmiş İddialar Tablosu

| İddia ID | Bileşen / Kütüphane | Sürüm | Kaynak (`source_url`) | Durum | `verified_at` | Kanıt / Not |
| :--- | :--- | :---: | :--- | :---: | :---: | :--- |
| `claim-agp-version` | **AGP** | `9.4.0` | `developer.android.com/build/releases/gradle-plugin` | `VERIFIED` | 2026-10-11 | Android Gradle plugin 9.4.0 (September 2026) |
| `claim-gradle-version` | **Gradle (min uyumlu)** | `9.6.0` | `developer.android.com/build/releases/gradle-plugin` | `VERIFIED` | 2026-10-11 | `<td align="center">9.6.0</td>` |
| `claim-jdk-version` | **JDK** | `17` | `developer.android.com/build/releases/gradle-plugin` | `VERIFIED` | 2026-10-11 | JDK 17 uyumluluğu |
| `claim-kotlin-version` | **Kotlin** | `2.4.21` | `kotlinlang.org/docs/releases.html` | `VERIFIED` | 2026-10-11 | Kotlin 2.4.21 sürüm notları |
| `claim-compile-target-sdk` | **compileSdk / targetSdk** | `37` | `developer.android.com/build/releases/gradle-plugin` | `VERIFIED` | 2026-10-11 | "AGP 9.4 supports API level 37" |
| `claim-min-sdk` | **minSdk** | `24` | `PROJE_KARARI` | `PROJE KARARI` | - | Depo içi hedef kitle kararı |
| `claim-compose-bom` | **Compose BOM** | `2026.09.00` | `developer.android.com/develop/ui/compose/bom/bom-mapping` | `VERIFIED` | 2026-10-11 | Compose 1.12.1 eşleşmesi |
| `claim-material3-version` | **material3** | `1.4.0` | `developer.android.com/jetpack/androidx/releases/compose-material3` | `VERIFIED` | 2026-10-11 | Stabil sürüm |
| `claim-material3-no-expressive` | **expressive API** | Yok | `developer.android.com/jetpack/androidx/releases/compose-material3` | `VERIFIED` | 2026-10-11 | 1.4.0'dan çıkarıldı |
| `claim-material3-adaptive` | **material3-adaptive** | `1.3.0` | `developer.android.com/jetpack/androidx/releases/compose-material3-adaptive` | `VERIFIED` | 2026-10-11 | androidx/versions üzerinden bulundu |
| `claim-core-ktx` | **core-ktx** | `1.19.1` | `developer.android.com/jetpack/androidx/releases/core` | `VERIFIED` | 2026-10-11 | Core KTX 1.19.1 |
| `claim-lifecycle` | **lifecycle** | `2.11.0` | `developer.android.com/jetpack/androidx/releases/lifecycle` | `VERIFIED` | 2026-10-11 | Lifecycle Runtime KTX 2.11.0 |
| `claim-activity` | **activity** | `1.13.0` | `developer.android.com/jetpack/androidx/releases/activity` | `VERIFIED` | 2026-10-11 | Activity Compose 1.13.0 |

> [!WARNING]
> `material3`'ün "expressive" API'leri 1.4.0'dan ÇIKARILDI ve hiç stabil olmadı. Kullanma. "Compose 3" diye bir sürüm yok — kastedilen Material 3'tür.
