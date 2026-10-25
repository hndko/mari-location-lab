package com.maripartner.locationqa.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material.icons.automirrored.outlined.ListAlt
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Rute dan metadata navigasi layar utama aplikasi Mari Location Lab.
 */
sealed class Screen(
    val route: String,
    val title: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector
) {
    data object Dashboard : Screen(
        route = "dashboard",
        title = "Dashboard",
        unselectedIcon = Icons.Outlined.Dashboard,
        selectedIcon = Icons.Filled.Dashboard
    )

    data object Map : Screen(
        route = "map",
        title = "Peta",
        unselectedIcon = Icons.Outlined.Map,
        selectedIcon = Icons.Filled.Map
    )

    data object Scenarios : Screen(
        route = "scenarios",
        title = "Skenario",
        unselectedIcon = Icons.AutoMirrored.Outlined.ListAlt,
        selectedIcon = Icons.AutoMirrored.Filled.ListAlt
    )

    data object History : Screen(
        route = "history",
        title = "Riwayat",
        unselectedIcon = Icons.Outlined.History,
        selectedIcon = Icons.Filled.History
    )

    data object Settings : Screen(
        route = "settings",
        title = "Pengaturan",
        unselectedIcon = Icons.Outlined.Settings,
        selectedIcon = Icons.Filled.Settings
    )

    companion object {
        val bottomNavItems: List<Screen>
            get() = listOf(
                Dashboard,
                Map,
                Scenarios,
                History,
                Settings
            )
    }
}
