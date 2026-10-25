package com.maripartner.locationqa.domain.model

/**
 * Status verifikasi konfigurasi sistem untuk pengujian mock location.
 * Nilai jujur dan tidak menyamarkan status asli perangkat.
 */
enum class VerificationState(val label: String) {
    VERIFIED_ACTIVE("Aktif & Terverifikasi"),
    NEEDS_VERIFICATION("Perlu Verifikasi"),
    NOT_CONFIGURED("Belum Dikonfigurasi"),
    UNAVAILABLE("Tidak Tersedia")
}

/**
 * Representasi status kesiapan perangkat untuk pengujian simulasi lokasi.
 */
data class MockLocationConfigStatus(
    val isDeveloperOptionsEnabled: Boolean = false,
    val developerOptionsState: VerificationState = VerificationState.NEEDS_VERIFICATION,
    val isMockLocationAppSet: Boolean = false,
    val mockLocationAppState: VerificationState = VerificationState.NEEDS_VERIFICATION,
    val hasFineLocationPermission: Boolean = false,
    val hasCoarseLocationPermission: Boolean = false,
    val isSessionActive: Boolean = false,
    val activeScenarioName: String? = null,
    val statusMessage: String = "Perlu verifikasi manual melalui Opsi Pengembang perangkat."
)
