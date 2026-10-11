# Görev: Aşama D — Not Uygulaması

## Görev Özeti
Aşama A, B, C tamamlandıktan sonra bu aşamada aşağıdaki adımlar uygulanır:

1. **D0 — Kontrol Script'i (`scripts/check_claims.py`):**
   - Bayat VERIFIED iddia "kontrol edilen" sayısına dahil edilmez.
   - Kaynaksız VERIFIED iddia "VERIFIED (KAYNAK YOK!)" ve ÇELİŞKİ sayılır; çıkış kodu 1 döner.
   - Wiki frontmatter'larından mükerrer `status:` alanı silindi; yalnızca `confidence` okunur.
   - `.github/workflows/android-apk.yml` içine derleme öncesi `Wiki iddia kontrolu` adımı eklendi.
   - Script `$HOME` altındaki geçici notla test edildi.
2. **D1 — Brief Güncellemesi (`briefs/2026-10-12-not-uygulamasi.md`):**
   - Ham renk ve dp/sp kuralı güncellendi (ham değer YALNIZ `ui/theme/` altında).
   - "Depolama yaklaşımı" (filesDir + org.json + SharedPreferences) eklendi.
3. **D2 — Not Uygulaması Geliştirmesi:**
   - Ana ekran: Not listesi (`LazyColumn`), not ekleme (metin alanı + "Ekle" butonu), not silme.
   - Ayarlar ekranı: Tema seçimi (Sistem / Açık / Koyu, anında uygulanır).
   - Depolama: `filesDir/notes.json` (`org.json`), SharedPreferences (`ThemePreference`), I/O `Dispatchers.IO` üzerinde.
   - Ekran rotasyonunda yazılan metin korunur (`rememberSaveable`).
   - İkon yerine metin butonları, `strings.xml` ve `Dimens` token kullanımı.
   - Bağımlılıklar: `lifecycle-viewmodel-compose` ve `lifecycle-runtime-compose` (`2.11.0`).

## Bitirme Komutu
```bash
git add -A && git commit -m "Asama D: not uygulamasi + script duzeltmeleri" && git push
```
