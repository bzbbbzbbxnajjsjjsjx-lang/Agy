#!/usr/bin/env python3
"""
check_claims.py Denetleyici Testleri (unittest)
Yalnızca standart kütüphane kullanır.
Tüm test vakaları geçici dizinlerde (tempfile) çalışır ve otomatik temizlenir.
"""

import os
import sys
import tempfile
import unittest
import subprocess

SCRIPT_PATH = os.path.abspath(os.path.join(os.path.dirname(__file__), 'check_claims.py'))

class TestCheckClaims(unittest.TestCase):

    def run_check(self, wiki_dir, extra_args=None):
        cmd = [sys.executable, SCRIPT_PATH, wiki_dir]
        if extra_args:
            cmd.extend(extra_args)
        return subprocess.run(cmd, capture_output=True, text=True)

    # 1. wiki/*.md dosyasında geçerli frontmatter yok
    def test_hata1_gecersiz_frontmatter(self):
        with tempfile.TemporaryDirectory() as tmpdir:
            # Durum A: Kapanış satırından sonra satır sonu / gövde metni yok
            bad_file = os.path.join(tmpdir, "bad.md")
            with open(bad_file, 'w', encoding='utf-8') as f:
                f.write("---\ntitle: Bad\nclaims:\n  - id: bad-1\n    claim: \"Test\"\n    confidence: UNVERIFIED\n---")
            
            res = self.run_check(tmpdir)
            self.assertEqual(res.returncode, 1)
            self.assertIn("kapanış", res.stdout + res.stderr)

    # 2. Frontmatter var ama claims: yok ya da boş
    def test_hata2_claims_yok_veya_bos(self):
        with tempfile.TemporaryDirectory() as tmpdir:
            bad_file = os.path.join(tmpdir, "no_claims.md")
            with open(bad_file, 'w', encoding='utf-8') as f:
                f.write("---\ntitle: No Claims\n---\n# Body\n")

            res = self.run_check(tmpdir)
            self.assertEqual(res.returncode, 1)
            self.assertIn("claims:", res.stdout + res.stderr)

    # 3. Aynı iddia id'si birden fazla kez geçiyor
    def test_hata3_mukerrer_iddia_id(self):
        with tempfile.TemporaryDirectory() as tmpdir:
            f1 = os.path.join(tmpdir, "note1.md")
            with open(f1, 'w', encoding='utf-8') as f:
                f.write("---\ntitle: Note 1\nclaims:\n  - id: dup-id\n    claim: \"First\"\n    confidence: UNVERIFIED\n---\n# Body\n")
            f2 = os.path.join(tmpdir, "note2.md")
            with open(f2, 'w', encoding='utf-8') as f:
                f.write("---\ntitle: Note 2\nclaims:\n  - id: dup-id\n    claim: \"Second\"\n    confidence: UNVERIFIED\n---\n# Body\n")

            res = self.run_check(tmpdir)
            self.assertEqual(res.returncode, 1)
            self.assertIn("birden fazla kez geçiyor", res.stdout + res.stderr)

    # 4. confidence değeri geçersiz
    def test_hata4_gecersiz_confidence(self):
        with tempfile.TemporaryDirectory() as tmpdir:
            bad_file = os.path.join(tmpdir, "invalid_conf.md")
            with open(bad_file, 'w', encoding='utf-8') as f:
                f.write("---\ntitle: Invalid Conf\nclaims:\n  - id: c-1\n    claim: \"Test\"\n    confidence: YANLIS_DEGER\n---\n# Body\n")

            res = self.run_check(tmpdir)
            self.assertEqual(res.returncode, 1)
            self.assertIn("geçersiz confidence", res.stdout + res.stderr)

    # 5. VERIFIED iddiada source_url, verified_at veya review_at eksik
    def test_hata5_verified_eksik_alan(self):
        with tempfile.TemporaryDirectory() as tmpdir:
            bad_file = os.path.join(tmpdir, "missing_field.md")
            with open(bad_file, 'w', encoding='utf-8') as f:
                f.write("---\ntitle: Missing Field\nclaims:\n  - id: c-1\n    claim: \"Test\"\n    source_url: https://example.com\n    verified_at: 2026-10-12\n    confidence: VERIFIED\n---\n# Body\n")

            res = self.run_check(tmpdir)
            self.assertEqual(res.returncode, 1)
            self.assertIn("VERIFIED iddiada review_at eksik", res.stdout + res.stderr)

    # 6. verified_at / review_at YYYY-MM-DD biçiminde değil
    def test_hata6_gecersiz_tarih_formati(self):
        with tempfile.TemporaryDirectory() as tmpdir:
            bad_file = os.path.join(tmpdir, "bad_date.md")
            with open(bad_file, 'w', encoding='utf-8') as f:
                f.write("---\ntitle: Bad Date\nclaims:\n  - id: c-1\n    claim: \"Test\"\n    source_url: https://example.com\n    verified_at: 12-10-2026\n    review_at: 2027-01-10\n    confidence: VERIFIED\n---\n# Body\n")

            res = self.run_check(tmpdir)
            self.assertEqual(res.returncode, 1)
            self.assertIn("YYYY-MM-DD biçiminde değil", res.stdout + res.stderr)

    # 7. superseded_by var olmayan bir id'yi gösteriyor
    def test_hata7_gecersiz_superseded_by(self):
        with tempfile.TemporaryDirectory() as tmpdir:
            bad_file = os.path.join(tmpdir, "bad_superseded.md")
            with open(bad_file, 'w', encoding='utf-8') as f:
                f.write("---\ntitle: Bad Superseded\nclaims:\n  - id: c-1\n    claim: \"Test\"\n    confidence: UNVERIFIED\n    superseded_by: olmayan-id-999\n---\n# Body\n")

            res = self.run_check(tmpdir)
            self.assertEqual(res.returncode, 1)
            self.assertIn("var olmayan bir id'yi gösteriyor", res.stdout + res.stderr)

    # 8. (mevcut) Kaynaksız VERIFIED -> çelişki
    def test_hata8_kaynaksiz_verified_celiski(self):
        with tempfile.TemporaryDirectory() as tmpdir:
            bad_file = os.path.join(tmpdir, "celiski.md")
            with open(bad_file, 'w', encoding='utf-8') as f:
                f.write("---\ntitle: Celiski\nclaims:\n  - id: c-1\n    claim: \"Test\"\n    source_url: null\n    verified_at: 2026-10-12\n    review_at: 2027-01-10\n    confidence: VERIFIED\n---\n# Body\n")

            res = self.run_check(tmpdir)
            self.assertEqual(res.returncode, 1)
            self.assertIn("ÇELİŞKİ", res.stdout + res.stderr)

    # Bayat iddia -> exit 0 + uyarı metni çıktıda
    def test_uyari_bayat_iddia(self):
        with tempfile.TemporaryDirectory() as tmpdir:
            stale_file = os.path.join(tmpdir, "stale.md")
            with open(stale_file, 'w', encoding='utf-8') as f:
                f.write("---\ntitle: Stale\nclaims:\n  - id: stale-1\n    claim: \"Test\"\n    source_url: https://example.com\n    verified_at: 2020-01-01\n    review_at: 2020-01-01\n    confidence: VERIFIED\n---\n# Body\n")

            res = self.run_check(tmpdir)
            self.assertEqual(res.returncode, 0)
            self.assertIn("BAYAT", res.stdout)
            self.assertIn("bayat iddia", res.stdout)

    # Sağlam tek iddialı not -> exit 0, "Kontrol edilen iddia: 1 / toplam: 1"
    def test_saglam_tek_iddiali_not(self):
        with tempfile.TemporaryDirectory() as tmpdir:
            good_file = os.path.join(tmpdir, "good.md")
            with open(good_file, 'w', encoding='utf-8') as f:
                f.write("---\ntitle: Good\nclaims:\n  - id: good-1\n    claim: \"Test iddia\"\n    source_url: https://example.com\n    verified_at: 2026-10-12\n    review_at: 2027-01-10\n    confidence: VERIFIED\n---\n# Body\n")

            res = self.run_check(tmpdir)
            self.assertEqual(res.returncode, 0)
            self.assertIn("Kontrol edilen iddia: 1 / toplam: 1", res.stdout)

    # E2: toml'da wiki'de olmayan sürüm -> exit 1
    def test_e2_toml_wiki_uyumsuzlugu(self):
        with tempfile.TemporaryDirectory() as tmpdir:
            stack_file = os.path.join(tmpdir, "android-stack.md")
            with open(stack_file, 'w', encoding='utf-8') as f:
                f.write("---\ntitle: Stack\nclaims:\n  - id: c-agp\n    claim: \"AGP 9.4.0'dir\"\n    source_url: https://example.com\n    verified_at: 2026-10-12\n    review_at: 2027-01-10\n    confidence: VERIFIED\n---\n# Body\n")

            toml_file = os.path.join(tmpdir, "test.toml")
            with open(toml_file, 'w', encoding='utf-8') as f:
                f.write("[versions]\nagp = \"9.4.0\"\nbilinmeyen = \"99.9.9\"\n")

            wrapper_file = os.path.join(tmpdir, "wrapper.properties")
            with open(wrapper_file, 'w', encoding='utf-8') as f:
                f.write("distributionUrl=https\\://services.gradle.org/distributions/gradle-9.4.0-bin.zip\n")

            res = self.run_check(tmpdir, ["--toml", toml_file, "--wrapper", wrapper_file])
            self.assertEqual(res.returncode, 1)
            self.assertIn("toml: bilinmeyen=99.9.9 wiki'de yok", res.stdout + res.stderr)

if __name__ == '__main__':
    unittest.main()
