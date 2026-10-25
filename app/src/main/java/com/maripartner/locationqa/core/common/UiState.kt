package com.maripartner.locationqa.core.common

/**
 * Representasi status UI umum untuk seluruh layar aplikasi.
 */
sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>
    data class Success<T>(val data: T) : UiState<T>
    data class Empty(val message: String = "") : UiState<Nothing>
    data class Error(val message: String, val throwable: Throwable? = null) : UiState<Nothing>
}
