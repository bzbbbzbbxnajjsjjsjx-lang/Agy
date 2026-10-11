---
name: tazele
description: Bir veya daha fazla wiki iddiasını (claim) doğrudan kendi resmî kaynağından doğrular ve tazeler.
---

# Tazele Komutu Talimatı (İddia ve Satır Bazlı)

Wiki iddialarını resmî kaynaklarından doğrulamak için şu adımları sırayla uygula:

## 1. Hedef İddiayı / Notu Seç
- Belirli bir iddia veya bir not dosyasındaki tüm iddialar/satırlar hedeflenir.
- Her iddia için kendi `source_url` alanını oku.
- Kaynağı olmayan (proje kararı vb.) iddialar dış doğrulamaya tabi tutulmaz.

## 2. Kaynağa Eriş
- İddianın doğrudan `source_url` adresine eriş (web aracı veya `curl -sL <url>`; geçici dosya gerekiyorsa `$HOME` kullan, `/tmp` kullanma).
- Kaynağa erişilemezse sonuç: `ERİŞİLEMEDİ` — iddia üzerinde hiçbir değişiklik yapma!

## 3. İddiayı Sayfayla Karşılaştır
- Sayfa içeriğini tara ve yalnızca iddianın belirttiği somut değeri / kuralı ara.
- Sayfada bulamazsan sonuç: `BULUNAMADI` — değeri değiştirme, işaretle.

## 4. İddia Sonucunu Belirle
- **AYNI:** İddiadaki değer kaynakta aynen geçerli ise iddia içeriğini değiştirme, yalnız `verified_at` tarihini bugüne çek.
- **AYNI (kaynak düzeltildi):** Değer aynıdır, sadece daha doğru/spesifik bir resmî kaynağa geçilmiştir. Bu `FARKLI` değildir. Kaynak adresi güncellenir ve `verified_at` bugüne çekilir.
- **FARKLI:** Sayfadaki değer iddiadan farklıysa önce farkı raporla (eski → yeni). Ardından iddiayı yeni değerle güncelle, `source_url`'yi teyit ettiğin sayfaya çevir ve `verified_at` tarihini bugüne çek.
- **BULUNAMADI / ERİŞİLEMEDİ:** İddiada hiçbir şeyi değiştirme, durumu rapora ve "Doğrulayamadıklarım" bölümüne ekle.

## 5. Şeffaf Raporlama ve Sayı Kontrolü
- Raporda zorunlu satır: `"Kontrol edilen iddia: X / toplam iddia: Y"`
- Eğer `X < Y` ise kontrol edilmeyen iddialar açıkça listelenir ve `verified_at` onlar için **güncellenmez**.
- Her satır için: `iddia | kaynak | durum` tablosunu üret.
