package com.maripartner.locationqa.data.repository

import com.maripartner.locationqa.domain.model.TestScenario
import com.maripartner.locationqa.domain.repository.ScenarioRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import java.util.concurrent.ConcurrentHashMap

/**
 * Implementasi in-memory repository untuk fondasi Tahap 1.
 * Akan digantikan oleh Room Database pada Tahap 3 sesuai rencana PRD.
 */
class ScenarioRepositoryImpl : ScenarioRepository {

    private val scenariosMap = ConcurrentHashMap<String, TestScenario>()
    private val scenariosFlow = MutableStateFlow<List<TestScenario>>(emptyList())

    init {
        // Data awal placeholder untuk demonstrasi pengujian authorized
        val sampleScenarios = listOf(
            TestScenario(
                id = "monas-jakarta",
                name = "Monas Jakarta Pusat",
                description = "Titik pengujian nol kilometer Jakarta untuk uji geofence radius 500m.",
                latitude = -6.175392,
                longitude = 106.827153,
                category = "Geofence"
            ),
            TestScenario(
                id = "tugu-pahlawan",
                name = "Tugu Pahlawan Surabaya",
                description = "Titik pengujian zona Jawa Timur untuk verifikasi akurasi aplikasi peta.",
                latitude = -7.245833,
                longitude = 112.737778,
                category = "Peta"
            )
        )
        sampleScenarios.forEach { scenariosMap[it.id] = it }
        scenariosFlow.value = sampleScenarios
    }

    override fun getScenarios(): Flow<List<TestScenario>> {
        return scenariosFlow.asStateFlow()
    }

    override suspend fun getScenarioById(id: String): TestScenario? {
        return scenariosMap[id]
    }

    override suspend fun saveScenario(scenario: TestScenario) {
        scenariosMap[scenario.id] = scenario
        scenariosFlow.value = scenariosMap.values.toList()
    }

    override suspend fun deleteScenario(id: String) {
        scenariosMap.remove(id)
        scenariosFlow.value = scenariosMap.values.toList()
    }

    override suspend fun getScenarioCount(): Int {
        return scenariosMap.size
    }
}
