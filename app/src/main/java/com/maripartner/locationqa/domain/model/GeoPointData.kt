package com.maripartner.locationqa.domain.model

import java.util.Locale

/**
 * Model domain murni untuk representasi koordinat geografis.
 * Bebas dependensi Android framework atau library peta pihak ketiga.
 */
data class GeoPointData(
    val latitude: Double,
    val longitude: Double
) {
    fun toFormattedCoordinates(): String {
        return String.format(Locale.US, "%.6f, %.6f", latitude, longitude)
    }

    companion object {
        // Titik awal default pengujian (Pusat Jakarta / Monas)
        val DEFAULT = GeoPointData(
            latitude = -6.175392,
            longitude = 106.827153
        )
    }
}
