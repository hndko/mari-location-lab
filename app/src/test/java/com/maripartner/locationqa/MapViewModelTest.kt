package com.maripartner.locationqa

import com.maripartner.locationqa.domain.model.CoordinateValidationResult
import com.maripartner.locationqa.domain.model.GeoPointData
import com.maripartner.locationqa.domain.usecase.ValidateCoordinatesUseCase
import com.maripartner.locationqa.feature.map.MapViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Unit Test untuk [MapViewModel].
 * Menguji sinkronisasi dua arah interaksi peta, validasi batasan koordinat manual,
 * aksi salin, reset, serta penanganan notifikasi dan status jaringan.
 */
class MapViewModelTest {

    private lateinit var validateCoordinatesUseCase: ValidateCoordinatesUseCase
    private lateinit var viewModel: MapViewModel

    @Before
    fun setUp() {
        validateCoordinatesUseCase = ValidateCoordinatesUseCase()
        viewModel = MapViewModel(validateCoordinatesUseCase)
    }

    @Test
    fun initialState_isMonasDefaultLocationAndFormattedInputs() {
        val state = viewModel.uiState.value

        assertEquals(GeoPointData.DEFAULT.latitude, state.selectedLocation.latitude, 0.000001)
        assertEquals(GeoPointData.DEFAULT.longitude, state.selectedLocation.longitude, 0.000001)
        assertTrue(state.latitudeInput.startsWith("-6.175"))
        assertTrue(state.longitudeInput.startsWith("106.827"))
        assertNull(state.errorMessage)
        assertNull(state.userNotification)
        assertEquals(15.0, state.mapZoomLevel, 0.001)
    }

    @Test
    fun onMapPointSelected_withValidCoordinates_updatesSelectedLocationAndInputs() {
        val targetLat = -7.797068 // Yogyakarta
        val targetLon = 110.370529

        viewModel.onMapPointSelected(targetLat, targetLon)
        val state = viewModel.uiState.value

        assertEquals(targetLat, state.selectedLocation.latitude, 0.000001)
        assertEquals(targetLon, state.selectedLocation.longitude, 0.000001)
        assertTrue(state.latitudeInput.startsWith("-7.797"))
        assertTrue(state.longitudeInput.startsWith("110.370"))
        assertTrue(state.validationResult is CoordinateValidationResult.Valid)
        assertNull(state.errorMessage)
    }

    @Test
    fun onMapPointSelected_withInvalidCoordinates_setsErrorMessage() {
        val invalidLat = 95.0 // Di luar batas 90.0
        val targetLon = 106.8

        viewModel.onMapPointSelected(invalidLat, targetLon)
        val state = viewModel.uiState.value

        // Lokasi terpilih tidak boleh berubah ke nilai invalid
        assertEquals(GeoPointData.DEFAULT.latitude, state.selectedLocation.latitude, 0.000001)
        assertTrue(state.validationResult is CoordinateValidationResult.Invalid)
        assertNotNull(state.errorMessage)
    }

    @Test
    fun onLatitudeInputChanged_updatesInputAndClearsValidation() {
        viewModel.onLatitudeInputChanged("-6.500000")
        val state = viewModel.uiState.value

        assertEquals("-6.500000", state.latitudeInput)
        assertNull(state.validationResult)
        assertNull(state.errorMessage)
    }

    @Test
    fun onLongitudeInputChanged_updatesInputAndClearsValidation() {
        viewModel.onLongitudeInputChanged("107.200000")
        val state = viewModel.uiState.value

        assertEquals("107.200000", state.longitudeInput)
        assertNull(state.validationResult)
        assertNull(state.errorMessage)
    }

    @Test
    fun applyManualCoordinates_withValidValues_updatesSelectedLocation() {
        viewModel.onLatitudeInputChanged("-6.917464") // Bandung
        viewModel.onLongitudeInputChanged("107.619123")

        viewModel.applyManualCoordinates()
        val state = viewModel.uiState.value

        assertEquals(-6.917464, state.selectedLocation.latitude, 0.000001)
        assertEquals(107.619123, state.selectedLocation.longitude, 0.000001)
        assertTrue(state.validationResult is CoordinateValidationResult.Valid)
        assertNull(state.errorMessage)
        assertNotNull(state.userNotification)
    }

    @Test
    fun applyManualCoordinates_withBoundaryViolation_setsErrorAndDoesNotChangeLocation() {
        val originalLat = viewModel.uiState.value.selectedLocation.latitude
        val originalLon = viewModel.uiState.value.selectedLocation.longitude

        // Longitude > 180.0
        viewModel.onLatitudeInputChanged("-6.200000")
        viewModel.onLongitudeInputChanged("185.000000")

        viewModel.applyManualCoordinates()
        val state = viewModel.uiState.value

        assertEquals(originalLat, state.selectedLocation.latitude, 0.000001)
        assertEquals(originalLon, state.selectedLocation.longitude, 0.000001)
        assertTrue(state.validationResult is CoordinateValidationResult.Invalid)
        assertNotNull(state.errorMessage)
    }

    @Test
    fun applyManualCoordinates_withInvalidFormat_setsError() {
        viewModel.onLatitudeInputChanged("abc")
        viewModel.onLongitudeInputChanged("106.827")

        viewModel.applyManualCoordinates()
        val state = viewModel.uiState.value

        assertTrue(state.validationResult is CoordinateValidationResult.Invalid)
        assertNotNull(state.errorMessage)
    }

    @Test
    fun resetToDefaultLocation_restoresDefaultMonasPoint() {
        // Pindah dulu ke titik lain
        viewModel.onMapPointSelected(-7.257472, 112.752090) // Surabaya

        // Lalu atur ulang
        viewModel.resetToDefaultLocation()
        val state = viewModel.uiState.value

        assertEquals(GeoPointData.DEFAULT.latitude, state.selectedLocation.latitude, 0.000001)
        assertEquals(GeoPointData.DEFAULT.longitude, state.selectedLocation.longitude, 0.000001)
        assertNotNull(state.userNotification)
        assertNull(state.errorMessage)
    }

    @Test
    fun notifyCoordinatesCopied_setsNotificationMessage() {
        viewModel.notifyCoordinatesCopied()
        val state = viewModel.uiState.value

        assertNotNull(state.userNotification)
        assertTrue(state.userNotification!!.contains("disalin ke clipboard"))
    }

    @Test
    fun clearNotification_clearsNotificationMessage() {
        viewModel.notifyCoordinatesCopied()
        viewModel.clearNotification()
        val state = viewModel.uiState.value

        assertNull(state.userNotification)
    }

    @Test
    fun onNetworkStatusChanged_offline_setsWarningMessage() {
        viewModel.onNetworkStatusChanged(true)
        val state = viewModel.uiState.value

        assertTrue(state.isNetworkUnavailable)
        assertNotNull(state.errorMessage)
        assertTrue(state.errorMessage!!.contains("offline"))

        viewModel.onNetworkStatusChanged(false)
        val onlineState = viewModel.uiState.value
        assertEquals(false, onlineState.isNetworkUnavailable)
        assertNull(onlineState.errorMessage)
    }
}
