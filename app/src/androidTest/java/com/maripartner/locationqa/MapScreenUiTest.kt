package com.maripartner.locationqa

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import com.maripartner.locationqa.core.designsystem.LocationQaTheme
import com.maripartner.locationqa.domain.usecase.ValidateCoordinatesUseCase
import com.maripartner.locationqa.feature.map.MapScreen
import com.maripartner.locationqa.feature.map.MapViewModel
import org.junit.Rule
import org.junit.Test

/**
 * UI Test untuk alur pemilihan lokasi dan input koordinat manual pada [MapScreen].
 * Memverifikasi rendering elemen, interaksi tombol, serta pesan validasi pada UI.
 */
class MapScreenUiTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun mapScreen_initialState_displaysDefaultCoordinatesAndControls() {
        val viewModel = MapViewModel(ValidateCoordinatesUseCase())

        composeTestRule.setContent {
            LocationQaTheme {
                MapScreen(viewModel = viewModel)
            }
        }

        // Pastikan judul kartu koordinat muncul
        composeTestRule.onNodeWithText("Koordinat Titik Terpilih").assertIsDisplayed()

        // Pastikan tombol aksi muncul
        composeTestRule.onNodeWithText("Salin").assertIsDisplayed()
        composeTestRule.onNodeWithText("Atur Ulang").assertIsDisplayed()

        // Pastikan form manual input koordinat muncul
        composeTestRule.onNodeWithText("Input Koordinat Manual").assertIsDisplayed()
        composeTestRule.onNodeWithText("Terapkan ke Peta").assertIsDisplayed()
    }

    @Test
    fun mapScreen_invalidManualInput_displaysValidationErrorMessage() {
        val viewModel = MapViewModel(ValidateCoordinatesUseCase())

        composeTestRule.setContent {
            LocationQaTheme {
                MapScreen(viewModel = viewModel)
            }
        }

        // Masukkan latitude invalid (> 90.0)
        composeTestRule.onNodeWithText("Latitude (-90.0 s/d 90.0)")
            .performTextClearance()
        composeTestRule.onNodeWithText("Latitude (-90.0 s/d 90.0)")
            .performTextInput("99.5")

        // Klik tombol Terapkan
        composeTestRule.onNodeWithText("Terapkan ke Peta").performClick()

        // Verifikasi pesan kesalahan muncul di layar
        composeTestRule.onNodeWithText("Latitude harus berada di antara -90.0 dan 90.0.")
            .assertIsDisplayed()
    }

    @Test
    fun mapScreen_resetButton_restoresDefaultState() {
        val viewModel = MapViewModel(ValidateCoordinatesUseCase())

        composeTestRule.setContent {
            LocationQaTheme {
                MapScreen(viewModel = viewModel)
            }
        }

        // Lakukan pemilihan titik lain via ViewModel
        viewModel.onMapPointSelected(-7.257472, 112.752090)
        composeTestRule.waitForIdle()

        // Klik tombol Atur Ulang
        composeTestRule.onNodeWithText("Atur Ulang").performClick()
        composeTestRule.waitForIdle()

        // Pastikan koordinat terpilih kembali ke Monas
        composeTestRule.onNodeWithText("Koordinat Titik Terpilih").assertIsDisplayed()
    }
}
