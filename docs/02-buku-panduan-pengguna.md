# 📘 02. Buku Panduan Pengguna & QA Tester (User Manual)

Buku panduan ini disusun untuk QA tester, Android developer, dan tim pengembang aplikasi peta/logistik yang menggunakan **Mari Location Lab** untuk memvalidasi fitur berbasis lokasi pada perangkat pengujian.

---

## 🎯 1. Profil Singkat Aplikasi

**Mari Location Lab** adalah alat pengujian transparan yang memanfaatkan mekanisme lokasi tiruan (*Mock Location*) resmi Android. Aplikasi ini digunakan untuk menguji aplikasi milik sendiri atau aplikasi berlisensi, tanpa perlu bepergian secara fisik.

> [!IMPORTANT]
> **Batasan Etika & Keamanan:**
> - Aplikasi ini **TIDAK** dirancang untuk mengakali aplikasi absensi/kehadiran pihak ketiga.
> - Aplikasi ini **TIDAK** memodifikasi data aplikasi lain atau melewati sistem deteksi mock location.
> - Seluruh data pengujian disimpan secara lokal pada perangkat penguji.

---

## ⚙️ 2. Persiapan Awal Perangkat Pengujian

Sebelum dapat menjalankan simulasi lokasi, perangkat Android (baik smartphone fisik maupun emulator) wajib dikonfigurasi melalui menu Opsi Pengembang (*Developer Options*).

### Langkah 1: Mengaktifkan Opsi Pengembang
1. Buka menu **Pengaturan** (*Settings*) pada ponsel Android Anda.
2. Gulir ke bawah dan pilih **Tentang Ponsel** (*About Phone*).
3. Cari entri bertuliskan **Nomor Versi** (*Build Number*).
4. Ketuk baris **Nomor Versi** sebanyak **7 kali berturut-turut**.
5. Jika diminta, masukkan PIN atau pola kunci layar Anda.
6. Perangkat akan menampilkan notifikasi: *"Anda kini adalah seorang pengembang!"* (*You are now a developer!*).

### Langkah 2: Memilih Aplikasi Lokasi Tiruan
1. Kembali ke menu utama **Pengaturan**.
2. Masuk ke **Sistem** > **Opsi Pengembang** (*System* > *Developer Options*).
3. Gulir ke bagian kategori **Lokasi** (*Location*).
4. Ketuk opsi **Pilih aplikasi lokasi tiruan** (*Select mock location app*).
5. Pada daftar aplikasi yang muncul, pilih **Mari Location Lab**.

---

## 🖥️ 3. Panduan Penggunaan Modul Aplikasi

Aplikasi memiliki 5 tab utama yang dapat diakses melalui bilah navigasi bawah (*Bottom Navigation Bar*):

### 3.1 Tab Dashboard (Pusat Kendali)
Layar utama yang memberikan informasi status pengujian secara instan:
- **Badge Status QA ONLY**: Menandakan aplikasi beroperasi dalam lingkungan pengujian terkendali.
- **Status Konfigurasi Perangkat**:
  - *Opsi Pengembang*: Menampilkan status verifikasi.
  - *Aplikasi Lokasi Tiruan*: Menampilkan apakah Mari Location Lab telah dipilih. Jika sistem Android tidak memungkinkan verifikasi langsung, aplikasi akan menampilkan label kuning **"Perlu Verifikasi"** secara jujur.
  - *Izin Lokasi*: Menampilkan status izin GPS (Fine / Coarse).
- **Tombol Perbarui Status (Ikon Refresh)**: Ketuk tombol ini untuk memicu pengecekan ulang status perangkat setelah Anda selesai mengatur Opsi Pengembang.
- **Ringkasan Pengujian**: Menampilkan jumlah skenario yang tersedia dan sesi yang telah dijalankan.
- **Kartu Akses Cepat**: Pintasan menuju layar Peta, Skenario, Riwayat, dan Pengaturan.

---

### 3.2 Tab Peta & Validasi Koordinat
Modul untuk memvalidasi titik geografis sebelum dijadikan skenario:
- **Banner OpenStreetMap**: Menampilkan atribusi resmi data peta `© OpenStreetMap contributors (ODbL)`.
- **Formulir Input Manual**:
  - Masukkan nilai **Latitude** dalam rentang `-90.0` hingga `90.0` derajat desimal.
  - Masukkan nilai **Longitude** dalam rentang `-180.0` hingga `180.0` derajat desimal.
- **Validasi Nilai Koordinat**:
  - Ketuk tombol **Validasi Nilai Koordinat**.
  - Jika nilai valid: Muncul kotak hijau bertuliskan *"Koordinat valid. Memenuhi aturan PRD AC-02"*.
  - Jika nilai salah/di luar batas: Muncul kotak merah yang menjelaskan alasan spesifik penolakan (misal: *"Latitude harus berada di antara -90.0 dan 90.0"* atau *"Format latitude tidak valid"*).

---

### 3.3 Tab Skenario Pengujian
Modul untuk mengelola titik uji yang dapat digunakan ulang:
- **Daftar Skenario**: Menampilkan kartu-kartu skenario yang sudah tersimpan beserta kategori (*Geofence*, *Peta*, *Logistik*), deskripsi tujuan uji, dan koordinat lat/lon.
- **Tombol Tambah (+)**: Floating Action Button untuk membuat skenario baru.

---

### 3.4 Tab Riwayat Pengujian
Modul pencatatan eksekusi sesi simulasi:
- **Transparansi Privasi**: Catatan riwayat hanya mencatat ID skenario, waktu mulai, waktu selesai, dan status keberhasilan/kegagalan.
- **Perlindungan Data**: Koordinat GPS riil perangkat pengguna **TIDAK PERNAH** direkam atau disimpan ke dalam database.

---

### 3.5 Tab Pengaturan & Panduan
Pusat dokumentasi dalam aplikasi:
- Panduan terperinci aktivasi Opsi Pengembang.
- Tombol pintas **"Buka Pengaturan Developer Perangkat"** untuk langsung membuka pengaturan sistem Android.
- Penjelasan batasan etika QA resmi dan komitmen keamanan tanpa root.
- Informasi versi aplikasi dan package identifier.

---

## ❓ 4. Tanya Jawab & Penanganan Masalah (FAQ)

### Q1: Mengapa status Mock App menampilkan "Perlu Verifikasi"?
> **Jawab:** Pada sistem operasi Android versi modern (Android 11+), sistem operasi membatasi aplikasi biasa membaca daftar setting mock location secara global demi alasan keamanan privasi. Mari Location Lab tidak memalsukan status sukses dan menampilkan *"Perlu Verifikasi"* agar Anda memastikan setelan tersebut telah dipilih di Opsi Pengembang ponsel Anda.

### Q2: Mengapa aplikasi target saya menolak lokasi tiruan?
> **Jawab:** Aplikasi komersial tertentu (misal: perbankan atau ojek online) memiliki modul proteksi internal untuk menolak lokasi mock. Untuk tujuan pengujian yang sah, mintalah tim developer Anda untuk menyediakan **Build QA / Debug** dari aplikasi target yang membolehkan mock location selama fase pengujian internal. Mari Location Lab tidak memfasilitasi teknik *bypass* deteksi mock location.

### Q3: Apakah aplikasi ini membutuhkan koneksi internet?
> **Jawab:** Fitur dasar seperti validasi koordinat manual, manajemen skenario lokal, dan simulasi mock location dapat berjalan 100% offline tanpa sambungan internet. Internet hanya digunakan saat mengunduh tile OpenStreetMap interaktif pada modul peta.
