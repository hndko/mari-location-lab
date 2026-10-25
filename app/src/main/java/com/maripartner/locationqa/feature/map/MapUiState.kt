package com.maripartner.locationqa.feature.map

import com.maripartner.locationqa.domain.model.CoordinateValidationResult
import com.maripartner.locationqa.domain.model.GeoPointData
import java.util.Locale

/**
 * State UI untuk modul Peta dan Pemilihan Lokasi.
 */
data class MapUiState(
    val selectedLocation: GeoPointData = GeoPointData.DEFAULT,
    val latitudeInput: String = String.format(Locale.US, "%.6f", GeoPointData.DEFAULT.latitude),
    val longitudeInput: String = String.format(Locale.US, "%.6f", GeoPointData.DEFAULT.longitude),
    val validationResult: CoordinateValidationResult? = null,
    val isMapLoading: Boolean = false,
    val errorMessage: String? = null,
    val userNotification: String? = null,
    val mapZoomLevel: Double = 15.0,
    val isNetworkUnavailable: Boolean = false
)
