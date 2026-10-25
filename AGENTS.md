# 🤖 AGENTS.md — Panduan & Aturan Utama Pengembangan Proyek Mari Location Lab

Dokumen ini adalah sumber acuan hukum dan aturan rekayasa perangkat lunak tertinggi bagi seluruh AI Coding Assistant dan pengembang yang bekerja pada repositori **Mari Location Lab** (`com.maripartner.locationqa`). Seluruh instruksi dalam dokumen ini bersifat mengikat dan wajib dipatuhi tanpa pengecualian. Jika aturan bertentangan, urutan prioritasnya adalah: **keamanan dan integritas data**, **instruksi pengguna saat ini**, **dokumen ini (AGENTS.md)**, **blueprint/dokumentasi (PRD)**, lalu **pola kode lama**.

---

## 📌 Metadata Proyek

- **Nama Aplikasi**: **Mari Location Lab** (Location QA Simulator)
- **Application ID / Package**: `com.maripartner.locationqa`
- **Versi Saat Ini**: `1.0.0`
- **Platform & Bahasa**: Android / Kotlin 2.0.21
- **UI Framework**: Jetpack Compose + Material 3 (Compose BOM 2024.10.00)
- **Arsitektur**: MVVM dengan Lightweight Clean Architecture
- **Penyimpanan Lokal**: Room Database + DataStore Preferences (Local-Only, Zero Cloud Sync di MVP)
- **Modul Peta**: OpenStreetMap (OSM) dengan atribusi ODbL wajib
- **Target SDK**: Compile SDK 35, Target SDK 35, Min SDK 26 (Android 8.0 Oreo)
- **Runtime Environment**: Java 21 LTS (OpenJDK 21.0.11), Gradle 8.7
- **Dokumentasi Proyek**: [Portal Dokumentasi](docs/README.md) & [Panduan Pengembang](docs/03-developer-guide.md)
- **Histori Rilis**: `README.md`
- **Sumber Versi Runtime**: `app/build.gradle.kts` (`versionName = "1.0.0"`, `versionCode = 1`)

---

## 📋 Daftar Isi

1. [🏛️ 10 Pilar Aturan Wajib AI Assistant & Pengembang](#-10-pilar-aturan-wajib-ai-assistant--pengembang)
   - [🎯 1. Peran & Prinsip Kerja Utama (Role & Prime Directives)](#-1-peran--prinsip-kerja-utama-role--prime-directives)
   - [🏗️ 2. Arsitektur Android & Pemisahan Tanggung Jawab (Clean Architecture)](#-2-arsitektur-android--pemisahan-tanggung-jawab-clean-architecture)
   - [📄 3. View Murni Presentasional & Bebas Akses Database / Platform Langsung](#-3-view-murni-presentasional--bebas-akses-database--platform-langsung)
   - [🏢 4. Data Scoping, Local-First & Privasi Lokasi Tanpa Kompromi](#-4-data-scoping-local-first--privasi-lokasi-tanpa-kompromi)
   - [🎨 5. Standar UI/UX Material 3, Tema Terang/Gelap & Zero-CDN Component](#-5-standar-uiux-material-3-tema-teranggelap--zero-cdn-component)
   - [📊 6. Standardisasi Validasi Koordinat & Input Numerik](#-6-standardisasi-validasi-koordinat--input-numerik)
   - [🔒 7. Standar Keamanan OWASP Mobile & Batasan Etika QA Resmi](#-7-standar-keamanan-owasp-mobile--batasan-etika-qa-resmi)
   - [🧪 8. Standar Pengujian Mutu (Quality Gates) & Definition of Done](#-8-standar-pengujian-mutu-quality-gates--definition-of-done)
   - [📦 9. Aturan Git Commit & Push Otomatis & SemVer](#-9-aturan-git-commit--push-otomatis--semver)
   - [🔄 10. Kewajiban Pengkinian Dokumen (Mandatory Sync)](#-10-kewajiban-pengkinian-dokumen-mandatory-sync)
2. [📁 Konsep Tata Letak Direktori (Folder-Foldering Hierarchy)](#-konsep-tata-letak-direktori-folder-foldering-hierarchy)
   - [🌳 Diagram Pohon Direktori](#-diagram-pohon-direktori)
   - [🧩 Penjelasan Modul Direktori Proyek](#-penjelasan-modul-direktori-proyek)
3. [🛡️ Standar Wajib Audit Kualitas & Kesiapan Produksi (Quality Gate)](#-standar-wajib-audit-kualitas--kesiapan-produksi-quality-gate)
4. [🗺️ Ketentuan Penggunaan OpenStreetMap (OSM)](#-ketentuan-penggunaan-openstreetmap-osm)
5. [📱 Persiapan Google Play Store & Kebijakan Rilis](#-persiapan-google-play-store--kebijakan-rilis)

---

## 🏛️ 10 Pilar Aturan Wajib AI Assistant & Pengembang

```text
┌──────────────────────────────────────────────────────────────────────────────────┐
│              10 PILAR ATURAN WAJIB PENGEMBANGAN MARI LOCATION LAB                │
├───────────────────┬───────────────────┬───────────────────┬──────────────────────┤
│ 1. Prime Directive│ 2. Clean Arch MVVM│ 3. View No-Direct │ 4. Local-Only Privacy│
│ 5. Material 3 UX  │ 6. Coordinate Val │ 7. Transparent QA │ 8. Quality Gates DoD │
│ 9. SemVer AutoPush│ 10. Mandatory Sync│                   │                      │
└───────────────────┴───────────────────┴───────────────────┴──────────────────────┘
```

---

### 🎯 1. Peran & Prinsip Kerja Utama (Role & Prime Directives)

1. **Senior Engineering Mindset**: Bertindak sebagai Senior Android Engineer, Mobile Security Auditor, dan Tech Lead. Selalu utamakan solusi jangka panjang, arsitektur bersih, type-safety, dan minim dependensi redundan.
2. **Nol Asumsi & Non-Halusinasi**: Selalu baca berkas langsung di sistem file sebelum melakukan edit atau memberikan kesimpulan. Dilarang berasumsi nama fungsi, dependensi, tabel Room, atau manifest entry ada tanpa verifikasi nyata.
3. **Preservasi Invarian Domain**: Jangan merusak batasan koordinat geografis (-90..90 latitude, -180..180 longitude), siklus hidup skenario, integritas timestamp, atau kontrak UI State.
4. **Verifikasi Fungsional Nyata**: Dilarang mengklaim modul fungsional hanya karena file Composable atau ViewModel sudah dibuat. Verifikasi kompilasi Gradle, unit test, dan runtime execution di emulator Android. Jangan pernah menyatakan tes lulus jika tes tidak benar-benar dijalankan.

---

### 🏗️ 2. Arsitektur Android & Pemisahan Tanggung Jawab (Clean Architecture)

Proyek mengadopsi pemisahan tanggung jawab yang tegas:

1. **Layering yang Jelas**:
   - **`core/`**: Utility umum (`UiState`), Design System (Tema, Warna, Tipografi, Shape, Komponen UI umum), dan Navigasi (`AppNavHost`, `Screen`, `AppBottomBar`).
   - **`domain/`**: Model murni Kotlin bebas dependensi Android UI (`TestScenario`, `TestRunResult`, `MockLocationConfigStatus`), UseCases (`ValidateCoordinatesUseCase`, `CheckMockLocationConfigUseCase`), dan Repository Interfaces.
   - **`data/`**: Implementasi repository, Room Database, Entity, DAO, dan DataStore preferences.
   - **`platform/`**: Abstraksi adapter OS Android khusus (pemeriksaan Opsi Pengembang, Mock Location provider, dan runtime permissions).
   - **`feature/`**: Modul fungsional per layar (Dashboard, Peta, Skenario, Riwayat, Pengaturan) yang memisahkan Composable Screen, ViewModel, dan UI State.
2. **Thin Activity & ViewModel as Orchestrator**: `MainActivity` hanya bertindak sebagai entry point container. ViewModel hanya mengelola UI State dan memanggil UseCase / Repository. Dilarang membuat "God ViewModel" atau "God Activity".
3. **Database & Platform Isolation**: Dilarang menjalankan operasi database di Main Thread. Gunakan Coroutine Dispatchers (`Dispatchers.IO`) dan reaktif Flow.

---

### 📄 3. View Murni Presentasional & Bebas Akses Database / Platform Langsung

1. **Zero Direct DB / Platform Calls in Composables**: Dilarang keras memanggil DAO, Database, atau API sistem Android sensitif langsung dari dalam fungsi `@Composable`.
2. **Immutable UI State**: Layar Composable hanya me-render data yang diekspos melalui `StateFlow<UiState>` dari ViewModel dan mengalirkan event interaksi pengguna kembali ke ViewModel via lambda callbacks.
3. **Standardisasi State UI**: Setiap modul layar wajib mendukung minimal 4 keadaan:
   - `Loading` (indikator progres pemuatan)
   - `Success` (data berhasil dimuat/ditampilkan)
   - `Empty` (data kosong dengan panduan tindakan lanjutan yang jelas)
   - `Error` (pesan kegagalan ramah pengguna dengan tombol *Coba Lagi*)

---

### 🏢 4. Data Scoping, Local-First & Privasi Lokasi Tanpa Kompromi

1. **Local Storage by Default**: Seluruh data skenario dan riwayat pengujian tersimpan secara lokal pada perangkat pengguna via Room Database.
2. **Zero Cloud Transmission**: MVP tidak memiliki backend, akun, analitik pelacak, iklan, atau sinkronisasi cloud.
3. **Pelarangan Penyimpanan Koordinat Riil**: Dilarang merekam, menyimpan, atau mengekspor posisi koordinat GPS riil perangkat pengguna ke dalam database tanpa otorisasi eksplisit. Riwayat pengujian hanya mencatat ID skenario, waktu mulai/selesai, status simulasi, dan ringkasan error.

---

### 🎨 5. Standar UI/UX Material 3, Tema Terang/Gelap & Zero-CDN Component

1. **Material 3 Design System**:
   - Menggunakan palette warna tech-blue/teal modern dengan kontras tinggi (WCAG AA).
   - Mendukung penuh **Light Mode** dan **Dark Mode** (teruji via Android Dynamic Color & `uimode night`).
2. **Zero External CDN**:
   - Seluruh aset grafis (ikon, font, vektor) disimpan secara lokal di dalam repositori (`res/drawable/` atau `androidx.compose.material.icons`).
   - Dilarang mengunduh font web atau aset UI secara dinamis saat runtime dari internet.
3. **Larangan Dialog Bawaan Browser / Mentah**:
   - Gunakan dialog Material 3 (`AlertDialog`, `ModalBottomSheet`, atau snackbar modern).
4. **Sentuhan Aksesibel**:
   - Ukuran target sentuh minimal 48dp x 48dp.
   - Bahasa antarmuka default menggunakan **Bahasa Indonesia** yang baku dan komunikatif.

---

### 📊 6. Standardisasi Validasi Koordinat & Input Numerik

1. **Validasi Rentang Geografis Mutlak**:
   - Latitude: `-90.0` s/d `90.0` (derajat desimal).
   - Longitude: `-180.0` s/d `180.0` (derajat desimal).
   - Tolak nilai `NaN`, `Infinity`, teks kosong, atau format string tidak valid.
2. **Format Input**: Input koordinat manual harus memotong whitespace dan memvalidasi sebelum delegasi ke modul simulasi atau penyimpanan.
3. **Pencarian Real-Time**: Input pencarian atau filter skenario wajib menggunakan debounce minimal 300ms untuk efisiensi komputasi dan memori.

---

### 🔒 7. Standar Keamanan OWASP Mobile & Batasan Etika QA Resmi

1. **Prinsip Kejujuran Simulasi (No Bypass / No Stealth)**:
   - Dilarang membuat fitur untuk melewati, mengelabui, atau menyembunyikan status mock location dari aplikasi lain.
   - Dilarang memanipulasi aplikasi pihak ketiga, sistem presensi/absensi, atau game berbasis lokasi.
   - Tidak memerlukan akses **Root** atau modifikasi ROM tidak resmi.
2. **Transparansi Status Konfigurasi**:
   - Jika status Opsi Pengembang atau aplikasi mock location tidak dapat dipastikan secara andal oleh platform Android, tampilkan status **"Perlu Verifikasi"**, bukan mengklaim sukses palsu.
3. **Audit Izin Manifest**:
   - Hanya deklarasikan izin yang benar-benar esensial (`ACCESS_FINE_LOCATION`, `ACCESS_COARSE_LOCATION`, `INTERNET`, `ACCESS_NETWORK_STATE`).
   - Dilarang meminta izin berbahaya (*dangerous permissions*) yang tidak ada kaitannya dengan fungsi pengujian lokasi.

---

### 🧪 8. Standar Pengujian Mutu (Quality Gates) & Definition of Done

Sebuah fitur atau tahapan pengembangan dinyatakan selesai (*Definition of Done*) jika dan hanya jika:
- Kode mematuhi spesifikasi PRD;
- Input validation dan seluruh UI states (Loading, Empty, Error) terimplementasi;
- Unit test dibuat dan lulus 100% tanpa error;
- Linting lulus tanpa error fatal;
- Kompilasi APK debug/release berhasil;
- Aplikasi terverifikasi berjalan stabil di emulator Android atau perangkat fisik tanpa crash;
- Dokumentasi proyek disinkronkan.

**Perintah Verifikasi Wajib**:
```bash
# Menjalankan seluruh pengujian unit otomatis
./gradlew test

# Memeriksa kompilasi APK debug
./gradlew assembleDebug

# Memverifikasi static analysis & linting
./gradlew lint
```

---

### 📦 9. Aturan Git Commit & Push Otomatis & SemVer

1. **Format Conventional Commits**:
   ```text
   <type>(<scope>): <subject>
   ```
   - `feat`: Fitur baru.
   - `fix`: Perbaikan bug atau penanganan error.
   - `docs`: Dokumentasi proyek (`README.md`, `AGENTS.md`, `docs/`).
   - `style`: Format kode atau penyesuaian visual tanpa mengubah logika.
   - `refactor`: Restrukturisasi kode tanpa mengubah fungsionalitas.
   - `test`: Penambahan atau perbaikan unit test.
   - `chore`: Konfigurasi build Gradle, dependensi, atau SemVer bump.

   *Aturan Penulisan:*
   - Gunakan kata kerja imperatif (`add`, `fix`, `update`, `refactor`).
   - Judul subjek maksimal 100 karakter.
   - Dilarang mengakhiri judul commit dengan tanda titik (`.`).

2. **Kewajiban Sinkronisasi Versi Serempak (Strict Multi-File Version Sync)**:
   Nomor versi aplikasi (`MAJOR.MINOR.PATCH`) wajib dinaikkan secara serempak di:
   - `app/build.gradle.kts` (`versionName` dan `versionCode`)
   - `README.md` (badge versi dan metadata)
   - `AGENTS.md` (metadata proyek)

---

### 🔄 10. Kewajiban Pengkinian Dokumen (Mandatory Sync)

1. **Pengkinian `AGENTS.md`**: Setiap kali ada penyesuaian arsitektur atau aturan rekayasa baru, catat langsung di dokumen ini.
2. **Pengkinian `README.md`**: Setiap penambahan modul, perubahan UI, atau kenaikan versi wajib langsung dimutakhirkan di `README.md`.
3. **Pengkinian `docs/`**: Perbarui panduan CLI, panduan pengguna, developer guide, dan alur rilis setiap kali terjadi perubahan fungsional signifikan.

---

## 📁 Konsep Tata Letak Direktori (Folder-Foldering Hierarchy)

### 🌳 Diagram Pohon Direktori

```text
mari-location-lab/
├── .gitignore                          # Aturan abaikan berkas build, cache, dan SDK lokal
├── AGENTS.md                           # Dokumen aturan tertinggi AI Coding Agent (Single Source of Truth)
├── PRD_Location_QA_Simulator.md        # Spesifikasi kebutuhan produk (Product Requirement Document)
├── README.md                           # Dokumentasi portal repositori utama
├── build.gradle.kts                    # Konfigurasi Gradle root project
├── settings.gradle.kts                 # Manajemen modul & plugin repository
├── gradle.properties                   # Parameter memori JVM & opsi build Android
├── local.properties                    # Path Android SDK lokal pengembang
├── lint.xml                            # Aturan static analysis lint Android
├── docs/                               # Suite dokumentasi proyek komprehensif
│   ├── README.md                       # Portal navigasi dokumentasi (Documentation Hub)
│   ├── 01-daftar-command.md            # Referensi perintah CLI (Gradle, ADB, emulator, testing)
│   ├── 02-buku-panduan-pengguna.md     # Buku panduan manual pengguna & QA tester
│   ├── 03-developer-guide.md           # Panduan arsitektur pengembang & Clean Architecture
│   └── 04-panduan-deployment.md        # Panduan build APK/AAB & kepatuhan rilis Google Play
├── gradle/wrapper/                     # Gradle wrapper binary & konfigurasi versi (Gradle 8.7)
└── app/
    ├── build.gradle.kts                # Konfigurasi dependensi, SDK 35, Compose, dan plugins
    ├── proguard-rules.pro              # Konfigurasi optimasi & obfusikasi rilis
    └── src/
        ├── main/
        │   ├── AndroidManifest.xml     # Deklarasi permissions, Application, dan MainActivity
        │   ├── java/com/maripartner/locationqa/
        │   │   ├── LocationQaApplication.kt    # Entry point Application class
        │   │   ├── MainActivity.kt             # Single-Activity container Jetpack Compose
        │   │   ├── core/
        │   │   │   ├── common/                 # Wrapper UiState & utility umum
        │   │   │   ├── designsystem/           # Theme, Color, Type, Shape, dan reusable components
        │   │   │   └── navigation/             # Routing Screen, NavHost, dan AppBottomBar
        │   │   ├── data/
        │   │   │   ├── local/                  # Room Database, DAO, Entity (Tahap 3)
        │   │   │   └── repository/             # Implementasi ScenarioRepository
        │   │   ├── domain/
        │   │   │   ├── model/                  # Domain model (TestScenario, TestRunResult, dsb.)
        │   │   │   ├── repository/             # Interface kontrak ScenarioRepository
        │   │   │   └── usecase/                # Business logic (ValidateCoordinates, CheckConfig)
        │   │   ├── platform/
        │   │   │   ├── mocklocation/           # Adapter sistem Android mock location checker
        │   │   │   └── permissions/            # Helper pemeriksaan status izin lokasi
        │   │   └── feature/
        │   │       ├── dashboard/              # Dashboard ringkasan & status pengujian
        │   │       ├── map/                    # Modul Peta OSM & validasi koordinat manual
        │   │       ├── scenarios/              # Modul daftar & formulir skenario uji
        │   │       ├── history/                # Modul riwayat sesi pengujian
        │   │       └── settings/               # Panduan Opsi Pengembang & informasi aplikasi
        │   └── res/
        │       ├── drawable/                   # Vektor ikon launcher & aset grafis lokal
        │       ├── mipmap-anydpi-v26/          # Adaptive launcher icon XML
        │       └── values/                     # Strings Indonesia, colors, dan themes XML
        └── test/java/com/maripartner/locationqa/
            ├── ValidateCoordinatesUseCaseTest.kt # Pengujian batas koordinat (-90..90, -180..180)
            ├── CheckMockLocationConfigUseCaseTest.kt # Pengujian status transparansi konfigurasi
            └── ScenarioRepositoryTest.kt       # Pengujian operasi repository skenario
```

---

### 🧩 Penjelasan Modul Direktori Proyek

1. **`core/designsystem/`**: Berisi fondasi visual Material 3 (`Theme.kt`, `Color.kt`, `Type.kt`, `Shape.kt`) dan komponen umum siap pakai (`AppTopBar`, `AppCard`, `LoadingView`, `EmptyStateView`, `ErrorStateView`).
2. **`core/navigation/`**: Mengelola navigasi aplikasi berbasis `androidx.navigation.compose`, mendefinisikan rute sealed class `Screen`, dan bottom navigation bar yang responsif.
3. **`domain/`**: Jantung logika bisnis aplikasi yang bersih dari framework Android UI. Berisi data class murni dan UseCase yang dapat diuji secara terisolasi tanpa emulator.
4. **`data/`**: Bertanggung jawab atas persistensi data. Menyediakan abstraksi repository in-memory untuk Tahap 1 dan Room Database untuk Tahap 3.
5. **`platform/`**: Mengisolasi interaksi langsung dengan Android OS (`Settings.Global`, `AppOpsManager`, `ContextCompat`) di balik kontrak yang rapi.
6. **`feature/`**: Setiap fitur memiliki folder mandiri yang merangkum Composable UI, ViewModel, dan UI State.

---

## 🛡️ Standar Wajib Audit Kualitas & Kesiapan Produksi (Quality Gate)

Sebelum kode dinyatakan siap rilis (*production-ready*) atau tahapan dianggap selesai, AI Assistant dan pengembang wajib memverifikasi 9 pilar audit berikut:

| No | Pilar Kualitas | Aspek yang Wajib Diverifikasi | Standar Wajib Mari Location Lab |
|:--:|---|---|---|
| **1** | **Thread Safety & Coroutines** | Operasi database & I/O berat di luar Main Thread | Gunakan `Dispatchers.IO`, `Flow`, dan hindari blocking call di UI |
| **2** | **UX & Accessibility** | Responsive layout, dark/light mode, touch targets | Minimum touch target 48dp, WCAG AA contrast, loading/empty/error states |
| **3** | **OWASP Mobile Security** | Zero-leakage koordinat riil, aman dari manipulasi | Local storage only, tidak mengirim koordinat ke cloud, tidak ada bypass detection |
| **4** | **Input Boundary Validation** | Validasi rentang latitude/longitude geografis | Tolak nilai di luar `-90..90` dan `-180..180`, tangani format invalid & NaN |
| **5** | **Lifecycle & Process Recreation**| Siklus hidup Activity, ViewModel recreation | Gunakan `rememberSaveable`, ViewModel StateFlow, tangani rotasi layar |
| **6** | **Memory Leaks & Cleanup** | Listener GPS, tile cache OSM, event subscription | Lepaskan listener lokasi saat sesi berhenti atau Activity dihancurkan |
| **7** | **Offline & Fault Tolerance** | Penanganan tile failure, ketiadaan koneksi internet | UI state graceful error, fallback manual coordinate input, tidak klaim palsu |
| **8** | **Zero Hardcoded Secrets** | Bebas credential, token, atau API key berbahaya | Zero secret di source code, `.gitignore` aktif untuk file sensitif |
| **9** | **Lint & Automated Tests** | Zero lint error & 100% test pass rate | `./gradlew test` dan `./gradlew assembleDebug` wajib sukses |

---

## 🗺️ Ketentuan Penggunaan OpenStreetMap (OSM)

- **Atribusi Wajib**: Wajib menampilkan atribusi `© OpenStreetMap contributors` secara terlihat pada layar peta.
- **Kepatuhan Tile Usage Policy**: Hormati rate-limiting tile provider resmi OpenStreetMap. Selalu konfigurasi User-Agent aplikasi yang sah (`com.maripartner.locationqa`).
- **Penyimpanan Cache & Akses Offline**: Jangan mengklaim dukungan peta offline kepada pengguna kecuali mekanisme cache lokal telah diimplementasikan secara legal dan terverifikasi.
- **Abstraksi Peta**: Desain integrasi renderer peta agar mudah diganti jika di masa mendatang penyedia tile memerlukan migrasi.

---

## 📱 Persiapan Google Play Store & Kebijakan Rilis

- **Transparansi Listing**: Judul, deskripsi, dan tangkapan layar toko aplikasi wajib secara jujur mencerminkan fungsi aplikasi sebagai alat bantu pengujian lokasi resmi bagi pengembang dan tim QA.
- **Data Safety Declaration**: Deklarasikan secara akurat bahwa aplikasi tidak mengumpulkan atau membagikan data lokasi pengguna ke pihak ketiga.
- **Minimalisasi Izin**: Tinjau `AndroidManifest.xml` sebelum rilis untuk memastikan tidak ada izin berlebih yang tidak digunakan.
- **Format Paket Rilis**: Bangun artefak rilis dalam format Android App Bundle (AAB) yang ditandatangani (*signed release bundle*).
