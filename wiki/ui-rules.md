---
title: UI Kuralları ve Yasaklar
tags: [ui, yasak]
last_reviewed: 2026-10-11
confidence: PARTIAL
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
    source_url: null
    verified_at: null
    review_at: 2027-01-10
    confidence: PROJE_KARARI
    status: "PROJE KARARI"
    evidence: "Dahili tasarım sistemi ve token izolasyonu kuralı; dış doğrulama iddia edilmez."

  - id: claim-ui-mandatory-preview
    claim: "Oluşturulan her Composable fonksiyonun bağımsız bir @Preview fonksiyonu olmak zorundadır."
    source_url: null
    verified_at: null
    review_at: 2027-01-10
    confidence: PROJE_KARARI
    status: "PROJE KARARI"
    evidence: "kaynak özelliği tanıtıyor, zorunluluk bizim kararımız"

  - id: claim-ui-strings-xml
    claim: "UI içinde doğrudan sabit metin (hardcoded text) yazılamaz; tüm metinler res/values/strings.xml dosyasından stringResource ile çekilir."
    source_url: null
    verified_at: null
    review_at: 2027-01-10
    confidence: PROJE_KARARI
    status: "PROJE KARARI"
    evidence: "kaynak özelliği tanıtıyor, zorunluluk bizim kararımız"
---

# UI Kuralları ve Yasaklar

Bu dosya, Jetpack Compose arayüz geliştirmelerinde uyulması zorunlu tasarım, token ve mimari kurallarını içerir.

---

## 1. Resmî Kaynaklardan Doğrulanan Kurallar

| İddia ID | Kural / İlke | Doğrulama Kaynağı | Durum | Doğrulama Notu |
| :--- | :--- | :--- | :---: | :--- |
| `claim-ui-no-material-icons` | `material-icons-*` kullanımı yasaktır (kaldırıldı). | `developer.android.com/jetpack/androidx/releases/compose-material3` | **AYNI (kaynak düzeltildi)** | Kütüphane sürüm notlarında resmî kaldırma duyurusu. |
| `claim-ui-material-symbols-xml` | İkonlar Material Symbols XML olarak `res/drawable` altına eklenir. | `developer.android.com/jetpack/androidx/releases/compose-material3` | **AYNI (kaynak düzeltildi)** | fonts.google.com/icons yönlendirmesi teyitli. |

---

## 2. Proje Kuralları (Dış Doğrulama Yok — Dahili Standartlar)

| İddia ID | Kural İfadesi | Kaynak | Durum | Gerekçe / Standart |
| :--- | :--- | :---: | :---: | :--- |
| `claim-ui-theme-raw-values` | **Ham renk (`Color(0xFF..)`) ve ham dp/sp YALNIZ `ui/theme/` altında tanımlanır (`Color.kt`, `Type.kt`, `Dimens.kt`). Composable'ların içinde yasak; oralarda `MaterialTheme.colorScheme` / `MaterialTheme.typography` / `Dimens` kullanılır.** | - | `PROJE KARARI` | Token izolasyonu ve tema tutarlılığı. |
| `claim-ui-mandatory-preview` | **Her Composable fonksiyon için açık ve koyu tema durumlarını test eden bağımsız bir `@Preview` yazılmalıdır.** | - | `PROJE KARARI` | Kaynak özelliği tanıtıyor, zorunluluk bizim kararımız. |
| `claim-ui-strings-xml` | **UI içinde tırnak içinde sabit metin (hardcoded) yazılamaz; metinler `res/values/strings.xml` üzerinden `stringResource` ile çekilir.** | - | `PROJE KARARI` | Kaynak özelliği tanıtıyor, zorunluluk bizim kararımız. |

> [!IMPORTANT]
> Yukarıdaki 3 kural deponun dahili geliştirme disiplinidir; dış kaynak iddia edilmez.
