package com.maripartner.locationqa.domain.model

import java.util.UUID

/**
 * Model domain skenario pengujian lokasi.
 */
data class TestScenario(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String? = null,
    val latitude: Double,
    val longitude: Double,
    val category: String = "Umum",
    val parametersJson: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
