# Teknik Brief Şablonu

Bu dizin, `GOREV.md` içindeki göreve yönelik üretilen teknik brief'leri barındırır. Her brief `briefs/<tarih>-<kisa-ad>.md` biçiminde adlandırılır.

## Şablon

```markdown
# Teknik Brief: [Görev / Modül Başlığı]
Tarih: YYYY-MM-DD
Kaynak Görev: GOREV.md

## 1. Kapsam ve Hedef
- Görevin amacı ve beklenen işlevsellik.

## 2. Sabitlenmiş Bağımlılıklar ve Sürümler
(Sürüm tablosu brief üretilirken wiki/android-stack.md'den kopyalanır; şablon sürüm içermez.)
- Kotlin: <wiki'den>
- AGP: <wiki'den>
- Compose BOM: <wiki'den>
- Material 3: <wiki'den>
- compileSdk / targetSdk: <wiki'den>
- minSdk: <wiki'den>

## 3. UI ve Mimari Kısıtlar
- Material Symbols XML kullanımı (material-icons yasak).
- MaterialTheme.colorScheme ve semantik tokenlar (ham hex / dp yasak).
- Her Composable için @Preview.
- Tüm metinler strings.xml üzerinden.
- Composable içinde I/O veya ağ çağrısı yok.

## 4. Kabul Kriterleri ve Doğrulama Adımları
- libs.versions.toml eşlemesi.
- ./gradlew assembleDebug doğrulaması.
```
