package com.maripartner.locationqa

import com.maripartner.locationqa.domain.model.VerificationState
import com.maripartner.locationqa.domain.usecase.CheckMockLocationConfigUseCase
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CheckMockLocationConfigUseCaseTest {

    private lateinit var useCase: CheckMockLocationConfigUseCase

    @Before
    fun setUp() {
        useCase = CheckMockLocationConfigUseCase()
    }

    @Test
    fun `when developer options and mock app are active, returns verified active`() {
        val result = useCase.execute(
            isDeveloperOptionsActive = true,
            isMockAppSelected = true,
            hasFineLocation = true,
            hasCoarseLocation = true
        )

        assertEquals(VerificationState.VERIFIED_ACTIVE, result.developerOptionsState)
        assertEquals(VerificationState.VERIFIED_ACTIVE, result.mockLocationAppState)
        assertTrue(result.hasFineLocationPermission)
        assertTrue(result.statusMessage.contains("Konfigurasi terverifikasi"))
    }

    @Test
    fun `when developer options active but mock app not verified, returns needs verification`() {
        val result = useCase.execute(
            isDeveloperOptionsActive = true,
            isMockAppSelected = false,
            hasFineLocation = true,
            hasCoarseLocation = true
        )

        assertEquals(VerificationState.VERIFIED_ACTIVE, result.developerOptionsState)
        assertEquals(VerificationState.NEEDS_VERIFICATION, result.mockLocationAppState)
        assertTrue(result.statusMessage.contains("Opsi Pengembang"))
    }

    @Test
    fun `when neither developer options nor mock app active, returns not configured`() {
        val result = useCase.execute(
            isDeveloperOptionsActive = false,
            isMockAppSelected = false,
            hasFineLocation = true,
            hasCoarseLocation = true
        )

        assertEquals(VerificationState.NEEDS_VERIFICATION, result.developerOptionsState)
        assertEquals(VerificationState.NOT_CONFIGURED, result.mockLocationAppState)
    }

    @Test
    fun `when fine location permission is missing, status warns user`() {
        val result = useCase.execute(
            isDeveloperOptionsActive = true,
            isMockAppSelected = true,
            hasFineLocation = false,
            hasCoarseLocation = false
        )

        assertFalse(result.hasFineLocationPermission)
        assertTrue(result.statusMessage.contains("Izin lokasi"))
    }

    @Test
    fun `when session is running, status displays active scenario name`() {
        val result = useCase.execute(
            isDeveloperOptionsActive = true,
            isMockAppSelected = true,
            hasFineLocation = true,
            hasCoarseLocation = true,
            isSessionRunning = true,
            activeScenario = "Uji Monas"
        )

        assertTrue(result.isSessionActive)
        assertEquals("Uji Monas", result.activeScenarioName)
        assertTrue(result.statusMessage.contains("Uji Monas"))
    }
}
