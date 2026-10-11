---
name: durum
description: Deponun genel durumunu (git, CI/CD, aktif görev ve wiki doğrulama durumlarını) raporlar.
---

# Durum Raporlama Yeteneği

Bu skill, projenin mevcut durumunu tek bir raporda konsolide etmek için kullanılır:

1. **Git Durumu:** Aktif dal, son commit hash'i ve çalışma ağacı temizliği (`git status`, `git log -n 1`).
2. **CI/CD Durumu:** GitHub Actions son derleme sonucu, çalışma süresi ve artifact varlığı (`gh run list`).
3. **Görev Durumu:** `GOREV.md` dosyasındaki mevcut aşama ve açık kalan adımlar.
4. **Wiki Durumu:** `wiki/` altındaki kanonik notlar, doğrulama tarihleri (`verified_at`) ve güven dereceleri (`confidence`).
