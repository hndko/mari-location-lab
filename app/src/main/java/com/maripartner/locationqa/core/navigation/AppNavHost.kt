package com.maripartner.locationqa.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.maripartner.locationqa.feature.dashboard.DashboardScreen
import com.maripartner.locationqa.feature.history.HistoryScreen
import com.maripartner.locationqa.feature.map.MapScreen
import com.maripartner.locationqa.feature.scenarios.ScenariosScreen
import com.maripartner.locationqa.feature.settings.SettingsScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route,
        modifier = modifier
    ) {
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onNavigate = { route ->
                    navController.navigate(route) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(Screen.Map.route) {
            MapScreen()
        }
        composable(Screen.Scenarios.route) {
            ScenariosScreen()
        }
        composable(Screen.History.route) {
            HistoryScreen()
        }
        composable(Screen.Settings.route) {
            SettingsScreen()
        }
    }
}
