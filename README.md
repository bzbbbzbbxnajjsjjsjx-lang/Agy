# Agy — Android Geliştirme Kuşak (Harness) Çalışma Alanı

Bu depo, Jetpack Compose ve modern Android standartlarına dayalı uygulamalar üretmek amacıyla tasarlanmış; bağlam, kanonik wiki ve kontrollü geliştirme döngüsünü merkezine alan disiplinli bir ajan çalışma alanıdır.

## Dizin Yapısı ve Görevleri

- **[`AGENTS.md`](AGENTS.md):** Kuşak (harness) sistemi, değişmez kurallar ve görev geliştirme döngüsü.
- **[`wiki/`](wiki/):** LLM kanonik bilgi tabanı; iddia bazında doğrulanmış Android kütüphane sürümleri ve mimari kurallar.
- **[`.agents/`](.agents/):** Ajan tetikleyici kuralları (`rules/`) ve komut/yetenek tanımları (`skills/`).
- **[`briefs/`](briefs/):** Görev bazlı teknik brief'ler ve dondurulmuş sürüm gereksinimleri.
- **[`app/`](app/):** Modern Compose mimarisiyle geliştirilen Android uygulama modülü.

## APK İndirme

Derlenen hata ayıklama (debug) APK dosyaları her GitHub Actions çalışmasının ardından otomatik üretilir:
1. Depodaki **Actions** sekmesine gidin.
2. En son başarılı iş akışı çalıştırmasını (**Android APK**) seçin.
3. Sayfanın en altındaki **Artifacts** bölümünden `app-debug-apk` paketini indirin.