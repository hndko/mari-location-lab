# 💻 01. Daftar Perintah Wajib & Operasional (CLI Reference)

Dokumen ini menyediakan referensi perintah baris perintah (*Command Line Interface*) yang digunakan sehari-hari untuk kompilasi, pengujian unit, instalasi emulator, penelusuran log, dan pembuatan paket rilis pada proyek **Mari Location Lab**.

---

## 📌 1. Ringkasan Parameter Lingkungan

| Parameter | Nilai Resmi | Keterangan |
|---|---|---|
| **Application ID** | `com.maripartner.locationqa` | Package name resmi aplikasi |
| **Java Home** | `C:\Users\Kyoo\.jdks\jbr-21.0.11` | OpenJDK 21 LTS |
| **Android SDK Root** | `C:\Users\Kyoo\AppData\Local\Android\Sdk` | Android SDK Platform 35 |
| **Gradle Wrapper** | `8.7` (didistribusikan via `./gradlew`) | Menggunakan Gradle Kotlin DSL |
| **Main Activity Intent** | `com.maripartner.locationqa/.MainActivity` | Entry point launcher aplikasi |

---

## 🛠️ 2. Perintah Build & Kompilasi Gradle

Jalankan perintah ini di direktori root repositori (`mari-location-lab/`):

### 2.1 Menjalankan Pengujian Unit (Unit Tests)
```bash
# Menjalankan seluruh test suite (Debug & Release)
./gradlew test

# Menjalankan unit test khusus Debug dengan stacktrace lengkap
./gradlew testDebugUnitTest --stacktrace

# Menjalankan kelas test spesifik (contoh: validasi koordinat)
./gradlew test --tests "com.maripartner.locationqa.ValidateCoordinatesUseCaseTest"
```

### 2.2 Kompilasi APK Debug
```bash
# Kompilasi APK Debug
./gradlew assembleDebug

# Lokasi output APK Debug:
# app/build/outputs/apk/debug/app-debug.apk
```

### 2.3 Kompilasi APK & Bundle Rilis (Production)
```bash
# Kompilasi Android App Bundle (.aab) untuk Google Play Store
./gradlew bundleRelease

# Kompilasi APK rilis mandiri
./gradlew assembleRelease

# Lokasi output:
# app/build/outputs/bundle/release/app-release.aab
# app/build/outputs/apk/release/app-release-unsigned.apk
```

### 2.4 Pemeriksaan Kualitas & Pembersihan
```bash
# Menjalankan static analysis linting
./gradlew lint

# Membersihkan berkas build dan cache lama
./gradlew clean
```

---

## 📱 3. Manajemen Emulator Android & ADB

### 3.1 Manajemen AVD (Android Virtual Device)
```bash
# Melihat daftar AVD yang terpasang
emulator -list-avds

# Menjalankan emulator AVD test_avd di latar belakang (tanpa audio & animasi boot)
emulator -avd test_avd -no-snapshot-load -no-audio -no-boot-anim
```

### 3.2 Menghubungkan & Memeriksa Status Perangkat
```bash
# Memeriksa daftar perangkat / emulator yang aktif
adb devices

# Menunggu hingga emulator menyelesaikan proses booting
adb wait-for-device shell "while [ \"\$(getprop sys.boot_completed)\" != \"1\" ]; do sleep 2; done; echo 'BOOT_COMPLETED'"
```

### 3.3 Instalasi & Eksekusi Aplikasi
```bash
# Menginstal atau memperbarui APK ke emulator
adb install -r app/build/outputs/apk/debug/app-debug.apk

# Menjalankan aplikasi langsung ke layar depan
adb shell am start -n com.maripartner.locationqa/.MainActivity

# Menutup aplikasi secara paksa
adb shell am force-stop com.maripartner.locationqa
```

### 3.4 Diagnostik & Penelusuran Log Runtime (Logcat)
```bash
# Memeriksa PID proses yang sedang berjalan
adb shell pidof com.maripartner.locationqa

# Menampilkan log error dan crash hanya untuk proses Mari Location Lab
adb logcat -d --pid=$(adb shell pidof com.maripartner.locationqa) | grep -E "FATAL|AndroidRuntime|Exception|Error"

# Mengambil tangkapan layar (screenshot) layar emulator ke komputer lokal
adb exec-out screencap -p > screenshot.png
```

### 3.5 Pengujian Tema Sistem (Light & Dark Mode)
```bash
# Mengaktifkan mode gelap (Dark Mode)
adb shell cmd uimode night yes

# Mengembalikan ke mode terang (Light Mode)
adb shell cmd uimode night no
```

---

## 📦 4. Git & Conventional Commits

Format commit wajib mengikuti aturan standar:
```text
<type>(<scope>): <subject>
```

### Contoh Perintah Commit Harian:
```bash
# Menambah fitur baru
git commit -m "feat(map): add osmdroid interactive map view"

# Memperbaiki bug atau kesalahan logika
git commit -m "fix(nav): resolve bottom navigation bar initialization NPE"

# Memperbarui dokumentasi
git commit -m "docs(commands): add adb uimode commands reference"

# Memperbarui dependency atau build script
git commit -m "chore(gradle): bump targetSdk to 35"
```

---

## 📋 5. Tabel Ringkasan Cepat (Daily Cheatsheet)

| Kebutuhan Operasional | Perintah Baris Perintah |
|---|---|
| **Jalankan Seluruh Test** | `./gradlew test` |
| **Build APK Debug Cepat** | `./gradlew assembleDebug` |
| **Instal APK ke Perangkat** | `adb install -r app/build/outputs/apk/debug/app-debug.apk` |
| **Buka Aplikasi di Device** | `adb shell am start -n com.maripartner.locationqa/.MainActivity` |
| **Cek Crash di Logcat** | `adb logcat -d -s AndroidRuntime` |
| **Tes Mode Gelap** | `adb shell cmd uimode night yes` |
| **Tes Mode Terang** | `adb shell cmd uimode night no` |
| **Bersihkan Cache Build** | `./gradlew clean` |
