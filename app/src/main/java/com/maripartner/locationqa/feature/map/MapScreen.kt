package com.maripartner.locationqa.feature.map

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.maripartner.locationqa.core.designsystem.StatusError
import com.maripartner.locationqa.core.designsystem.StatusSuccess
import com.maripartner.locationqa.core.designsystem.components.AppCard
import com.maripartner.locationqa.domain.model.CoordinateValidationResult
import com.maripartner.locationqa.domain.usecase.ValidateCoordinatesUseCase

@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    validateCoordinatesUseCase: ValidateCoordinatesUseCase = remember { ValidateCoordinatesUseCase() }
) {
    var latInput by remember { mutableStateOf("-6.2088") }
    var lonInput by remember { mutableStateOf("106.8456") }
    var validationResult by remember { mutableStateOf<CoordinateValidationResult?>(null) }

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        // Banner Fitur Peta & Pemberitahuan Tahap 2
        AppCard(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            borderColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f),
            elevation = 0.dp
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Map,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Modul Peta OpenStreetMap",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Text(
                        text = "Integrasi tile map & marker interaktif dijadwalkan pada Tahap 2.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.15f))
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Atribusi: Data peta © Kontributor OpenStreetMap (ODbL).",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Form Pengujian Validasi Koordinat Manual
        Text(
            text = "Validasi Koordinat Manual",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Uji batasan latitude (-90 s/d 90) dan longitude (-180 s/d 180) sesuai spesifikasi PRD.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(12.dp))

        AppCard {
            OutlinedTextField(
                value = latInput,
                onValueChange = {
                    latInput = it
                    validationResult = null
                },
                label = { Text("Latitude (-90.0 s/d 90.0)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = lonInput,
                onValueChange = {
                    lonInput = it
                    validationResult = null
                },
                label = { Text("Longitude (-180.0 s/d 180.0)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    validationResult = validateCoordinatesUseCase.execute(latInput, lonInput)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.MyLocation,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Validasi Nilai Koordinat")
            }

            // Tampilan Hasil Validasi
            validationResult?.let { result ->
                Spacer(modifier = Modifier.height(16.dp))
                when (result) {
                    is CoordinateValidationResult.Valid -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(StatusSuccess.copy(alpha = 0.12f))
                                .padding(12.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = StatusSuccess,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Koordinat valid ($latInput, $lonInput). Memenuhi aturan PRD AC-02.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = StatusSuccess,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                    is CoordinateValidationResult.Invalid -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(StatusError.copy(alpha = 0.12f))
                                .padding(12.dp)
                        ) {
                            Row(verticalAlignment = Alignment.Top) {
                                Icon(
                                    imageVector = Icons.Default.ErrorOutline,
                                    contentDescription = null,
                                    tint = StatusError,
                                    modifier = Modifier
                                        .size(20.dp)
                                        .padding(top = 2.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = result.reason,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = StatusError,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Informasi Tambahan Peta
        AppCard {
            Row(verticalAlignment = Alignment.Top) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "Kebijakan Tile & Akses Offline",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Sesuai PRD bagian 4.1 dan AGENTS.md, aplikasi tidak menjanjikan peta offline sampai penyimpanan cache resmi diimplementasikan. Error jaringan dan kegagalan tile akan ditangani secara anggun pada Tahap 2.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
