package com.maripartner.locationqa.domain.usecase

import com.maripartner.locationqa.domain.model.CoordinateValidationResult

/**
 * UseCase untuk memvalidasi nilai koordinat geografis (latitude dan longitude).
 * Mematuhi aturan batas PRD:
 * - Latitude: -90.0 s/d 90.0
 * - Longitude: -180.0 s/d 180.0
 */
class ValidateCoordinatesUseCase {

    fun execute(latitude: Double, longitude: Double): CoordinateValidationResult {
        if (latitude.isNaN() || latitude.isInfinite()) {
            return CoordinateValidationResult.Invalid("Latitude bukan angka yang valid.")
        }
        if (longitude.isNaN() || longitude.isInfinite()) {
            return CoordinateValidationResult.Invalid("Longitude bukan angka yang valid.")
        }
        if (latitude < -90.0 || latitude > 90.0) {
            return CoordinateValidationResult.Invalid("Latitude harus berada di antara -90.0 dan 90.0.")
        }
        if (longitude < -180.0 || longitude > 180.0) {
            return CoordinateValidationResult.Invalid("Longitude harus berada di antara -180.0 dan 180.0.")
        }
        return CoordinateValidationResult.Valid
    }

    fun execute(latString: String, lonString: String): CoordinateValidationResult {
        val cleanLat = latString.trim()
        val cleanLon = lonString.trim()

        if (cleanLat.isEmpty()) {
            return CoordinateValidationResult.Invalid("Latitude tidak boleh kosong.")
        }
        if (cleanLon.isEmpty()) {
            return CoordinateValidationResult.Invalid("Longitude tidak boleh kosong.")
        }

        val lat = cleanLat.toDoubleOrNull()
            ?: return CoordinateValidationResult.Invalid("Format latitude tidak valid. Gunakan format angka desimal (contoh: -6.2088).")
        val lon = cleanLon.toDoubleOrNull()
            ?: return CoordinateValidationResult.Invalid("Format longitude tidak valid. Gunakan format angka desimal (contoh: 106.8456).")

        return execute(lat, lon)
    }
}
