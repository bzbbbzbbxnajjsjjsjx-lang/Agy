---
title: UI Kuralları ve Yasaklar
tags: [ui, yasak]
last_reviewed: 2026-10-11
confidence: VERIFIED
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

  - id: claim-ui-no-raw-hex
    claim: "Bileşenlerde doğrudan ham hex renk kodları (Color(0xFF...)) kullanılamaz; MaterialTheme.colorScheme semantik rolleri kullanılır."
    source_url: https://m3.material.io/blog/material-is-compose-first
    verified_at: 2026-10-11
    review_at: 2027-01-10
    confidence: VERIFIED
    status: VERIFIED
    evidence: "Material 3 color roles and tokens"

  - id: claim-ui-token-spacing
    claim: "Ham dp/sp sabitleri kod içine serbestçe gömülemez; tasarım sistemi ve tema tokenları üzerinden kullanılır."
    source_url: https://m3.material.io/blog/material-is-compose-first
    verified_at: 2026-10-11
    review_at: 2027-01-10
    confidence: VERIFIED
    status: VERIFIED
    evidence: "Spacing, typography and shape tokens standard"

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

Bu dosya, Jetpack Compose arayüz geliştirmelerinde uyulması zorunlu tasarım, token ve mimari yasaklarını iddia bazında içerir.

## İddia Bazlı Kural Tablosu

| İddia ID | Kural / Yasak | Kaynak (`source_url`) | Durum | `verified_at` |
| :--- | :--- | :--- | :---: | :---: |
| `claim-ui-no-material-icons` | `material-icons-*` kullanımı yasaktır (kaldırıldı). | `developer.android.com/jetpack/androidx/releases/compose-material3` | **AYNI (kaynak düzeltildi)** | 2026-10-11 |
| `claim-ui-material-symbols-xml` | İkonlar Material Symbols Vector Drawable XML olarak `res/drawable` altına eklenir. | `developer.android.com/jetpack/androidx/releases/compose-material3` | **AYNI (kaynak düzeltildi)** | 2026-10-11 |
| `claim-ui-no-raw-hex` | Ham hex `Color(0xFF...)` yasaktır; semantik renk rolleri kullanılır. | `m3.material.io/blog/material-is-compose-first` | `VERIFIED` | 2026-10-11 |
| `claim-ui-token-spacing` | Ham `dp`/`sp` sabitleri yasaktır; tema ve ölçek tokenları kullanılır. | `m3.material.io/blog/material-is-compose-first` | `VERIFIED` | 2026-10-11 |
| `claim-ui-mandatory-preview` | Her Composable için açık/koyu `@Preview` zorunludur. | `developer.android.com/develop/ui/compose/tooling/previews` | `VERIFIED` | 2026-10-11 |
| `claim-ui-strings-xml` | Sabit metin (hardcoded) yasaktır; `stringResource(R.string...)` zorunludur. | `developer.android.com/guide/topics/resources/string-resource` | `VERIFIED` | 2026-10-11 |
