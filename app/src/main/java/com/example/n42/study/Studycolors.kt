package com.example.n42.study

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color





// All the colors one screen needs, in one place.
class StudyColors(
    val background: Color,
    val screenTitle: Color,
    val subtitle: Color,
    val divider: Color,
    val card: Color,
    val cardBorder: Color,
    val cardTitle: Color,
    val tile: Color,
    val onTile: Color,
    val tipCard: Color,
    val onTipCard: Color,
    val bodyText: Color
)

// Light theme = the NT palette from the Figma (NT 4 and NT 7)
val LightStudyColors = StudyColors(
    background = Color(0xFFF7F0EA),
    screenTitle = Color(0xFF713B50),
    subtitle = Color(0xFF798698),
    divider = Color(0xFFFFFFFF),
    card = Color(0xFFFFFFFF),
    cardBorder = Color(0xFFB9707A),
    cardTitle = Color(0xFF000000),
    tile = Color(0xFF97404C),
    onTile = Color(0xFFFFFFFF),
    tipCard = Color(0xFFB9707A),
    onTipCard = Color(0xFFFFFFFF),
    bodyText = Color(0xFF3B2E2A)
)

// Dark theme: the Figma has no dark frame, so these are the same rose colors on dark backgrounds.
val DarkStudyColors = StudyColors(
    background = Color(0xFF1E1819),
    screenTitle = Color(0xFFF0C9D0),
    subtitle = Color(0xFFA79AA0),
    divider = Color(0xFF3A2F31),
    card = Color(0xFF2A2224),
    cardBorder = Color(0xFF8A5560),
    cardTitle = Color(0xFFF5EDEE),
    tile = Color(0xFF97404C),
    onTile = Color(0xFFFFFFFF),
    tipCard = Color(0xFF7A4A52),
    onTipCard = Color(0xFFFFFFFF),
    bodyText = Color(0xFFE8DEDF)
)

// Call this in any screen to get the right colors for the phone's current theme.
@Composable
fun studyColors(): StudyColors {
    return if (isSystemInDarkTheme()) DarkStudyColors else LightStudyColors
}
