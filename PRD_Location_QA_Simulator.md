# PRD — Location QA Simulator (Fake GPS Android)

- **Versi:** 1.0
- **Tanggal:** 25 Oktober 2026
- **Platform:** Android
- **Bahasa:** Kotlin
- **UI:** Jetpack Compose + Material 3
- **Arsitektur:** MVVM + Clean Architecture ringan
- **Peta:** OpenStreetMap
- **Penyimpanan:** Room Database + DataStore
- **Distribusi target:** Google Play Store

## 1. Ringkasan Produk

Location QA Simulator adalah aplikasi Android untuk membantu developer dan QA menguji perilaku aplikasi berbasis lokasi pada kondisi yang dapat direproduksi. Pengguna dapat membuat skenario pengujian, menentukan koordinat dan parameter, menyimpan konfigurasi secara lokal, lalu menjalankan tes menggunakan mekanisme mock location resmi Android pada perangkat pengujian.

Aplikasi ditujukan untuk pengujian aplikasi milik sendiri atau yang penggunaannya telah diizinkan. Aplikasi harus transparan tentang status simulasi dan tidak dirancang untuk mengakali absensi, memalsukan kehadiran, melewati deteksi aplikasi pihak ketiga, atau menyamarkan status mock location.

## 2. Tujuan

- Mempercepat pengujian fitur berbasis lokasi tanpa berpindah secara fisik.
- Membuat hasil pengujian konsisten melalui skenario yang dapat digunakan ulang.
- Memudahkan pengujian validasi koordinat, geofencing, dan perubahan lokasi.
- Menampilkan status, hasil, serta batasan perangkat secara jelas.

## 3. Target Pengguna

- Android developer.
- QA engineer dan software tester.
- Tim pengembang aplikasi peta, logistik, pengiriman, dan geofencing.
- Tim internal yang menguji aplikasi di emulator atau perangkat Android khusus.

## 4. Scope MVP

### 4.1 Peta dan pemilihan lokasi

- Peta OpenStreetMap dengan marker.
- Memilih titik melalui interaksi peta.
- Input latitude dan longitude manual.
- Validasi latitude `-90..90` dan longitude `-180..180`.
- Menampilkan detail koordinat yang dipilih.
- Menampilkan atribusi OpenStreetMap secara jelas.
- Menangani tile error dan kondisi offline; jangan mengklaim peta tersedia offline jika belum didukung.

### 4.2 Manajemen skenario pengujian

- Membuat, melihat, mengubah, menggandakan, dan menghapus skenario.
- Menyimpan nama, deskripsi, koordinat, kategori, parameter tes, dan timestamp.
- Mengelompokkan skenario berdasarkan proyek atau kategori.
- Memuat ulang skenario tersimpan.
- Meminta konfirmasi sebelum tindakan destruktif.

### 4.3 Eksekusi dan hasil tes

- Menampilkan skenario yang dipilih dan status sesi.
- Memberikan panduan konfigurasi Developer Options dan mock location.
- Mencatat waktu mulai/selesai, status, dan ringkasan error.
- Menghentikan sesi dengan jelas.
- Membedakan status “skenario dimulai” dari “aplikasi target berhasil menerima hasil yang diharapkan”.
- Jika status konfigurasi tidak dapat dipastikan, tampilkan “Perlu diverifikasi”, bukan sukses.

### 4.4 Transparansi dan privasi

- Indikator yang jelas saat mode pengujian aktif.
- Tidak menyamarkan status mock location.
- Tidak memodifikasi data aplikasi lain.
- Tidak memerlukan root atau akses sistem yang tidak didukung.
- Penyimpanan lokal sebagai default.
- Tidak mengirim data lokasi ke server tanpa kebutuhan, pengungkapan, dan persetujuan yang sesuai.

## 5. Di Luar Scope MVP

- Login, akun, dan sinkronisasi cloud.
- Backend/API khusus.
- Pembayaran dan langganan.
- Pelacakan background untuk penggunaan umum.
- Melewati deteksi mock location aplikasi pihak ketiga.
- Manipulasi OS, root, atau penyamaran status simulasi.
- Otomatisasi terhadap aplikasi absensi atau aplikasi produksi pihak ketiga.

## 6. Arsitektur

Gunakan MVVM dengan Clean Architecture ringan:

- **Presentation:** Jetpack Compose, ViewModel, UI state.
- **Domain:** model, repository interfaces, use cases.
- **Data:** Room, DAO, repository implementations.
- **Platform:** adapter khusus Android untuk pemeriksaan konfigurasi/izin dan alur pengujian lokasi yang didukung.
- **Core:** navigation, common utilities, design system.

UI tidak boleh mengakses database atau API sistem secara langsung. Pisahkan validasi koordinat, persistensi, dan logika eksekusi tes agar dapat diuji secara terisolasi.

## 7. Stack Teknologi

| Area | Pilihan |
|---|---|
| Bahasa | Kotlin |
| UI | Jetpack Compose, Material 3 |
| Arsitektur | MVVM + Clean Architecture ringan |
| Database lokal | Room |
| Preferensi | DataStore |
| Asynchronous | Kotlin Coroutines + Flow |
| Dependency injection | Hilt jika memang dibutuhkan |
| Peta | OpenStreetMap melalui renderer/library yang masih aktif dan sesuai |
| Testing | JUnit, instrumented tests, Compose UI tests |
| Build | Gradle Kotlin DSL |
| Distribusi | Android App Bundle (AAB) |

OpenStreetMap adalah sumber data peta, bukan jaminan layanan tile gratis tanpa batas. Hormati kebijakan tile provider, cache, identifikasi aplikasi, atribusi, dan lisensi. Untuk rilis publik, evaluasi provider tile yang sesuai kebutuhan trafik.

## 8. Struktur Folder yang Disarankan

```text
app/
├── src/main/
│   ├── AndroidManifest.xml
│   └── java/com/maripartner/locationqa/
│       ├── LocationQaApplication.kt
│       ├── MainActivity.kt
│       ├── core/
│       │   ├── common/
│       │   ├── designsystem/
│       │   └── navigation/
│       ├── data/
│       │   ├── local/
│       │   │   ├── AppDatabase.kt
│       │   │   ├── ScenarioDao.kt
│       │   │   └── ScenarioEntity.kt
│       │   └── repository/
│       │       └── ScenarioRepositoryImpl.kt
│       ├── domain/
│       │   ├── model/
│       │   │   ├── TestScenario.kt
│       │   │   └── TestRunResult.kt
│       │   ├── repository/
│       │   │   └── ScenarioRepository.kt
│       │   └── usecase/
│       │       ├── SaveScenarioUseCase.kt
│       │       ├── DeleteScenarioUseCase.kt
│       │       └── ValidateCoordinatesUseCase.kt
│       ├── platform/
│       │   ├── mocklocation/
│       │   └── permissions/
│       └── feature/
│           ├── dashboard/
│           ├── map/
│           ├── scenarios/
│           ├── testexecution/
│           ├── history/
│           └── settings/
└── src/test/
    └── java/com/maripartner/locationqa/
```

Package `com.maripartner.locationqa` adalah contoh dan harus disesuaikan dengan package final. Jangan membuat satu Activity/ViewModel yang menampung semua fitur. Hindari operasi database di main thread. Gunakan Flow untuk data reaktif dan migrasi Room saat skema berubah.

## 9. Model Data

### TestScenario

| Field | Tipe | Keterangan |
|---|---|---|
| `id` | UUID/String | ID unik |
| `name` | String | Nama skenario |
| `description` | String nullable | Tujuan pengujian |
| `latitude` | Double | Latitude |
| `longitude` | Double | Longitude |
| `category` | String | Misalnya map atau geofence |
| `parametersJson` | String nullable | Parameter tambahan tervalidasi |
| `createdAt` | Instant/Long | Waktu dibuat |
| `updatedAt` | Instant/Long | Waktu diperbarui |

### TestRunResult

Simpan hasil terpisah dari skenario. Minimal: ID hasil, referensi skenario, waktu mulai, waktu selesai, status, dan ringkasan error. Jangan menyimpan koordinat aktual perangkat atau data sensitif yang tidak relevan.

## 10. Acceptance Criteria

| ID | Pengujian | Kriteria lulus |
|---|---|---|
| AC-01 | Membuat skenario | Skenario tersimpan dengan ID unik |
| AC-02 | Input koordinat | Nilai valid diterima; nilai di luar rentang ditolak |
| AC-03 | Peta | Marker mengikuti titik yang dipilih |
| AC-04 | CRUD skenario | Buat, lihat, edit, duplikasi, hapus bekerja dengan benar |
| AC-05 | Persistensi | Skenario tersedia setelah aplikasi dibuka kembali |
| AC-06 | Mock location belum dikonfigurasi | Panduan ditampilkan dan tes tidak dilaporkan berhasil |
| AC-07 | Mulai tes | Sesi dicatat dengan status awal yang benar |
| AC-08 | Hentikan tes | Sesi berakhir dan hasil disimpan |
| AC-09 | Error | Error sistem/kompatibilitas ditampilkan tanpa crash |
| AC-10 | Transparansi | Status pengujian terlihat dan tidak disamarkan |
| AC-11 | Database offline | CRUD skenario lokal tetap berfungsi tanpa internet |
| AC-12 | Peta offline | Kegagalan tile ditangani; tidak mengklaim dukungan offline yang tidak tersedia |
| AC-13 | Privasi | Tidak ada pengiriman data lokasi tanpa kebutuhan dan pengungkapan yang sesuai |
| AC-14 | Rilis | Build release berhasil, tanda tangan valid, dan pemeriksaan pra-rilis selesai |

AC-07 dan AC-08 hanya membuktikan sesi aplikasi berjalan/berakhir. Verifikasi bahwa aplikasi target menerima perilaku yang diharapkan harus dilakukan terpisah melalui tes integrasi pada aplikasi milik sendiri.

## 11. Skenario QA Wajib

- Koordinat di khatulistiwa dan belahan bumi berbeda.
- Nilai tepat pada batas rentang dan nilai invalid.
- Aplikasi target hanya mendapat izin approximate location.
- Pengguna membuka Settings lalu kembali ke aplikasi.
- Aplikasi masuk background atau proses dihentikan.
- Jaringan peta lambat, offline, atau tile gagal dimuat.
- Database kosong, skenario duplikat, dan migrasi database.
- Perbedaan perilaku pada versi Android yang didukung.
- Pengujian integrasi dengan aplikasi target milik sendiri.

## 12. Risiko Kompatibilitas dan Mitigasi

| Risiko | Dampak | Mitigasi |
|---|---|---|
| Versi Android/vendor berbeda | Perilaku lokasi tidak konsisten | Uji emulator dan perangkat fisik |
| Mock location tidak aktif | Tes tidak menghasilkan data yang diharapkan | Panduan Developer Options dan verifikasi ulang |
| Aplikasi target mendeteksi mock location | Data tes ditandai atau ditolak | Gunakan build debug/environment QA milik sendiri; jangan bypass deteksi |
| Pembatasan background | Sesi dapat dihentikan sistem | Utamakan sesi foreground dan lifecycle yang benar |
| Perubahan izin | Aplikasi target kehilangan akses | Uji perubahan izin dan restart |
| Tile peta gagal | Pemilihan lokasi via peta terganggu | Error state dan input koordinat manual |
| Kebijakan tile OSM | Provider dapat membatasi penggunaan | Gunakan provider yang sesuai, cache, dan atribusi |
| Kebijakan Google Play | Publikasi ditolak | Tinjau fungsi aktual, listing, privasi, dan kebijakan terbaru |

Jangan mengasumsikan aplikasi dapat membaca semua status Developer Options secara andal. Jika status tidak dapat dipastikan, tampilkan “Perlu diverifikasi”. Kelayakan Google Play tidak dijamin; kebijakan yang berlaku harus ditinjau sebelum rilis.

## 13. Tahapan Pengembangan

1. **Fondasi:** setup Kotlin, Compose, navigation, theme, Gradle, lint, dan unit test.
2. **Peta:** integrasi OpenStreetMap, marker, input koordinat, validasi, atribusi, dan error tile.
3. **Skenario:** Room, DAO, repository, use cases, daftar dan form CRUD.
4. **Integrasi QA:** alur mock location yang didukung Android, status sesi, pencatatan hasil, dan error handling.
5. **Rilis:** unit/UI/integration tests, audit manifest/permission/dependensi, AAB, screenshot, listing, privacy disclosure, dan internal testing.

Estimasi awal satu developer yang menguasai Kotlin: sekitar 3–5 minggu, dengan asumsi scope tetap, tanpa backend, dan integrasi berjalan sesuai rencana. Ini perkiraan, bukan jaminan.

## 14. Persiapan Google Play Store

- Nama, ikon, screenshot, dan deskripsi sesuai fungsi aktual.
- Privacy Policy disediakan bila diperlukan.
- Data Safety akurat terhadap praktik pengumpulan/pembagian data.
- Permission dijelaskan dan dibatasi pada kebutuhan.
- Atribusi OpenStreetMap terlihat.
- Tidak ada debug tools atau rahasia pada build release.
- Uji versi Android yang ditargetkan.
- AAB ditandatangani dan diuji melalui internal testing.
- Periksa kembali target API dan kebijakan Play Store terbaru menjelang rilis.

## 15. Definition of Done

MVP selesai jika:

1. Semua fitur dalam scope MVP berfungsi.
2. Acceptance criteria lulus di emulator dan minimal satu perangkat fisik.
3. Skenario dapat disimpan dan digunakan ulang.
4. Status simulasi dan hasil tes ditampilkan secara akurat.
5. Error, izin, dan ketidakcocokan perangkat ditangani.
6. Tidak ada bug kritis atau kebocoran data yang diketahui dari pengujian.
7. Build release berhasil dan siap ditinjau untuk publikasi Google Play.
