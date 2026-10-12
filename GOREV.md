Rol: Bu repoda AGENTS.md'ye bağlı ajansın. Bu aşama YALNIZ context/harness
sistemi üzerindedir. Uygulama koduna (app/ altına) DOKUNMA. Yeni Android
bağımlılığı ekleme, sürüm değiştirme.

Önce oku: AGENTS.md, wiki/README.md, briefs/README.md, scripts/check_claims.py,
DURUM.md, .agents/skills/*/SKILL.md, .github/workflows/android-apk.yml

Bu mesajın tamamını önce GOREV.md'ye yaz (mevcut içeriğin üzerine), sonra uygula.
--------------------------------------------------------------------------
E1 — check_claims.py: sessiz geçişleri kapat
--------------------------------------------------------------------------
Şu an aşağıdaki durumların HEPSİ exit 0 ile geçiyor. Her biri artık
HATA olacak (exit 1) ve hangi dosya/iddia olduğunu yazacak:

 1. wiki/*.md (README hariç) dosyasında geçerli frontmatter yok
    (dosyanın `---` ile bitmesi, yani kapanış satırından sonra satır sonu
    olmaması dahil)
 2. Frontmatter var ama `claims:` yok ya da boş
 3. Aynı iddia id'si birden fazla kez geçiyor (tüm wiki genelinde)
 4. `confidence` değeri VERIFIED / PROJE_KARARI / INFERRED / UNVERIFIED
    dışında (ör. yazım hatası)
 5. VERIFIED iddiada source_url, verified_at veya review_at eksik
 6. verified_at / review_at YYYY-MM-DD biçiminde değil
 7. superseded_by var olmayan bir id'yi gösteriyor
 8. (mevcut) Kaynaksız VERIFIED → çelişki

UYARI olacaklar (exit 0 kalır, CI'da görünür):
 - Bayat iddia (bugün > review_at)
 - review_at'e 30 günden az kalan iddia
 - CI ortamında (GITHUB_ACTIONS=true) uyarıları `::warning file=...::mesaj`
   biçiminde de bas, hataları `::error file=...::mesaj` biçiminde.

Kısıtlar: Yalnız Python standart kütüphanesi. Mevcut çıktı formatı ve
"Kontrol edilen iddia: X / toplam: Y" satırı korunur.

--------------------------------------------------------------------------
E2 — Sürüm tutarlılık kontrolü (wiki ↔ derleme dosyaları)
--------------------------------------------------------------------------
check_claims.py'ye ekle (ayrı bölüm, ayrı başlık):
 - gradle/libs.versions.toml içindeki [versions] bölümündeki HER değer,
   wiki/android-stack.md içinde bir iddianın claim metninde geçmeli.
 - gradle/wrapper/gradle-wrapper.properties içindeki Gradle sürümü de
   aynı şekilde wiki'de geçmeli.
 - Geçmeyen her değer HATA (exit 1): "toml: <anahtar>=<değer> wiki'de yok".
Not: Şu an 10 toml değeri + Gradle 9.6.0 wiki'de mevcut; bu kontrol
mevcut repoda geçmeli. Geçmezse kontrolü değil, nedenini raporla.

--------------------------------------------------------------------------
E3 — Denetleyicinin kendi testleri
--------------------------------------------------------------------------
scripts/test_check_claims.py oluştur (unittest, standart kütüphane).
Geçici test dosyalarını tempfile ile oluştur, iş bitince sil.
Her vaka ayrı test:
 - E1'deki 8 hata durumunun her biri → exit 1
 - Bayat iddia → exit 0 + uyarı metni çıktıda
 - Sağlam tek iddialı not → exit 0, "Kontrol edilen iddia: 1 / toplam: 1"
 - E2: toml'da wiki'de olmayan sürüm → exit 1
Workflow'da "Wiki iddia kontrolu" adımından ÖNCE şu adımı ekle:
   - name: Denetleyici testleri
     run: python3 -m unittest scripts/test_check_claims.py -v
Workflow'da bunun dışında HİÇBİR şeyi değiştirme.

--------------------------------------------------------------------------
E4 — Tek kopya kuralı (AGENTS.md kural 4)
--------------------------------------------------------------------------
 a) briefs/README.md şablonundaki elle yazılmış sürümleri kaldır. Yerine:
    "Sürüm tablosu brief üretilirken wiki/android-stack.md'den kopyalanır;
    şablon sürüm içermez." ve <wiki'den> yer tutucuları koy.
    (Üretilmiş brief'ler sürüm SABİTLER — bu bilinçli bir anlık görüntüdür,
dokunma.)
 b) DURUM.md elle yazılmasın. check_claims.py'ye `--durum` seçeneği ekle:
    DURUM.md'yi script çıktısından üretir (iddia tablosu + özet + hatalar
    + uyarılar). Dosyadaki tarih = çalıştırma günü.
    DURUM.md'yi bu seçenekle yeniden üret ve commit et.
    .agents/skills/durum/SKILL.md'yi buna göre güncelle: "DURUM.md'yi elle
    düzenleme; `python3 scripts/check_claims.py --durum` çalıştır."

--------------------------------------------------------------------------
E5 — Kural metinleri
--------------------------------------------------------------------------
AGENTS.md "Değişmez Kurallar"a ekle (mevcut maddeleri silme):
 7. Yerelde Android SDK yoksa derleme kanıtı CI'dır. Raporda "CI'da derlendi
    (koşu linki)" yaz. "CI'da derlendi" ≠ "test edildi". Cihazda/emülatörde
    çalıştırmadıysan "test edildi" YAZMA.
 8. Wiki'ye not eklediğinde/değiştirdiğinde commit'ten önce
`python3 scripts/check_claims.py` çalıştır; exit 0 değilse commit etme.
AGENTS.md "Geliştirme Döngüsü" 5. adımını buna uygun düzelt
("./gradlew assembleDebug — yerelde SDK yoksa CI").
wiki/README.md "Not Şeması" altına ekle: frontmatter kapanış `---`
satırından sonra satır sonu ve gövde metni zorunlu; aksi hâlde denetleyici
hata verir.
Bu kurallar için kabul-kriterleri'ne yeni iddia GEREKMİYOR; kural metni
AGENTS.md/wiki README'de tek kopya olarak durur.

--------------------------------------------------------------------------
DOĞRULAMA (hepsi zorunlu, sırayla)
--------------------------------------------------------------------------
 1. python3 -m unittest scripts/test_check_claims.py -v  → hepsi OK
 2. python3 scripts/check_claims.py  → exit 0. Beklenen özet:
      Kontrol edilen iddia: 17 / toplam: 27
Çelişki 0, hata 0, bayat 0
    (Bugünkü wiki: tekrar id yok, geçersiz confidence yok, VERIFIED
tarihleri tam, en erken review_at 2027-01-10.)
    Farklı çıkarsa kuralı gevşetme; nedenini bul ve raporla.
 3. Negatif kanıt: $HOME altında geçici bir kopya wiki klasörüne `---` ile
biten bir not koy, script'i o klasöre çalıştır → exit 1 gör. Klasörü sil.
    (/tmp kullanma.)
 4. python3 scripts/check_claims.py --durum → DURUM.md üretildi, git diff'i
    makul.
 5. Commit + push:
      git add scripts .github AGENTS.md wiki/README.md briefs/README.md DURUM.md .agents GOREV.md
      git commit -m "Asama E: context denetleyicisi sertlestirildi + testler + tek kopya"
      git push
 6. Actions: "Denetleyici testleri", "Wiki iddia kontrolu", "Debug ve Release
    APK derle" adımlarının üçü de yeşil olmalı. Kırmızıysa düzelt, tekrar push.
    Yeşil olmadan "bitti" deme.

--------------------------------------------------------------------------
RAPOR (kısa)
--------------------------------------------------------------------------
 - Commit hash + Actions koşu linki + 3 adımın sonucu
 - unittest çıktısının özet satırı (Ran N tests ... OK)
 - check_claims.py son satırları
 - Negatif kanıt çıktısı (exit 1 ve hata mesajı)
 - Değişen dosya listesi; app/ altında değişiklik OLMADIĞINI git diff
--stat ile göster
 - Doğrulayamadıkların
