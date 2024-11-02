package com.najih.android.util

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.najih.android.R
// Create a FontFamily for English font
val englishFontFamily = FontFamily(
    Font(R.font.quicksand_medium) // Replace with your English font resource
)

// Create custom Typography using the English font
val EnglishTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = englishFontFamily,
        fontSize = 30.sp,
        lineHeight = 36.sp
    ),
    displayMedium = TextStyle(
        fontFamily = englishFontFamily,
        fontSize = 24.sp,
        lineHeight = 30.sp
    ),
    displaySmall = TextStyle(
        fontFamily = englishFontFamily,
        fontSize = 20.sp,
        lineHeight = 26.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = englishFontFamily,
        fontSize = 18.sp,
        lineHeight = 24.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = englishFontFamily,
        fontSize = 16.sp,
        lineHeight = 22.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = englishFontFamily,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = englishFontFamily,
        fontSize = 16.sp,
        lineHeight = 22.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = englishFontFamily,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    bodySmall = TextStyle(
        fontFamily = englishFontFamily,
        fontSize = 12.sp,
        lineHeight = 18.sp
    ),
    labelLarge = TextStyle(
        fontFamily = englishFontFamily,
        fontSize = 16.sp,
        lineHeight = 22.sp
    ),
    labelMedium = TextStyle(
        fontFamily = englishFontFamily,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    labelSmall = TextStyle(
        fontFamily = englishFontFamily,
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),
    titleLarge = TextStyle(
        fontFamily = englishFontFamily,
        fontSize = 22.sp,
        lineHeight = 28.sp
    ),
    titleMedium = TextStyle(
        fontFamily = englishFontFamily,
        fontSize = 18.sp,
        lineHeight = 24.sp
    ),
    titleSmall = TextStyle(
        fontFamily = englishFontFamily,
        fontSize = 16.sp,
        lineHeight = 22.sp
    ),
)
