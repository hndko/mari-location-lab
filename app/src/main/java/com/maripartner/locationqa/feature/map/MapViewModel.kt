package com.maripartner.locationqa.feature.map

import androidx.lifecycle.ViewModel
import com.maripartner.locationqa.domain.model.CoordinateValidationResult
import com.maripartner.locationqa.domain.model.GeoPointData
import com.maripartner.locationqa.domain.usecase.ValidateCoordinatesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Locale

/**
 * ViewModel untuk modul Peta & Pemilihan Lokasi.
 * Mengelola sinkronisasi interaksi peta dan input koordinat manual secara dua arah.
 */
class MapViewModel @JvmOverloads constructor(
    private val validateCoordinatesUseCase: ValidateCoordinatesUseCase = ValidateCoordinatesUseCase()
) : ViewModel() {

    private val _uiState = MutableStateFlow(MapUiState())
    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    /**
     * Dipanggil saat pengguna mengetuk titik tertentu pada peta OSM.
     */
    fun onMapPointSelected(latitude: Double, longitude: Double) {
        val validation = validateCoordinatesUseCase.execute(latitude, longitude)
        if (validation is CoordinateValidationResult.Valid) {
            _uiState.update { current ->
                current.copy(
                    selectedLocation = GeoPointData(latitude, longitude),
                    latitudeInput = String.format(Locale.US, "%.6f", latitude),
                    longitudeInput = String.format(Locale.US, "%.6f", longitude),
                    validationResult = validation,
                    errorMessage = null
                )
            }
        } else if (validation is CoordinateValidationResult.Invalid) {
            _uiState.update { current ->
                current.copy(
                    validationResult = validation,
                    errorMessage = validation.reason
                )
            }
        }
    }

    /**
     * Dipanggil saat nilai teks latitude diubah secara manual oleh pengguna.
     */
    fun onLatitudeInputChanged(newLatText: String) {
        _uiState.update { current ->
            current.copy(
                latitudeInput = newLatText,
                validationResult = null,
                errorMessage = null
            )
        }
    }

    /**
     * Dipanggil saat nilai teks longitude diubah secara manual oleh pengguna.
     */
    fun onLongitudeInputChanged(newLonText: String) {
        _uiState.update { current ->
            current.copy(
                longitudeInput = newLonText,
                validationResult = null,
                errorMessage = null
            )
        }
    }

    /**
     * Memvalidasi dan menerapkan input koordinat manual ke marker peta.
     */
    fun applyManualCoordinates() {
        val latText = _uiState.value.latitudeInput
        val lonText = _uiState.value.longitudeInput

        val validation = validateCoordinatesUseCase.execute(latText, lonText)
        when (validation) {
            is CoordinateValidationResult.Valid -> {
                val parsedLat = latText.trim().toDouble()
                val parsedLon = lonText.trim().toDouble()
                _uiState.update { current ->
                    current.copy(
                        selectedLocation = GeoPointData(parsedLat, parsedLon),
                        latitudeInput = String.format(Locale.US, "%.6f", parsedLat),
                        longitudeInput = String.format(Locale.US, "%.6f", parsedLon),
                        validationResult = validation,
                        errorMessage = null,
                        userNotification = "Koordinat manual berhasil diterapkan ke peta."
                    )
                }
            }
            is CoordinateValidationResult.Invalid -> {
                _uiState.update { current ->
                    current.copy(
                        validationResult = validation,
                        errorMessage = validation.reason
                    )
                }
            }
        }
    }

    /**
     * Mengatur ulang titik koordinat terpilih ke lokasi awal default.
     */
    fun resetToDefaultLocation() {
        val defaultPoint = GeoPointData.DEFAULT
        _uiState.update { current ->
            current.copy(
                selectedLocation = defaultPoint,
                latitudeInput = String.format(Locale.US, "%.6f", defaultPoint.latitude),
                longitudeInput = String.format(Locale.US, "%.6f", defaultPoint.longitude),
                validationResult = CoordinateValidationResult.Valid,
                errorMessage = null,
                userNotification = "Pilihan lokasi telah diatur ulang ke titik default."
            )
        }
    }

    /**
     * Dipanggil saat koordinat disalin ke clipboard pengguna.
     */
    fun notifyCoordinatesCopied() {
        _uiState.update { current ->
            current.copy(
                userNotification = "Koordinat (${current.selectedLocation.toFormattedCoordinates()}) disalin ke clipboard."
            )
        }
    }

    /**
     * Membersihkan pesan notifikasi / snackbar.
     */
    fun clearNotification() {
        _uiState.update { current ->
            current.copy(userNotification = null)
        }
    }

    /**
     * Menangani kondisi kegagalan jaringan peta (offline / tile error).
     */
    fun onNetworkStatusChanged(isUnavailable: Boolean) {
        _uiState.update { current ->
            current.copy(
                isNetworkUnavailable = isUnavailable,
                errorMessage = if (isUnavailable) {
                    "Jaringan peta offline. Anda tetap dapat memilih koordinat via input manual."
                } else null
            )
        }
    }
}
