package com.maripartner.locationqa.domain.usecase

import com.maripartner.locationqa.domain.model.MockLocationConfigStatus
import com.maripartner.locationqa.domain.model.VerificationState

/**
 * UseCase untuk mengevaluasi status kesiapan konfigurasi pengujian mock location.
 * Memastikan prinsip kejujuran: jika platform tidak dapat memastikan status,
 * tampilkan "Perlu Verifikasi" alih-alih mengklaim sukses palsu.
 */
class CheckMockLocationConfigUseCase {

    fun execute(
        isDeveloperOptionsActive: Boolean,
        isMockAppSelected: Boolean,
        hasFineLocation: Boolean,
        hasCoarseLocation: Boolean,
        isSessionRunning: Boolean = false,
        activeScenario: String? = null
    ): MockLocationConfigStatus {
        val devState = if (isDeveloperOptionsActive) {
            VerificationState.VERIFIED_ACTIVE
        } else {
            VerificationState.NEEDS_VERIFICATION
        }

        val mockState = when {
            isMockAppSelected -> VerificationState.VERIFIED_ACTIVE
            isDeveloperOptionsActive -> VerificationState.NEEDS_VERIFICATION
            else -> VerificationState.NOT_CONFIGURED
        }

        val statusMsg = when {
            !hasFineLocation -> "Izin lokasi (Fine Location) diperlukan untuk memverifikasi posisi pengujian."
            mockState == VerificationState.NEEDS_VERIFICATION -> "Pastikan Mari Location Lab telah dipilih di 'Pilih aplikasi lokasi tiruan' pada Opsi Pengembang."
            mockState == VerificationState.NOT_CONFIGURED -> "Opsi Pengembang atau aplikasi lokasi tiruan belum dikonfigurasi."
            isSessionRunning -> "Sesi pengujian aktif untuk skenario: ${activeScenario ?: "Tanpa nama"}"
            else -> "Konfigurasi terverifikasi. Siap menjalankan pengujian."
        }

        return MockLocationConfigStatus(
            isDeveloperOptionsEnabled = isDeveloperOptionsActive,
            developerOptionsState = devState,
            isMockLocationAppSet = isMockAppSelected,
            mockLocationAppState = mockState,
            hasFineLocationPermission = hasFineLocation,
            hasCoarseLocationPermission = hasCoarseLocation,
            isSessionActive = isSessionRunning,
            activeScenarioName = activeScenario,
            statusMessage = statusMsg
        )
    }
}
