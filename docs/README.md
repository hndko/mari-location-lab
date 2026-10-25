# 📚 Portal Dokumentasi Proyek Mari Location Lab

Selamat datang di pusat dokumentasi resmi **Mari Location Lab** (`com.maripartner.locationqa`). Direktori ini memuat seluruh panduan operasional, manual book pengguna, arsitektur teknis, dan prosedur rilis aplikasi berstandar enterprise.

---

## 🗺️ Peta Navigasi Dokumen

Gunakan katalog di bawah ini untuk mengakses dokumen sesuai kebutuhan dan peran Anda:

| Dokumen | Judul Dokumen | Target Pembaca | Ringkasan Konten |
|---|---|---|---|
| [**01. CLI Reference**](01-daftar-command.md) | **Daftar Perintah Wajib & Operasional** | Developer, DevOps, QA | Perintah CLI Gradle, ADB, emulator management, unit test runner, linting, dan build APK/AAB. |
| [**02. User Manual**](02-buku-panduan-pengguna.md) | **Buku Panduan Pengguna & QA Tester** | QA Engineer, Software Tester | Panduan aktivasi Opsi Pengembang, pemilihan mock app, uji koordinat manual, skenario, dan FAQ. |
| [**03. Developer Guide**](03-developer-guide.md) | **Panduan Pengembang & Arsitektur** | Android Engineer, Maintainer | Prinsip Clean Architecture + MVVM, alur UI State StateFlow, Design System Material 3, dan panduan TDD. |
| [**04. Deployment Guide**](04-panduan-deployment.md) | **Panduan Build, Rilis & Google Play** | Release Manager, Tech Lead | Penandatanganan keystore, build AAB, audit manifest/permissions, deklarasi Data Safety Play Store. |

---

## ⚡ Pintasan Navigasi Cepat (Quick Shortcuts)

### 🧑‍💻 Jika Anda Pengembang yang Baru Bergabung (Onboarding Developer):
1. Baca [Panduan Pengembang (03-developer-guide.md)](03-developer-guide.md) untuk memahami struktur paket `core`, `domain`, `data`, `platform`, dan `feature`.
2. Buka [Daftar Perintah (01-daftar-command.md)](01-daftar-command.md) untuk menyalin perintah build dan testing lokal.
3. Pastikan membaca acuan aturan di [AGENTS.md](../AGENTS.md).

### 🧪 Jika Anda QA Tester yang Menguji Aplikasi Target:
1. Baca [Buku Panduan Pengguna (02-buku-panduan-pengguna.md)](02-buku-panduan-pengguna.md) bagian *Aktivasi Opsi Pengembang & Pemilihan Aplikasi Lokasi Tiruan*.
2. Gunakan modul *Peta & Validasi Koordinat* untuk menguji boundary latitude `-90..90` dan longitude `-180..180`.

### 🚀 Jika Anda Menyiapkan Rilis Produksi / Toko Aplikasi:
1. Buka [Panduan Deployment (04-panduan-deployment.md)](04-panduan-deployment.md) untuk checklist audit manifest dan deklarasi privasi Google Play.

---

## 🛡️ Prinsip Utama Dokumentasi

Seluruh dokumen dalam direktori ini ditulis dengan mematuhi prinsip anti-slop:
- **Konkret & Bebas Placeholder**: Tidak ada konfigurasi atau perintah fiktif. Seluruh path, package name, dan script telah diverifikasi secara nyata pada codebase repositori.
- **Transparansi Tanpa Kompromi**: Menjelaskan batasan sistem secara jujur tanpa klaim palsu mengenai kemampuan bypass atau ketersediaan offline tanpa cache.
