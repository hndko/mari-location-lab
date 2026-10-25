# 🚀 04. Panduan Build, Rilis & Deployment Google Play Store

Dokumen ini memuat prosedur teknis untuk mempersiapkan, menandatangani, dan menerbitkan aplikasi **Mari Location Lab** (`com.maripartner.locationqa`) ke Google Play Store atau pengujian internal (*Internal Testing Track*).

---

## 📦 1. Pembuatan Paket Aplikasi Android

### 1.1 Kompilasi APK Debug (Untuk Pengujian Tim Internal)
```bash
./gradlew assembleDebug
```
- **Output**: `app/build/outputs/apk/debug/app-debug.apk`
- Ditandatangani otomatis dengan debug keystore Android Studio default.

### 1.2 Pembuatan Keystore Rilis Produksi
Untuk menandatangani paket rilis publik, buat keystore resmi (simpan berkas ini di lokasi yang aman dan jangan pernah di-commit ke Git):
```bash
keytool -genkey -v -keystore release.jks -alias marilocation -keyalg RSA -keysize 2048 -validity 10000
```

### 1.3 Kompilasi Android App Bundle (AAB) Rilis
Google Play mewajibkan format rilis menggunakan **Android App Bundle (.aab)**:
```bash
./gradlew bundleRelease
```
- **Output**: `app/build/outputs/bundle/release/app-release.aab`

---

## 🔒 2. Audit Izin Manifest (Permission Hygiene)

Sebelum pengajuan rilis ke Google Play Store, lakukan audit terhadap `AndroidManifest.xml`:

```xml
<!-- Izin resmi yang diizinkan untuk fungsi pengujian lokasi -->
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

### Verifikasi Keamanan:
- ✅ **Tidak ada izin berbahaya berlebih**: Tidak meminta `READ_PHONE_STATE`, `READ_CONTACTS`, `CAMERA`, atau `RECORD_AUDIO`.
- ✅ **Tidak ada izin root**: Tidak memerlukan `android.permission.ACCESS_SUPERUSER`.
- ✅ **Mock Location**: Tidak menggunakan trik tersembunyi; mock location resmi diaktifkan oleh pengguna melalui Developer Options OS.

---

## 🛡️ 3. Kepatuhan Formulir Data Safety Google Play Store

Saat mengisi kuesioner **Data Safety** di Google Play Console, jawab sesuai implementasi riil aplikasi:

| Pertanyaan Play Console | Jawaban Resmi | Penjelasan Kepatuhan |
|---|---|---|
| **Apakah aplikasi mengumpulkan data lokasi pengguna?** | **TIDAK** | Aplikasi hanya membaca GPS lokal untuk verifikasi status pengujian dan tidak pernah mengirimnya ke server mana pun. |
| **Apakah data dibagikan ke pihak ketiga?** | **TIDAK** | Tidak ada SDK analitik pihak ketiga, jaringan iklan, atau pelacak eksternal. |
| **Apakah pengguna dapat meminta penghapusan data?** | **YA** | Pengguna dapat menghapus skenario tersimpan secara langsung atau menghapus data aplikasi via Pengaturan Sistem Android (*Clear Data*). |
| **Apakah data dienkripsi saat transit?** | **N/A** | Tidak ada transmisi jaringan untuk data lokasi atau profil pengguna. |

---

## 🗺️ 4. Kepatuhan Kebijakan Tile OpenStreetMap (OSM)

- **Atribusi ODbL**: Layar peta wajib memuat teks atribusi `© OpenStreetMap contributors` yang terlihat jelas.
- **User-Agent Header**: Wajib mengirimkan User-Agent unik yang mengidentifikasi aplikasi secara sah:
  ```text
  User-Agent: MariLocationLab/1.0.0 (com.maripartner.locationqa; Android QA Simulator)
  ```
- **Larangan Bulk Scraping**: Dilarang melakukan bulk download ubin peta secara masif dari server publik OpenStreetMap tanpa persetujuan khusus.

---

## ✅ 5. Checklist Kesiapan Produksi (Quality Gate Rilis)

Sebelum mengajukan rilis versi baru ke Play Console, pastikan checklist berikut terpenuhi:

- [ ] Seluruh pengujian unit lulus 100%: `./gradlew test`.
- [ ] Pemeriksaan linter tidak menemukan error: `./gradlew lint`.
- [ ] Versi aplikasi telah disinkronkan:
  - `app/build.gradle.kts` (`versionCode` dan `versionName`).
  - `README.md` (badge versi).
  - `AGENTS.md` (metadata versi).
- [ ] Tangkapan layar listing toko aplikasi tidak memuat klaim palsu atau menjanjikan fitur bypass aplikasi pihak ketiga.
- [ ] Paket `.aab` ditandatangani dengan keystore rilis resmi dan diuji pada minimal satu perangkat fisik Android.
