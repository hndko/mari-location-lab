package com.maripartner.locationqa

import com.maripartner.locationqa.data.repository.ScenarioRepositoryImpl
import com.maripartner.locationqa.domain.model.TestScenario
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ScenarioRepositoryTest {

    private lateinit var repository: ScenarioRepositoryImpl

    @Before
    fun setUp() {
        repository = ScenarioRepositoryImpl()
    }

    @Test
    fun `initial repository contains default QA sample scenarios`() = runTest {
        val scenarios = repository.getScenarios().first()
        assertTrue(scenarios.isNotEmpty())
        assertEquals(2, repository.getScenarioCount())
    }

    @Test
    fun `saveScenario adds or updates scenario successfully`() = runTest {
        val newScenario = TestScenario(
            id = "test-custom-1",
            name = "Bandara Soekarno-Hatta",
            description = "Terminal 3 untuk uji presisi kedatangan logistik",
            latitude = -6.1275,
            longitude = 106.6537,
            category = "Bandara"
        )

        repository.saveScenario(newScenario)

        val retrieved = repository.getScenarioById("test-custom-1")
        assertNotNull(retrieved)
        assertEquals("Bandara Soekarno-Hatta", retrieved?.name)
        assertEquals(-6.1275, retrieved?.latitude ?: 0.0, 0.0001)

        val count = repository.getScenarioCount()
        assertEquals(3, count)
    }

    @Test
    fun `deleteScenario removes scenario properly`() = runTest {
        val countBefore = repository.getScenarioCount()
        repository.deleteScenario("monas-jakarta")

        val retrieved = repository.getScenarioById("monas-jakarta")
        assertNull(retrieved)
        assertEquals(countBefore - 1, repository.getScenarioCount())
    }
}
