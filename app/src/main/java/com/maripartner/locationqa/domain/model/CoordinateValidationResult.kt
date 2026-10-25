package com.maripartner.locationqa.domain.model

/**
 * Hasil validasi koordinat geografis (latitude dan longitude).
 */
sealed interface CoordinateValidationResult {
    data object Valid : CoordinateValidationResult
    data class Invalid(val reason: String) : CoordinateValidationResult
}
