# Teknik Brief: Not Uygulaması Geliştirmeleri (Arama, Düzenleme, Onaylı Silme)

**Tarih:** 2026-10-12  
**Kaynak Görev:** Aşama E — Not Arama, Canlı Filtreleme, Not Düzenleme, Zaman Damgası ve Silme Onay Diyaloğu.  
**İlgili Wiki Notları:** [`wiki/android-stack.md`](../wiki/android-stack.md), [`wiki/ui-rules.md`](../wiki/ui-rules.md), [`wiki/kabul-kriterleri.md`](../wiki/kabul-kriterleri.md)

---

## 1. Kapsam ve Hedef

- **Arama ve Filtreleme:** Kullanıcı notlar arasında metin araması yapabilmeli, arama sorgusu değiştikçe `LazyColumn` anlık filtrelenmeli, arama alanı temizlenebilmeli ve eşleşme olmadığında uygun bilgilendirici mesaj gösterilmelidir.
- **Not Düzenleme (Edit & Update):** Kullanıcı var olan bir notu düzenleyebilmeli; düzenleme modunda metin düzenleme alanına yüklenmeli, "Ekle" yerine "Güncelle" ve "Vazgeç" aksiyonları sunulmalı ve kaydedildiğinde `notes.json` güncellenmelidir.
- **Zaman Damgası:** Notlar son güncellenme/oluşturulma zamanına göre tersten kronolojik (en yeni en üstte) sıralanmalı ve kart üzerinde tarih/saat bilgisi yer almalıdır.
- **Silme Onayı:** Not silme aksiyonunda veri kaybını önlemek için `AlertDialog` ile kullanıcıdan teyit alınmalıdır.

---

## 2. Sabitlenmiş Bağımlılıklar ve Sürümler

*(Mevcut `wiki/android-stack.md` ve `gradle/libs.versions.toml` dışından hiçbir kütüphane veya sürüm eklenmemiştir.)*

| Bileşen | Sürüm |
| :--- | :---: |
| Android Gradle Plugin (AGP) | `9.4.0` |
| Gradle | `9.6.0` |
| Kotlin | `2.4.21` |
| JDK | `17` |
| Compose BOM | `2026.09.00` |
| Material 3 | `1.4.0` (BOM yönetiminde) |
| compileSdk / targetSdk | `37` |
| minSdk | `24` |

---

## 3. Mimari ve Tasarım Kısıtları

1. **Dokunulmaz Dosyalar:** `SettingsScreen.kt`, `ui/theme/*`, `app/build.gradle.kts`, `gradle/libs.versions.toml`, `.github/workflows/android-apk.yml`.
2. **UI Hijyeni:**
   - İkon kütüphanesi yok; metin butonları kullanılır.
   - Ham `dp` veya `Color` kullanımı yasaktır; yalnızca `Dimens` ve `MaterialTheme` token'ları kullanılır.
   - Hardcoded string yasaktır; tüm metinler `res/values/strings.xml` dosyasından okunur.
   - Tüm Composable bileşenler için açık ve koyu temayı test eden `@Preview` tanımlanır.
3. **Veri ve I/O:**
   - Composable içinde I/O yapılmaz; tüm okuma/yazma `Dispatchers.IO` üzerinde `NoteRepository` aracılığıyla `ViewModel` içinde yürütülür.
   - `rememberSaveable` ile arama sorgusu ve giriş alanı durumları ekran dönüşlerinde korunur.

---

## 4. Kabul Kriterleri

- [ ] `python3 scripts/check_claims.py` sıfır çelişki ile çıkış kodu 0 üretmelidir.
- [ ] Arama, filtreleme, düzenleme, silme onayı fonksiyonel olarak çalışmalıdır.
- [ ] Dokunulmaz dosyalarda hiçbir değişiklik yapılmamış olmalıdır.
- [ ] GitHub Actions CI işinde debug ve release APK derlemeleri yeşil sonuçlanmalıdır.
