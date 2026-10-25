package com.maripartner.locationqa.domain.model

import java.util.UUID

/**
 * Status eksekusi pengujian aplikasi penguji.
 */
enum class TestRunStatus(val label: String) {
    IDLE("Tidak Aktif"),
    RUNNING("Sedang Berjalan"),
    COMPLETED("Selesai"),
    STOPPED("Dihentikan"),
    FAILED("Gagal")
}

/**
 * Hasil eksekusi pengujian sesi simulasi.
 * Catatan penting: Tidak menyimpan koordinat riil perangkat.
 */
data class TestRunResult(
    val id: String = UUID.randomUUID().toString(),
    val scenarioId: String,
    val scenarioName: String,
    val startedAt: Long,
    val endedAt: Long? = null,
    val status: TestRunStatus = TestRunStatus.IDLE,
    val errorSummary: String? = null
)
