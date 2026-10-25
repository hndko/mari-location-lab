package com.maripartner.locationqa.feature.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DeveloperMode
import androidx.compose.material.icons.filled.GpsFixed
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.maripartner.locationqa.core.designsystem.StatusError
import com.maripartner.locationqa.core.designsystem.StatusInfo
import com.maripartner.locationqa.core.designsystem.StatusSuccess
import com.maripartner.locationqa.core.designsystem.StatusWarning
import com.maripartner.locationqa.core.designsystem.components.AppCard
import com.maripartner.locationqa.core.designsystem.components.ErrorStateView
import com.maripartner.locationqa.core.designsystem.components.LoadingView
import com.maripartner.locationqa.core.navigation.Screen
import com.maripartner.locationqa.domain.model.VerificationState

@Composable
fun DashboardScreen(
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DashboardViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    when {
        uiState.isLoading -> {
            LoadingView(message = "Memeriksa status konfigurasi pengujian...")
        }
        uiState.errorMessage != null -> {
            ErrorStateView(
                message = uiState.errorMessage ?: "Terjadi kesalahan",
                onRetryClick = { viewModel.refreshStatus() }
            )
        }
        else -> {
            DashboardContent(
                uiState = uiState,
                onRefresh = { viewModel.refreshStatus() },
                onNavigate = onNavigate,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun DashboardContent(
    uiState: DashboardUiState,
    onRefresh: () -> Unit,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        // Banner Identitas Aplikasi & Standar Transparansi QA
        AppCard(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            borderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
            elevation = 0.dp
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.GpsFixed,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(28.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Mari Location Lab",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "Lingkungan Pengujian Lokasi Terkendali (QA Only)",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.15f))
            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Security,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Pengujian resmi Android Mock Location. Tanpa modifikasi root atau bypass.",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Bagian Status Konfigurasi Pengujian
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Status Konfigurasi Perangkat",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            IconButton(onClick = onRefresh) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Perbarui Status",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        AppCard {
            StatusItemRow(
                icon = Icons.Default.DeveloperMode,
                title = "Opsi Pengembang",
                state = uiState.configStatus.developerOptionsState
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 10.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            )
            StatusItemRow(
                icon = Icons.Default.GpsFixed,
                title = "Aplikasi Lokasi Tiruan (Mock App)",
                state = uiState.configStatus.mockLocationAppState
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 10.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            )
            StatusItemRow(
                icon = Icons.Default.Security,
                title = "Izin Lokasi (Fine / Coarse)",
                state = if (uiState.configStatus.hasFineLocationPermission) {
                    VerificationState.VERIFIED_ACTIVE
                } else {
                    VerificationState.NEEDS_VERIFICATION
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Box Pesan Status Transparan
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                    .padding(12.dp)
            ) {
                Row(verticalAlignment = Alignment.Top) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.HelpOutline,
                        contentDescription = null,
                        modifier = Modifier
                            .size(18.dp)
                            .padding(top = 2.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = uiState.configStatus.statusMessage,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Ringkasan Statistik
        Text(
            text = "Ringkasan Pengujian",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatCard(
                title = "Skenario Tersimpan",
                value = uiState.totalScenariosCount.toString(),
                subtitle = "Tersedia untuk pengujian",
                modifier = Modifier.weight(1f)
            )
            StatCard(
                title = "Sesi Riwayat",
                value = uiState.totalRunsCount.toString(),
                subtitle = "Sesi tercatat",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Navigasi Cepat (Quick Actions)
        Text(
            text = "Akses Fitur",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        QuickActionCard(
            title = "Peta & Titik Lokasi",
            description = "Pilih lokasi pengujian via OpenStreetMap dan validasi koordinat lat/long.",
            icon = Icons.Default.Map,
            onClick = { onNavigate(Screen.Map.route) }
        )

        Spacer(modifier = Modifier.height(10.dp))

        QuickActionCard(
            title = "Skenario Pengujian",
            description = "Kelola kumpulan skenario teruji untuk reproduksi bug berbasis lokasi.",
            icon = Icons.AutoMirrored.Filled.ListAlt,
            onClick = { onNavigate(Screen.Scenarios.route) }
        )

        Spacer(modifier = Modifier.height(10.dp))

        QuickActionCard(
            title = "Riwayat Pengujian",
            description = "Tinjau riwayat waktu mulai, durasi, dan catatan kegagalan sesi simulasi.",
            icon = Icons.Default.History,
            onClick = { onNavigate(Screen.History.route) }
        )

        Spacer(modifier = Modifier.height(10.dp))

        QuickActionCard(
            title = "Panduan & Pengaturan",
            description = "Instruksi konfigurasi Opsi Pengembang dan prinsip privasi pengujian.",
            icon = Icons.Default.Settings,
            onClick = { onNavigate(Screen.Settings.route) }
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun StatusItemRow(
    icon: ImageVector,
    title: String,
    state: VerificationState,
    modifier: Modifier = Modifier
) {
    val (badgeBg, badgeText, badgeColor) = when (state) {
        VerificationState.VERIFIED_ACTIVE -> Triple(
            StatusSuccess.copy(alpha = 0.15f),
            state.label,
            StatusSuccess
        )
        VerificationState.NEEDS_VERIFICATION -> Triple(
            StatusWarning.copy(alpha = 0.15f),
            state.label,
            StatusWarning
        )
        VerificationState.NOT_CONFIGURED -> Triple(
            StatusError.copy(alpha = 0.12f),
            state.label,
            StatusError
        )
        VerificationState.UNAVAILABLE -> Triple(
            MaterialTheme.colorScheme.surfaceVariant,
            state.label,
            MaterialTheme.colorScheme.onSurfaceVariant
        )
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(badgeBg)
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                text = badgeText,
                style = MaterialTheme.typography.labelSmall,
                color = badgeColor,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun StatCard(
    title: String,
    value: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    AppCard(modifier = modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = subtitle,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
        )
    }
}

@Composable
private fun QuickActionCard(
    title: String,
    description: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AppCard(
        modifier = modifier,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                modifier = Modifier.size(18.dp)
            )
        }
    }
}
