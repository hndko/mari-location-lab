package com.maripartner.locationqa.domain.repository

import com.maripartner.locationqa.domain.model.TestScenario
import kotlinx.coroutines.flow.Flow

/**
 * Interface repository untuk mengelola persistensi skenario pengujian.
 */
interface ScenarioRepository {
    fun getScenarios(): Flow<List<TestScenario>>
    suspend fun getScenarioById(id: String): TestScenario?
    suspend fun saveScenario(scenario: TestScenario)
    suspend fun deleteScenario(id: String)
    suspend fun getScenarioCount(): Int
}
