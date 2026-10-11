# İddia Bazlı Wiki Şeması ve Yönetim Kuralları

Bu dizin, projenin kanonik bilgi tabanını barındırır. Bilgi doğrulama ve tazelik takibi dosya/not düzeyinde kaba bir bütün olarak değil; **iddia (claim) düzeyinde atomik** olarak yürütülür.

## İddia (Claim) Nedir?

Bir wiki notu birden çok somut gerçek, sürüm veya kural içerir. Her bir bağımsız olgu veya kural bir **iddia (`claim`)** olarak tanımlanır. Her iddianın kendi resmî kaynağı (`source_url`), doğrulama tarihi (`verified_at`), yeniden inceleme tarihi (`review_at`) ve güven düzeyi (`confidence`) bulunur.

## Not Şeması

Tüm wiki notları (`wiki/*.md`, `README.md` hariç) YAML frontmatter içinde `claims` listesi barındırır:

```yaml
---
title: "Not Başlığı"
tags: [etiket1, etiket2]
last_reviewed: YYYY-MM-DD
claims:
  - id: benzersiz-iddia-id
    claim: "Doğrulanabilir atomik iddia cümlesi."
    source_url: https://resmi-kaynak-adresi...
    verified_at: YYYY-MM-DD
    review_at: YYYY-MM-DD
    confidence: VERIFIED
---
```

### İddia Alanları

- `id`: Not içinde benzersiz, kebab-case kimlik (ör. `agp-version`, `ui-no-raw-hex`).
- `claim`: İddianın açık, tekil ve test edilebilir ifadesi.
- `source_url`: İddianın bizzat teyit edildiği doğrudan resmî kaynak URL'si.
- `verified_at`: İddianın bu kaynaktan en son doğrulandığı tarih (`YYYY-MM-DD`).
- `review_at`: İddianın yeniden teyit edilmesi gereken son geçerlilik tarihi (`YYYY-MM-DD`).
- `confidence`: Güven seviyesi:
  - `VERIFIED`: Resmî kaynaktan doğrudan teyit edilmiş iddia.
  - `INFERRED`: Resmî kaynaktan mantıksal çıkarım yoluyla türetilmiş iddia.
  - `UNVERIFIED`: Kaynaksız veya henüz resmî kaynaktan teyit edilmemiş iddia.
- `superseded_by`: (Opsiyonel) Bu iddia güncellendiyse veya yerini yeni bir iddia aldıysa yeni iddia ID'si.

## Değişmez İddia Kuralları

1. **Atomik Doğrulama:** Her iddia bağımsız olarak doğrulanır. Bir nottaki iddialardan birinin kaynağına erişilememesi, diğer doğrulanmış iddiaları geçersiz kılmaz.
2. **Kaynak Şartı:** `source_url` olmadan hiçbir iddia `confidence: VERIFIED` olamaz (en fazla `UNVERIFIED` olur).
3. **İddia Düzeyinde Bayatlık:** Bir iddianın `review_at` tarihi geçmişse (`bugün > review_at`), yalnız o iddia **BAYAT** sayılır ve brief'lerde uyarı etiketiyle çıkar.
4. **Tek Gerçeklik ve `superseded_by`:** Aynı iddia iki farklı yere yazılamaz. İddia değiştiğinde (ör. kütüphane sürümü yükseldiğinde veya API kaldırıldığında) eski iddia silinmez; yeni iddia eklenir veya eski iddiaya `superseded_by: <yeni-iddia-id>` atanır.
5. **Not Düzeyinde Türetilmiş Durum:**
   - **Tüm iddialar `VERIFIED`** → Not **TAM DOĞRULANMIŞ** (`VERIFIED`).
   - **En az bir iddia `UNVERIFIED` / `INFERRED`** → Not **KISMİ DOĞRULANMIŞ** (`PARTIAL`).
   - **Herhangi bir iddia bayat** → Not **DİKKAT (BAYAT İDDİA İÇERİR)**.
