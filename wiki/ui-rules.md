---
title: UI Kuralları ve Yasaklar
tags: [ui, yasak]
source_url: https://m3.material.io/blog/material-is-compose-first
verified_at: 2026-10-12
review_at: 2027-01-10
confidence: VERIFIED
---

# UI Kuralları ve Yasaklar

Bu dosya, Jetpack Compose arayüz geliştirmelerinde uyulması zorunlu tasarım, token ve mimari yasaklarını içerir.

## Yasaklar ve Zorunlu Alternatifleri

1. **İkon Yasakları:**
   - `material-icons-core` ve `material-icons-extended` kütüphaneleri **kullanılamaz** (kaldırılmıştır).
   - **Zorunlu Yol:** İkonlar için vektör formatında Material Symbols XML indirilir (`https://fonts.google.com/icons`) ve projede `res/drawable` altına eklenerek kullanılır.

2. **Renk Yasakları:**
   - Bileşenlerde doğrudan ham hex renk kodları (`Color(0xFF...)`) **kullanılamaz**.
   - **Zorunlu Yol:** Tüm renkler `MaterialTheme.colorScheme` semantik rolleri (Primary, Secondary, Tertiary, Surface, Error, Container vb.) üzerinden atanır.

3. **Ölçü (dp/sp) Yasakları:**
   - Ham dp veya sp sabitleri serbestçe koda gömülemez.
   - **Zorunlu Yol:** Tasarım sistemi ve tema tokenları (spacing/padding ölçekleri, tipografi tokenları) üzerinden kullanılır.

4. **Composable Önizleme Zorunluluğu:**
   - Oluşturulan her Composable fonksiyonun bağımsız bir `@Preview` fonksiyonu olmak **zorundadır**. Önizlemeler hem açık hem koyu tema durumlarını doğrulamalıdır.

5. **Metin ve Yerelleştirme Zorunluluğu:**
   - UI içinde doğrudan tırnak içinde sabit string ("hardcoded text") **yazılamaz**.
   - **Zorunlu Yol:** Tüm metinler `res/values/strings.xml` içine tanımlanmalı ve `stringResource(R.string...)` ile çağrılmalıdır.
