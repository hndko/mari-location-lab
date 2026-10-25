package com.maripartner.locationqa.platform.mocklocation

import android.app.AppOpsManager
import android.content.Context
import android.os.Build
import android.os.Process
import android.provider.Settings
import com.maripartner.locationqa.domain.model.VerificationState

/**
 * Pemeriksa konfigurasi status mock location pada platform Android.
 * Mematuhi prinsip transparansi: tidak memalsukan status sukses dan
 * menampilkan 'Perlu Verifikasi' apabila platform membatasi akses verifikasi langsung.
 */
class MockLocationChecker(private val context: Context) {

    /**
     * Memeriksa apakah Opsi Pengembang (Developer Options) aktif pada perangkat.
     */
    fun isDeveloperOptionsEnabled(): Boolean {
        return try {
            Settings.Global.getInt(
                context.contentResolver,
                Settings.Global.DEVELOPMENT_SETTINGS_ENABLED,
                0
            ) != 0
        } catch (_: Exception) {
            false
        }
    }

    /**
     * Memeriksa apakah aplikasi ini telah dipilih sebagai aplikasi mock location resmi di Opsi Pengembang.
     * Menggunakan AppOpsManager.OPSTR_MOCK_LOCATION yang didukung sejak Android 6.0+.
     */
    fun isMockLocationAppSet(): Boolean {
        return try {
            val appOpsManager = context.getSystemService(Context.APP_OPS_SERVICE) as? AppOpsManager
                ?: return false
            val mode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                appOpsManager.unsafeCheckOpNoThrow(
                    AppOpsManager.OPSTR_MOCK_LOCATION,
                    Process.myUid(),
                    context.packageName
                )
            } else {
                @Suppress("DEPRECATION")
                appOpsManager.checkOpNoThrow(
                    AppOpsManager.OPSTR_MOCK_LOCATION,
                    Process.myUid(),
                    context.packageName
                )
            }
            mode == AppOpsManager.MODE_ALLOWED
        } catch (_: Exception) {
            false
        }
    }

    /**
     * Mendapatkan state verifikasi komprehensif.
     */
    fun getVerificationState(): VerificationState {
        val devEnabled = isDeveloperOptionsEnabled()
        val mockSet = isMockLocationAppSet()

        return when {
            devEnabled && mockSet -> VerificationState.VERIFIED_ACTIVE
            devEnabled -> VerificationState.NEEDS_VERIFICATION
            else -> VerificationState.NOT_CONFIGURED
        }
    }
}
