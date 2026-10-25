package com.maripartner.locationqa

import com.maripartner.locationqa.domain.model.CoordinateValidationResult
import com.maripartner.locationqa.domain.usecase.ValidateCoordinatesUseCase
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ValidateCoordinatesUseCaseTest {

    private lateinit var useCase: ValidateCoordinatesUseCase

    @Before
    fun setUp() {
        useCase = ValidateCoordinatesUseCase()
    }

    @Test
    fun `execute returns Valid for valid coordinates in Indonesia`() {
        // Monas Jakarta Pusat
        val result = useCase.execute(-6.175392, 106.827153)
        assertTrue(result is CoordinateValidationResult.Valid)
    }

    @Test
    fun `execute returns Valid for equator and prime meridian`() {
        val result = useCase.execute(0.0, 0.0)
        assertTrue(result is CoordinateValidationResult.Valid)
    }

    @Test
    fun `execute returns Valid for boundary coordinates`() {
        assertTrue(useCase.execute(90.0, 180.0) is CoordinateValidationResult.Valid)
        assertTrue(useCase.execute(-90.0, -180.0) is CoordinateValidationResult.Valid)
        assertTrue(useCase.execute(90.0, -180.0) is CoordinateValidationResult.Valid)
        assertTrue(useCase.execute(-90.0, 180.0) is CoordinateValidationResult.Valid)
    }

    @Test
    fun `execute returns Invalid when latitude exceeds 90 degrees`() {
        val result = useCase.execute(90.0001, 100.0)
        assertTrue(result is CoordinateValidationResult.Invalid)
        assertEquals(
            "Latitude harus berada di antara -90.0 dan 90.0.",
            (result as CoordinateValidationResult.Invalid).reason
        )
    }

    @Test
    fun `execute returns Invalid when latitude is less than minus 90 degrees`() {
        val result = useCase.execute(-90.0001, 100.0)
        assertTrue(result is CoordinateValidationResult.Invalid)
        assertEquals(
            "Latitude harus berada di antara -90.0 dan 90.0.",
            (result as CoordinateValidationResult.Invalid).reason
        )
    }

    @Test
    fun `execute returns Invalid when longitude exceeds 180 degrees`() {
        val result = useCase.execute(10.0, 180.0001)
        assertTrue(result is CoordinateValidationResult.Invalid)
        assertEquals(
            "Longitude harus berada di antara -180.0 dan 180.0.",
            (result as CoordinateValidationResult.Invalid).reason
        )
    }

    @Test
    fun `execute returns Invalid when longitude is less than minus 180 degrees`() {
        val result = useCase.execute(10.0, -180.0001)
        assertTrue(result is CoordinateValidationResult.Invalid)
        assertEquals(
            "Longitude harus berada di antara -180.0 dan 180.0.",
            (result as CoordinateValidationResult.Invalid).reason
        )
    }

    @Test
    fun `execute returns Invalid for NaN or Infinite values`() {
        val nanResult = useCase.execute(Double.NaN, 100.0)
        assertTrue(nanResult is CoordinateValidationResult.Invalid)

        val infResult = useCase.execute(0.0, Double.POSITIVE_INFINITY)
        assertTrue(infResult is CoordinateValidationResult.Invalid)
    }

    @Test
    fun `execute with string arguments parses correctly and returns Valid`() {
        val result = useCase.execute(" -6.2088 ", " 106.8456 ")
        assertTrue(result is CoordinateValidationResult.Valid)
    }

    @Test
    fun `execute with empty strings returns Invalid with informative message`() {
        val emptyLat = useCase.execute("", "100.0")
        assertTrue(emptyLat is CoordinateValidationResult.Invalid)
        assertEquals(
            "Latitude tidak boleh kosong.",
            (emptyLat as CoordinateValidationResult.Invalid).reason
        )

        val emptyLon = useCase.execute("-6.2", "")
        assertTrue(emptyLon is CoordinateValidationResult.Invalid)
        assertEquals(
            "Longitude tidak boleh kosong.",
            (emptyLon as CoordinateValidationResult.Invalid).reason
        )
    }

    @Test
    fun `execute with non-numeric strings returns Invalid format message`() {
        val invalidLat = useCase.execute("abc", "106.8")
        assertTrue(invalidLat is CoordinateValidationResult.Invalid)
        assertTrue((invalidLat as CoordinateValidationResult.Invalid).reason.contains("Format latitude tidak valid"))

        val invalidLon = useCase.execute("-6.2", "xyz")
        assertTrue(invalidLon is CoordinateValidationResult.Invalid)
        assertTrue((invalidLon as CoordinateValidationResult.Invalid).reason.contains("Format longitude tidak valid"))
    }
}
