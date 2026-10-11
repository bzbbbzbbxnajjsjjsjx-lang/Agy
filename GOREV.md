# Görev: Aşama A — Bağlam Sistemi Kurulumu

## Görev Özeti
Bu deponun amacı, "bağlam + wiki + döngü" altyapısını kurarak disiplinli bir Android geliştirme ortamı sağlamaktır. Aşama A kapsamında uygulama kodu yazılmadan yalnızca sistem iskeleti oluşturulmuştur:

1. **Kuşak (Harness):** `AGENTS.md` (6 değişmez kural ve döngü tanımıyla, YAML frontmatter olmadan).
2. **Kanonik LLM Wiki:**
   - `wiki/android-stack.md`: Sabitlenmiş sürümler ve resmî kaynaklar matrisi.
   - `wiki/ui-rules.md`: Material Symbols zorunluluğu, token kullanımı ve UI yasakları.
   - `wiki/kabul-kriterleri.md`: libs.versions.toml zorunluluğu, compose-bom ve mimari kurallar.
3. **Koşullu Kurallar (`.agents/rules/`):**
   - `surum.md`: Sürüm ve bağımlılık kararlarında `wiki/android-stack.md` dahil edilir.
   - `ui.md`: UI ve Compose yazımında `wiki/ui-rules.md` dahil edilir.
4. **Ajan Becerileri (`.agents/skills/`):**
   - `baglam/SKILL.md`: İlgili wiki notlarını ve görevi okuyup teknik brief üretir.
   - `tazele/SKILL.md`: Wiki notlarının resmî kaynaklarını kontrol edip doğrular/günceller.
5. **Teknik Brief Şablonu:** `briefs/README.md`.
6. **CI/CD Derleme İşi:** `.github/workflows/android-apk.yml` (gradlew kontrolü ve APK derleme adımları).

## Bitince Çalıştırılacak Komut
Aşama A tamamlandığında değişiklikleri depoya işlemek ve göndermek için:
```bash
git add -A && git commit -m "Asama A: baglam sistemi" && git push
```
