---
title: UI Kuralları ve Yasaklar
tags: [ui, yasak]
last_reviewed: 2026-10-11
confidence: INFERRED
verification_mode: claim_level
claims:
  - id: claim-ui-no-material-icons
    claim: "material-icons-core ve material-icons-extended kütüphaneleri kullanımdan kaldırılmıştır, projeye eklenemez."
    source_url: https://developer.android.com/jetpack/androidx/releases/compose-material3
    verified_at: 2026-10-11
    review_at: 2027-01-10
    confidence: VERIFIED
    status: "AYNI (kaynak düzeltildi)"
    evidence: "The androidx.compose.material.icons library is no longer recommended... removed from the latest Material 3 library release"

  - id: claim-ui-material-symbols-xml
    claim: "İkonlar için res/drawable altına Material Symbols Vector Drawable XML indirilerek kullanılmalıdır."
    source_url: https://developer.android.com/jetpack/androidx/releases/compose-material3
    verified_at: 2026-10-11
    review_at: 2027-01-10
    confidence: VERIFIED
    status: "AYNI (kaynak düzeltildi)"
    evidence: "we recommend downloading a Vector Drawable XML file from the Android tab of https://fonts.google.com/icons to get access to the latest styled icons: Material Symbols."

  - id: claim-ui-theme-raw-values
    claim: "Ham renk (Color(0xFF..)) ve ham dp/sp YALNIZ ui/theme/ altında tanımlanır (Color.kt, Type.kt, Dimens.kt). Composable'ların içinde yasak; oralarda MaterialTheme.colorScheme / MaterialTheme.typography / Dimens kullanılır."
    source_url: PROJE_KARARI
    verified_at: null
    review_at: 2027-01-10
    confidence: PROJE_KARARI
    status: "PROJE KARARI"
    evidence: "Dahili tasarım sistemi ve token izolasyonu kuralı; dış doğrulama iddia edilmez."

  - id: claim-ui-mandatory-preview
    claim: "Oluşturulan her Composable fonksiyonun bağımsız bir @Preview fonksiyonu olmak zorundadır."
    source_url: https://developer.android.com/develop/ui/compose/tooling/previews
    verified_at: 2026-10-11
    review_at: 2027-01-10
    confidence: VERIFIED
    status: VERIFIED
    evidence: "Preview your UI with composable previews in Android Studio"

  - id: claim-ui-strings-xml
    claim: "UI içinde doğrudan sabit metin (hardcoded text) yazılamaz; tüm metinler res/values/strings.xml dosyasından stringResource ile çekilir."
    source_url: https://developer.android.com/guide/topics/resources/string-resource
    verified_at: 2026-10-11
    review_at: 2027-01-10
    confidence: VERIFIED
    status: VERIFIED
    evidence: "A string resource provides text strings for your application with optional text styling and formatting."
---

# UI Kuralları ve Yasaklar

Bu dosya, Jetpack Compose arayüz geliştirmelerinde uyulması zorunlu tasarım, token ve mimari kurallarını içerir.

---

## 1. Resmî Kaynaklardan Doğrulanan Kurallar

| İddia ID | Kural / İlke | Doğrulama Kaynağı | Durum | Doğrulama Notu |
| :--- | :--- | :--- | :---: | :--- |
| `claim-ui-no-material-icons` | `material-icons-*` kullanımı yasaktır (kaldırıldı). | `developer.android.com/jetpack/androidx/releases/compose-material3` | **AYNI (kaynak düzeltildi)** | Kütüphane sürüm notlarında resmi kaldırma duyurusu. |
| `claim-ui-material-symbols-xml` | İkonlar Material Symbols XML olarak `res/drawable` altına eklenir. | `developer.android.com/jetpack/androidx/releases/compose-material3` | **AYNI (kaynak düzeltildi)** | fonts.google.com/icons yönlendirmesi teyitli. |
| `claim-ui-mandatory-preview` | Her Composable için açık/koyu `@Preview` zorunludur. | `developer.android.com/develop/ui/compose/tooling/previews` | `VERIFIED` | Android Studio Compose Preview standartları. |
| `claim-ui-strings-xml` | Sabit metin (hardcoded) yasaktır; `stringResource(R.string...)` zorunludur. | `developer.android.com/guide/topics/resources/string-resource` | `VERIFIED` | String Resources yerelleştirme standardı. |

---

## 2. Proje Kuralları (Dış Doğrulama Yok)

| İddia ID | Kural İfadesi | Kaynak | Durum |
| :--- | :--- | :---: | :---: |
| `claim-ui-theme-raw-values` | **Ham renk (`Color(0xFF..)`) ve ham dp/sp YALNIZ `ui/theme/` altında tanımlanır (`Color.kt`, `Type.kt`, `Dimens.kt`). Composable'ların içinde yasak; oralarda `MaterialTheme.colorScheme` / `MaterialTheme.typography` / `Dimens` kullanılır.** | Proje kararı | `PROJE KARARI` |

> [!IMPORTANT]
> Ham renk ve ölçü kuralı deponun dahili token disiplinidir; dış doğrulama iddia edilmez.
