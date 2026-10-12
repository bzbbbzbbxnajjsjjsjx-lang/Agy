# Teknik Brief: Kişisel Bilgi Tabanlı Not Asistanı (Aşama A)
Tarih: 2026-10-12
Kaynak Görev: GOREV.md

## 1. Kapsam ve Hedef
- Mevcut Notlar uygulamasını koruyarak üzerine "Kişisel Bilgi Tabanlı Not Asistanı" sohbet arayüzü ve altyapısı inşa etmek.
- Ana ekranda kullanıcı mesajı ve asistan yanıtını gösteren Sohbet (Chat) modülü kurmak.
- Asistan yanıtlarını simüle eden `ModelClient` ve `FakeModelClient` mimarisini oluşturmak.
- Üst sekmeler (`TabRow`) ile Sohbet ve Notlar modülleri arasında hızlı ve temiz gezinme sağlamak.
- Ekran rotasyonunda ve yapılandırma değişikliklerinde mesaj ve metin durumlarını korumak.
- Mesaj gönderildiğinde kullanıcı ve asistan mesajlarının eklendiğini doğrulayan birim testini eklemek.

## 2. Sabitlenmiş Bağımlılıklar ve Sürümler
(Sürümler yalnız wiki/android-stack.md üzerinden alınır, asla tahmin edilmez)
- Kotlin: 2.4.21
- AGP: 9.4.0
- Compose BOM: 2026.09.00
- Material 3: 1.4.0 (stabil)
- compileSdk / targetSdk: 37
- minSdk: 24
- core-ktx: 1.19.1
- lifecycle: 2.11.0
- activity-compose: 1.13.0

## 3. UI ve Mimari Kısıtlar
- `material-icons-*` kullanımı yasaktır; ikon yerine metin veya Material Symbols XML kullanılır.
- Composable içinde ham hex veya dp/sp yasaktır; `MaterialTheme` ve `Dimens` tokenları kullanılır.
- Kullanıcıya görünen tüm metinler `strings.xml` üzerinden `stringResource` ile çekilir.
- Her Composable fonksiyon için bağımsız `@Preview` fonksiyonu (Açık ve Koyu tema) zorunludur.
- Composable içinde doğrudan I/O veya uzun süreli hesaplama yapılamaz; `ChatViewModel` ve Coroutine Dispatchers kullanılır.

## 4. Kabul Kriterleri ve Doğrulama Adımları
- `scripts/check_claims.py` ve `scripts/test_check_claims.py` sıfır hata ve sıfır çelişkiyle tamamlanmalıdır.
- Sohbet ekranında mesaj gönderildiğinde ekrana kullanıcı mesajı düşmeli, ardından asistan yanıtı gelmelidir.
- Ekran döndürüldüğünde sohbet geçmişi kaybolmamalıdır.
- GitHub Actions CI üzerinde debug ve release APK derlemesi başarıyla tamamlanmalıdır.
