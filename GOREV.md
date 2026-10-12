# Görev: Aşama E — Not Uygulaması Geliştirmeleri (Arama, Düzenleme, Onaylı Silme)

## Görev Özeti
Aşama D'de tamamlanan temel not uygulaması üzerine kullanıcı deneyimini ve veri bütünlüğünü güçlendiren geliştirmeler eklenir:

1. **E0 — Hazırlık & Brief:**
   - `briefs/2026-10-12-not-uygulamasi-gelistirmeler.md` teknik brief'i oluşturulur.
2. **E1 — Arama ve Gerçek Zamanlı Filtreleme:**
   - Arama metin alanı eklenir, büyük/küçük harf duyarsız canlı filtreleme yapılır.
   - Eşleşen not yoksa boş arama durumu gösterilir. Arama temizleme butonu sunulur.
3. **E2 — Not Düzenleme:**
   - Her not için düzenleme aksiyonu eklenir. Düzenleme modunda metin alana yüklenir, "Güncelle" ve "Vazgeç" butonları aktif olur.
   - `NoteRepository` üzerinden yerel JSON dosyasında atomik güncelleme yapılır.
4. **E3 — Zaman Damgası & Sıralama:**
   - Notlar oluşturulma / son güncellenme tarihine göre en yeniden eskiye doğru sıralı listelenir. Not kartında tarih/saat gösterilir.
5. **E4 — Silme Onay Diyaloğu:**
   - Kazara veri kaybını önlemek için silme butonuna tıklandığında `AlertDialog` ile onay istenir.
6. **E5 — Katı Mimari & UI Kısıtları:**
   - `SettingsScreen.kt`, `ui/theme/*`, `app/build.gradle.kts`, `gradle/libs.versions.toml`, `.github/workflows/android-apk.yml` dosyalarına dokunulmaz.
   - Yeni kütüphane eklenmez. Ham dp / renk Composable'da kullanılmaz.
   - Tüm metinler `strings.xml`'den çekilir. Composable'lar için `@Preview` zorunludur.
7. **E6 — Doğrulama ve CI:**
   - `python3 scripts/check_claims.py` sıfır çelişki ile doğrulanır.
   - Git push sonrası GitHub Actions CI üzerinde debug ve release APK derlemesi doğrulanır.

## Bitirme Komutu
```bash
git add -A && git commit -m "feat: not arama, duzenleme, zaman damgasi ve onayli silme" && git push
```
