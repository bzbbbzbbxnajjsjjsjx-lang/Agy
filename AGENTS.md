# Android Geliştirme Kuşak (Harness) Sistemi

Bu depo, Android uygulamaları üretmek amacıyla tasarlanmış; bağlam, kanonik wiki ve kontrollü geliştirme döngüsünü merkezine alan disiplinli bir ajan çalışma alanıdır. Sistem; eğitim verisi varsayımlarını engelleyerek kararları, sürümleri ve mimari standartları tekilleştirilmiş wiki notları üzerinden yönetir ve her görev döngüsünde doğrulanmış adımlarla ilerler.

## Değişmez Kurallar

1. **SÜRÜM NUMARASI HAFIZADAN YAZILMAZ.** Eğitim verin eskidir. Yalnız bu dosyadaki ya da `wiki/` içindeki kaynaklı değerleri kullan. Kaynağı yoksa "bu bilgi wiki'de yok" de, uydurma.
2. **Her wiki İDDİASINDA `source_url`, `verified_at`, `review_at` ZORUNLU.** Doğrulama ve tazelik kontrolleri kaba bir not bazında değil, **iddia (claim) düzeyinde atomik** yürütülür. Kaynağı olmayan iddia `confidence: VERIFIED` olamaz; en fazla `UNVERIFIED` olur.
3. **DOĞRULAMADIĞINI DOĞRULANMIŞ GİBİ YAZMA.** Derlemediysen "derlemedim" yaz. Test çalıştırmadıysan "test etmedim" yaz. Emin olmadığın şeyi "oldu" diye kapatma.
4. **AYNI BİLGİYİ İKİ YERE YAZMA.** Tek kopya. İkinci kopya zamanla ayrışır.
5. **Uzun metni terminale yapıştırmıyorum.** Talimatları dosyadan oku.
6. **Bir şeyi değiştirdiysen neyi değiştirdiğini söyle.** Sessiz değişiklik yok.

## Geliştirme Döngüsü

Her görev şu sıra izlenerek yürütülür:
```text
görev → bağlam → brief → uygula → doğrula → wiki'ye yaz
```
1. **Görev:** `GOREV.md` dosyasından görevin kapsamını ve hedefini oku.
2. **Bağlam:** `wiki/` altındaki ilgili notları tara, kısıtları ve standartları belirle.
3. **Brief:** Yapılacak işin planını ve kabul kriterlerini netleştir.
4. **Uygula:** Kod ve konfigürasyon değişikliklerini kurallara uygun biçimde hayata geçir.
5. **Doğrula:** Derleme (`./gradlew assembleDebug`), test veya statik analiz ile doğrula; neyin yapılıp neyin yapılamadığını açıkça raporla.
6. **Wiki'ye Yaz:** Edinilen kalıcı tecrübeyi veya güncellenen kararları ilgili tekil wiki sayfasına işle.

## Zorunlu Okumalar ve Yönlendirmeler

- Sürüm veya bağımlılık sorusu gelirse önce `wiki/android-stack.md` oku.
- UI yazacaksan önce `wiki/ui-rules.md` oku.
- Kabul kriterleri ve kalite kontrolleri için `wiki/kabul-kriterleri.md` oku.

## Yeni Bilgi Nasıl Girer

- Yeni bir Android bilgisi öğrendiysen (sürüm, API değişikliği, kaldırılan özellik), ilgili `wiki/` notuna atomik bir `claim` (iddia) olarak ekle. İddia bazında kaynak linki (`source_url`) + tarih (`verified_at`, `review_at`) ZORUNLU.
- Kaynağı olmayan iddia `confidence: UNVERIFIED` olarak girer ve brief'lerde "doğrulanmamış" etiketiyle çıkar.
- Aynı konuda iddia güncellenirse yenisini ekle, eskisine `superseded_by: <yeni-iddia-id>` ata.

