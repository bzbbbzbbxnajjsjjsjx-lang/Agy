# Wiki Not Şeması ve Yönetim Kuralları

Bu dizin, projenin kanonik bilgi tabanını barındırır. LLM varsayımları veya hafıza yerine yalnız bu dizindeki doğrulanmış bilgiler tek gerçeklik kaynağı kabul edilir.

## Not Şeması

Tüm wiki notları (`wiki/*.md`) aşağıdaki YAML frontmatter alanlarını içermek zorundadır:

```yaml
---
title: "Not Başlığı"
tags: [etiket1, etiket2]
source_url: https://resmi-kaynak-adresi...
verified_at: YYYY-MM-DD
review_at: YYYY-MM-DD
confidence: VERIFIED
---
```

### Alan Açıklamaları

- `title`: Notun açık ve tekil başlığı.
- `tags`: Konuyla ilgili arama etiketleri (örn: `[stack, surum]`, `[ui, yasak]`).
- `source_url`: Bilginin teyit edildiği resmî web sayfası adresi.
- `verified_at`: Bilginin en son teyit edildiği tarih (`YYYY-MM-DD`).
- `review_at`: Bilginin yeniden doğrulanması gereken son geçerlilik tarihi (`YYYY-MM-DD`).
- `confidence`: Güven seviyesi. Yalnızca aşağıdaki üç değerden biri olabilir:
  - `VERIFIED`: Resmî kaynaktan doğrudan teyit edilmiş bilgi.
  - `INFERRED`: Resmî kaynaktan mantıksal çıkarım yoluyla türetilmiş bilgi.
  - `UNVERIFIED`: Kaynağı teyit edilmemiş veya henüz resmî bir URL ile doğrulanmamış bilgi.

## Değişmez Kurallar

1. **Kaynak Şartı:** `source_url` olmadan hiçbir not `confidence: VERIFIED` olarak işaretlenemez (en fazla `UNVERIFIED` olabilir).
2. **Tek Doğruluk Kaynağı ve `superseded_by`:** Aynı bilgi iki farklı yere yazılamaz. Bilgi değiştiğinde veya güncellendiğinde eski not silinmez; yeni not yazılır ve eski notun frontmatter'ına `superseded_by: <yeni-not-slug>` alanı eklenir.
3. **Bayat Not Kuralı:** Eğer sistemin geçerli tarihi `review_at` tarihini geçmişse (`bugün > review_at`), bu not **BAYAT** sayılır ve oluşturulan teknik brief'lerde uyarı etiketiyle işaretlenir.
