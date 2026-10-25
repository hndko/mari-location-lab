package com.maripartner.locationqa.core.designsystem

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// Primary Colors - Deep Tech Blue
val PrimaryLight = Color(0xFF0D47A1)
val OnPrimaryLight = Color(0xFFFFFFFF)
val PrimaryContainerLight = Color(0xFFD6E4FF)
val OnPrimaryContainerLight = Color(0xFF001A43)

val PrimaryDark = Color(0xFF90CAF9)
val OnPrimaryDark = Color(0xFF003062)
val PrimaryContainerDark = Color(0xFF00448A)
val OnPrimaryContainerDark = Color(0xFFD6E4FF)

// Secondary Colors - Precision Teal
val SecondaryLight = Color(0xFF006874)
val OnSecondaryLight = Color(0xFFFFFFFF)
val SecondaryContainerLight = Color(0xFF97F0FF)
val OnSecondaryContainerLight = Color(0xFF001F24)

val SecondaryDark = Color(0xFF4FD8EB)
val OnSecondaryDark = Color(0xFF00363D)
val SecondaryContainerDark = Color(0xFF004E58)
val OnSecondaryContainerDark = Color(0xFF97F0FF)

// Tertiary Colors - Indigo
val TertiaryLight = Color(0xFF3949AB)
val OnTertiaryLight = Color(0xFFFFFFFF)
val TertiaryContainerLight = Color(0xFFDEE0FF)
val OnTertiaryContainerLight = Color(0xFF00105C)

val TertiaryDark = Color(0xFFB9C3FF)
val OnTertiaryDark = Color(0xFF1E2878)
val TertiaryContainerDark = Color(0xFF273891)
val OnTertiaryContainerDark = Color(0xFFDEE0FF)

// Neutral Colors - Slate tones
val BackgroundLight = Color(0xFFF8FAFC)
val OnBackgroundLight = Color(0xFF0F172A)
val SurfaceLight = Color(0xFFFFFFFF)
val OnSurfaceLight = Color(0xFF0F172A)
val SurfaceVariantLight = Color(0xFFE2E8F0)
val OnSurfaceVariantLight = Color(0xFF475569)

val BackgroundDark = Color(0xFF090D16)
val OnBackgroundDark = Color(0xFFF1F5F9)
val SurfaceDark = Color(0xFF131B2E)
val OnSurfaceDark = Color(0xFFE2E8F0)
val SurfaceVariantDark = Color(0xFF1E293B)
val OnSurfaceVariantDark = Color(0xFF94A3B8)

// Status & QA Indicators
val StatusSuccess = Color(0xFF16A34A)
val StatusWarning = Color(0xFFD97706)
val StatusError = Color(0xFFDC2626)
val StatusInfo = Color(0xFF2563EB)

val LightColorScheme = lightColorScheme(
    primary = PrimaryLight,
    onPrimary = OnPrimaryLight,
    primaryContainer = PrimaryContainerLight,
    onPrimaryContainer = OnPrimaryContainerLight,
    secondary = SecondaryLight,
    onSecondary = OnSecondaryLight,
    secondaryContainer = SecondaryContainerLight,
    onSecondaryContainer = OnSecondaryContainerLight,
    tertiary = TertiaryLight,
    onTertiary = OnTertiaryLight,
    tertiaryContainer = TertiaryContainerLight,
    onTertiaryContainer = OnTertiaryContainerLight,
    background = BackgroundLight,
    onBackground = OnBackgroundLight,
    surface = SurfaceLight,
    onSurface = OnSurfaceLight,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = OnSurfaceVariantLight,
    error = StatusError
)

val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    onPrimary = OnPrimaryDark,
    primaryContainer = PrimaryContainerDark,
    onPrimaryContainer = OnPrimaryContainerDark,
    secondary = SecondaryDark,
    onSecondary = OnSecondaryDark,
    secondaryContainer = SecondaryContainerDark,
    onSecondaryContainer = OnSecondaryContainerDark,
    tertiary = TertiaryDark,
    onTertiary = OnTertiaryDark,
    tertiaryContainer = TertiaryContainerDark,
    onTertiaryContainer = OnTertiaryContainerDark,
    background = BackgroundDark,
    onBackground = OnBackgroundDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = OnSurfaceVariantDark,
    error = StatusError
)
