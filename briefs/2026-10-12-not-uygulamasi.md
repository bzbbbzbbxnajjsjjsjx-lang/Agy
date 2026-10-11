# Teknik Brief: Ayarlar ve Tema Destekli Not Uygulaması

**Tarih:** 2026-10-12  
**Kaynak Görev:** Ayarlar ekranı olan, açık/koyu tema destekleyen tek ekranlı bir not uygulaması.  
**İlgili Wiki Notları:** [`wiki/android-stack.md`](../wiki/android-stack.md), [`wiki/ui-rules.md`](../wiki/ui-rules.md), [`wiki/kabul-kriterleri.md`](../wiki/kabul-kriterleri.md)

---

## 1. Kapsam ve Hedef

- **Ana Ekran:** Not listesi ve temel not ekleme/görüntüleme arayüzü.
- **Ayarlar Ekranı / Bölümü:** Kullanıcıya tema tercihi (Açık, Koyu, Sistem Varsayılanı) seçeneği sunan ayarlar görünümü.
- **Tema Entegrasyonu:** Dinamik renk veya sistem temasına uyumlu, `MaterialTheme.colorScheme` semantik rollerine dayalı açık ve koyu tema desteği.

---

## 2. Sabitlenmiş Bağımlılıklar ve Sürümler

*(Bu bölümdeki tüm sürüm değerleri doğrudan [`wiki/android-stack.md`](../wiki/android-stack.md) dosyasından alınmıştır. Wiki dışı hiçbir sürüm kullanılmamıştır.)*

| Bileşen | Sabitlenmiş Sürüm | Kaynak Not |
| :--- | :---: | :--- |
| **Android Gradle Plugin (AGP)** | `9.4.0` | [`wiki/android-stack.md`](../wiki/android-stack.md) |
| **Gradle** | `9.6.0` | [`wiki/android-stack.md`](../wiki/android-stack.md) |
| **Kotlin** | `2.4.21` | [`wiki/android-stack.md`](../wiki/android-stack.md) |
| **JDK** | `17` | [`wiki/android-stack.md`](../wiki/android-stack.md) |
| **compileSdk / targetSdk** | `37` | [`wiki/android-stack.md`](../wiki/android-stack.md) |
| **minSdk** | `24` | [`wiki/android-stack.md`](../wiki/android-stack.md) |
| **Compose BOM** | `2026.09.00` | [`wiki/android-stack.md`](../wiki/android-stack.md) |
| **material3** | `1.4.0` | [`wiki/android-stack.md`](../wiki/android-stack.md) *(stabil; BOM yönetir)* |
| **material3-adaptive** | `1.3.0` | [`wiki/android-stack.md`](../wiki/android-stack.md) |
| **core-ktx** | `1.19.1` | [`wiki/android-stack.md`](../wiki/android-stack.md) |
| **lifecycle** | `2.11.0` | [`wiki/android-stack.md`](../wiki/android-stack.md) |
| **activity** | `1.13.0` | [`wiki/android-stack.md`](../wiki/android-stack.md) |

---

## 3. UI ve Mimari Kısıtlar

1. **İkonlar:** `material-icons-core` / `-extended` kütüphaneleri **kullanılamaz**. Gerekli ikonlar için `https://fonts.google.com/icons` adresinden Material Symbols Vector Drawable XML indirilip `res/drawable` altına eklenir ([`wiki/ui-rules.md`](../wiki/ui-rules.md)).
2. **Renk ve Token Hijyeni:** Kod içinde ham hex (`Color(0xFF...)`) veya ham `dp`/`sp` sabitleri kullanılamaz. Tüm renkler `MaterialTheme.colorScheme` semantik rolleri üzerinden atanmalıdır.
3. **Önizleme (`@Preview`):** Üretilen tüm `@Composable` fonksiyonlar hem açık (`Light`) hem koyu (`Dark`) tema önizlemelerine sahip olmalıdır.
4. **Yerelleştirme:** Sabit string ifadeler doğrudan koda yazılamaz; tüm metinler `res/values/strings.xml` dosyasından `stringResource` ile çekilmelidir.
5. **Main Thread Hijyeni:** Composable fonksiyonlar içerisinde doğrudan dosya/veritabanı I/O veya ağ işlemleri yürütülemez; ViewModel ve Coroutine Dispatchers kullanılmalıdır ([`wiki/kabul-kriterleri.md`](../wiki/kabul-kriterleri.md)).
6. **Durum Korunumu:** Ekran rotasyonu veya süreç sonlanmalarında (process death) kullanıcı durumu `rememberSaveable` ve ViewModel StateFlow ile korunmalıdır.

---

## 4. Kabul Kriterleri ve Doğrulama Adımları

- [ ] Tüm bağımlılıklar yalnızca `gradle/libs.versions.toml` üzerinden yönetilmeli.
- [ ] Compose bağımlılıkları `platform(libs.androidx.compose.bom)` ile sürüm belirtilmeden eklenmeli.
- [ ] `./gradlew assembleDebug` hatasız derlenmeli ve GitHub Actions CI derleme işi yeşil bitmeli.
- [ ] Ayarlar menüsünde tema değişimi test edilmeli ve UI açık/koyu modlar arasında tutarlı tepki vermeli.
