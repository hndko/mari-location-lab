package com.maripartner.locationqa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.maripartner.locationqa.core.designsystem.LocationQaTheme
import com.maripartner.locationqa.core.designsystem.components.AppTopBar
import com.maripartner.locationqa.core.navigation.AppBottomBar
import com.maripartner.locationqa.core.navigation.AppNavHost
import com.maripartner.locationqa.core.navigation.Screen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LocationQaTheme {
                MainAppScaffold()
            }
        }
    }
}

@Composable
fun MainAppScaffold() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val currentScreenTitle = when (currentRoute) {
        Screen.Dashboard.route -> "Mari Location Lab"
        Screen.Map.route -> "Peta & Koordinat"
        Screen.Scenarios.route -> "Skenario Pengujian"
        Screen.History.route -> "Riwayat Pengujian"
        Screen.Settings.route -> "Panduan & Pengaturan"
        else -> "Mari Location Lab"
    }

    val currentScreenSubtitle = when (currentRoute) {
        Screen.Dashboard.route -> "Simulator QA Resmi"
        Screen.Map.route -> "Validasi Lokasi Geografis"
        Screen.Scenarios.route -> "Titik Uji Terstandarisasi"
        Screen.History.route -> "Log Hasil & Durasi"
        Screen.Settings.route -> "Konfigurasi Developer"
        else -> null
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                title = currentScreenTitle,
                subtitle = currentScreenSubtitle
            )
        },
        bottomBar = {
            AppBottomBar(
                currentRoute = currentRoute,
                onNavigateToRoute = { route ->
                    navController.navigate(route) {
                        popUpTo(Screen.Dashboard.route) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { innerPadding ->
        AppNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}
