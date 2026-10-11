---
name: tazele
description: Bir wiki notunu resmî kaynağından yeniden doğrular ve gerekirse günceller.
---

# Tazele Komutu Talimatı

Bir wiki notunu resmî kaynağından doğrulamak için şu adımları sırayla uygula:

1. **Notu Oku:**
   - İlgili wiki notunu oku ve `source_url` alanını al.
   - Kaynak URL yoksa işlemi hemen durdur ve raporla: `"bu notun kaynağı yok, doğrulanamaz"`.

2. **Kaynağa Eriş:**
   - Sayfa içeriğine web aracı ile eriş (mümkün değilse `curl -sL <url>` kullan; geçici dosya gerekiyorsa `$HOME` kullan, `/tmp` kullanma).
   - Kaynağa erişilemezse sonuç: `ERİŞİLEMEDİ` — notta hiçbir değişiklik yapma!

3. **İddiaları Karşılaştır:**
   - Nottaki iddiaları ve değerleri resmî sayfadaki karşılıklarıyla karşılaştır.
   - Sadece notta YAZAN değerleri kontrol et; sayfada gördüğün yeni özellikleri nota ekleme.

4. **Sonucu Belirle:**
   - **AYNI:** Bilgiler tam örtüşüyorsa, not içeriğine dokunma, yalnız `verified_at` tarihini bugüne çek.
   - **FARKLI:** Önce tespit edilen farkı raporla (eski → yeni). Ardından notu yeni değerle güncelle, `source_url`'yi teyit ettiğin sayfaya çevir ve `verified_at` tarihini bugüne çek.
   - **ERİŞİLEMEDİ:** Notta ve frontmatter'da hiçbir şeyi değiştirme, durumu "erişilemedi" olarak raporla.

5. **Şeffaf Raporlama:**
   - Yaptığın her değişikliği (değişen alanlar, eski ve yeni değerler) raporunda tek tek açıkça listele.
