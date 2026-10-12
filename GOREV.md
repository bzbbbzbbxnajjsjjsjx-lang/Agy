# Görev: Kişisel Bilgi Tabanlı Not Asistanı Dönüşümü (Aşama A)

## Görev Özeti
`app/` modülü dönüştürülerek temel not uygulaması, kişisel bilgi tabanlı not asistanına evrilir:

1. **Ana Ekran (Sohbet):**
   - Kullanıcı mesaj yazar, ajan yanıt verir.
   - Material 3 mesaj listesi, giriş metin alanı ve gönder butonu.
2. **Mesaj Modeli:**
   - Rol (kullanıcı / asistan), metin ve zaman damgası içeren veri modeli (`ChatMessage`, `MessageRole`).
3. **Model İstemcisi:**
   - `ModelClient` arayüzü ve sabit yanıtlı `FakeModelClient` (kısa gecikmeli simülasyon).
4. **ViewModel & Durum Yönetimi:**
   - `ChatViewModel` + `StateFlow` ile durum yönetimi.
   - Gönderilen mesaj listeye eklenir, ardından asistan yanıtı listeye eklenir.
   - Ekran döndürmede (configuration change) mesajlar ve giriş durumu korunur.
5. **Gezinme (Navigation):**
   - Üst sekmeler (`TabRow`) ile Sohbet ve Notlar ekranları arasında akıcı geçiş.
   - Mevcut Notlar ve Ayarlar ekranlarının işlevselliği korunur.
6. **Birim Testi:**
   - Mesaj gönderildiğinde listeye iki mesaj (kullanıcı + asistan) eklendiğini doğrulayan birim testi.
7. **UI ve Mimari Kısıtları:**
   - Ham renk ve dp/sp Composable içinde kullanılmaz; `Dimens` ve tema tokenları kullanılır.
   - Kullanıcıya görünen tüm metinler `strings.xml` üzerinden çekilir.
   - Her Composable için bağımsız `@Preview` fonksiyonu bulunur.
   - Yeni harici bağımlılık eklenmez.

## Bitirme Komutu
```bash
git add -A && git commit -m "feat: kisisel bilgi tabanli not asistani (Asama A - sohbet, model client, test)" && git push
```
