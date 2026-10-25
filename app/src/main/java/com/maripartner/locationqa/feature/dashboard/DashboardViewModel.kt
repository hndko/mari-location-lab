package com.maripartner.locationqa.feature.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.maripartner.locationqa.data.repository.ScenarioRepositoryImpl
import com.maripartner.locationqa.domain.repository.ScenarioRepository
import com.maripartner.locationqa.domain.usecase.CheckMockLocationConfigUseCase
import com.maripartner.locationqa.platform.mocklocation.MockLocationChecker
import com.maripartner.locationqa.platform.permissions.PermissionHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardViewModel @JvmOverloads constructor(
    application: Application,
    private val checkMockLocationConfigUseCase: CheckMockLocationConfigUseCase = CheckMockLocationConfigUseCase(),
    private val scenarioRepository: ScenarioRepository = ScenarioRepositoryImpl(),
    private val mockLocationChecker: MockLocationChecker = MockLocationChecker(application)
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(DashboardUiState(isLoading = true))
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        refreshStatus()
    }

    fun refreshStatus() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val context = getApplication<Application>()
                val isDevEnabled = mockLocationChecker.isDeveloperOptionsEnabled()
                val isMockAppSet = mockLocationChecker.isMockLocationAppSet()
                val hasFineLocation = PermissionHelper.hasFineLocationPermission(context)
                val hasCoarseLocation = PermissionHelper.hasCoarseLocationPermission(context)

                val configStatus = checkMockLocationConfigUseCase.execute(
                    isDeveloperOptionsActive = isDevEnabled,
                    isMockAppSelected = isMockAppSet,
                    hasFineLocation = hasFineLocation,
                    hasCoarseLocation = hasCoarseLocation,
                    isSessionRunning = false,
                    activeScenario = null
                )

                val scenarioCount = scenarioRepository.getScenarioCount()

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        configStatus = configStatus,
                        totalScenariosCount = scenarioCount,
                        totalRunsCount = 0,
                        errorMessage = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Gagal memuat status konfigurasi: ${e.localizedMessage ?: "Terjadi kesalahan"}"
                    )
                }
            }
        }
    }
}
