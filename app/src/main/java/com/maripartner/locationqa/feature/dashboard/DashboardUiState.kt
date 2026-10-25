package com.maripartner.locationqa.feature.dashboard

import com.maripartner.locationqa.domain.model.MockLocationConfigStatus

/**
 * State UI untuk layar Dashboard utama Mari Location Lab.
 */
data class DashboardUiState(
    val isLoading: Boolean = false,
    val configStatus: MockLocationConfigStatus = MockLocationConfigStatus(),
    val totalScenariosCount: Int = 0,
    val totalRunsCount: Int = 0,
    val errorMessage: String? = null
)
