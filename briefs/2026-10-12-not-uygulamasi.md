# Teknik Brief: Ayarlar ve Tema Destekli Not Uygulaması

**Tarih:** 2026-10-12  
**Kaynak Görev:** Aşama D — Ayarlar ekranı olan, açık/koyu tema destekleyen, not listeleme/ekleme/silme özellikli tek ekranlı/durum geçişli not uygulaması.  
**İlgili Wiki Notları:** [`wiki/android-stack.md`](../wiki/android-stack.md), [`wiki/ui-rules.md`](../wiki/ui-rules.md), [`wiki/kabul-kriterleri.md`](../wiki/kabul-kriterleri.md)

---

## 1. Kapsam ve Hedef

- **Ana Ekran (NotesScreen):**
  - Not listesi (`LazyColumn`)
  - Not ekleme alanı (metin alanı + "Ekle" butonu)
  - Not silme özelliği ("Sil" butonu)
  - Ayarlar ekranına geçiş butonu ("Ayarlar")
- **Ayarlar Ekranı (SettingsScreen):**
  - Tema seçimi: Sistem / Açık / Koyu seçenekleri (seçim anında uygulanır).
  - Ana ekrana dönüş butonu ("Geri") ve sistem geri tuşu (`BackHandler`) desteği.
- **Durum ve Süreklilik:**
  - Uygulama kapatılıp açıldığında notlar ve tema tercihi korunur.
  - Ekran döndürüldüğünde yazılmakta olan metin kaybolmaz (`rememberSaveable`).

---

## 2. Sabitlenmiş Bağımlılıklar ve Sürümler

*(Bu bölümdeki tüm sürüm değerleri doğrudan [`wiki/android-stack.md`](../wiki/android-stack.md) dosyasından alınmıştır. Wiki dışı hiçbir sürüm kullanılmamıştır.)*

| Bileşen / Kütüphane | Sabitlenmiş Sürüm | Kaynak / Not |
| :--- | :---: | :--- |
| **Android Gradle Plugin (AGP)** | `9.4.0` | [`wiki/android-stack.md`](../wiki/android-stack.md) |
| **Gradle** | `9.6.0` | [`wiki/android-stack.md`](../wiki/android-stack.md) |
| **Kotlin** | `2.4.21` | [`wiki/android-stack.md`](../wiki/android-stack.md) |
| **JDK** | `17` | [`wiki/android-stack.md`](../wiki/android-stack.md) |
| **compileSdk / targetSdk** | `37` | [`wiki/android-stack.md`](../wiki/android-stack.md) (Proje Kararı) |
| **minSdk** | `24` | [`wiki/android-stack.md`](../wiki/android-stack.md) (Proje Kararı) |
| **Compose BOM** | `2026.09.00` | [`wiki/android-stack.md`](../wiki/android-stack.md) |
| **material3** | `1.4.0` | [`wiki/android-stack.md`](../wiki/android-stack.md) *(stabil; BOM yönetir)* |
| **material3-adaptive** | `1.3.0` | [`wiki/android-stack.md`](../wiki/android-stack.md) |
| **core-ktx** | `1.19.1` | [`wiki/android-stack.md`](../wiki/android-stack.md) |
| **lifecycle** | `2.11.0` | [`wiki/android-stack.md`](../wiki/android-stack.md) |
| **activity** | `1.13.0` | [`wiki/android-stack.md`](../wiki/android-stack.md) |

---

## 3. Depolama Yaklaşımı

- **Notlar Depolama:** `filesDir` altında tek bir JSON dosyası (`notes.json`). JSON serileştirme/ayrıştırma için Android'in yerleşik `org.json` kütüphanesi (`JSONObject`, `JSONArray`) kullanılır. `kotlinx.serialization`, `Room`, `DataStore` wiki'de yer almadığından kesinlikle **kullanılmaz**.
- **Tema Tercihi Depolama:** Android platformunun yerleşik `SharedPreferences` mekanizması kullanılır.
- **Main Thread Hijyeni:** Dosya okuma ve yazma işlemleri `Dispatchers.IO` coroutine bağlamında ve `ViewModel` içinden yürütülür. Composable fonksiyonlar içerisinde dosya/tercih I/O işlemi yapılmaz.

---

## 4. UI ve Mimari Kısıtlar

1. **İkon Kısıtı:** Bu aşamada ikon kütüphaneleri (`material-icons-*`) kullanılmaz. Tüm aksiyonlar için metin butonları kullanılır ("Ekle", "Sil", "Ayarlar", "Geri"). Vektör çizim path'leri elle uydurulamaz.
2. **Renk ve Token Hijyeni:** Ham renk (`Color(0xFF..)`) ve ham `dp`/`sp` YALNIZ `ui/theme/` altında tanımlanır (`Color.kt`, `Type.kt`, `Dimens.kt`). Composable'ların içinde ham değer yazımı yasaktır; oralarda `MaterialTheme.colorScheme`, `MaterialTheme.typography` ve `Dimens` kullanılır.
3. **Önizleme (`@Preview`):** Üretilen tüm `@Composable` fonksiyonlar açık ve koyu tema durumlarını doğrulayan bağımsız `@Preview` fonksiyonlarına sahip olmalıdır.
4. **Yerelleştirme:** Sabit string ifadeler doğrudan koda yazılamaz; tüm metinler `res/values/strings.xml` dosyasından `stringResource` ile çekilir.
5. **Navigasyon Kısıtı:** `navigation-compose` wiki'de olmadığından eklenemez. Ekran durum geçişi (`Screen.Notes` ↔ `Screen.Settings`) ViewModel durum akışı ile yönetilir; sistem geri tuşu için `activity-compose` içindeki `BackHandler` kullanılır.
6. **Durum Korunumu:** Ekran rotasyonunda yazılmakta olan metin kaybolmamalıdır (`rememberSaveable`).

---

## 5. Kabul Kriterleri ve Doğrulama Adımları

- [ ] `python3 scripts/check_claims.py` sıfır çelişki ile çıkış kodu 0 üretmeli.
- [ ] Bağımlılıklar yalnızca `gradle/libs.versions.toml` üzerinden yönetilmeli (`version.ref = "lifecycle"`).
- [ ] `./gradlew assembleDebug` hatasız derlenmeli ve APK üretilmeli.
- [ ] GitHub Actions CI işinde hem "Wiki iddia kontrolu" hem "Debug APK derle" yeşil tamamlanmalı.
- [ ] Not ekleme, silme, kalıcılık ve tema geçişi doğrulanabilir olmalı.
