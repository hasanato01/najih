import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.TextUnit
import com.najih.android.R

// Create a FontFamily from your font resource
val arabicFontFamily = FontFamily(
    Font(R.font.almarai) // Replace with your font resource name
)

// Create custom Typography using the Arabic font
val CustomTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = arabicFontFamily,
        fontSize = 30.sp,
        lineHeight = 36.sp // Set line height
    ),
    displayMedium = TextStyle(
        fontFamily = arabicFontFamily,
        fontSize = 24.sp,
        lineHeight = 30.sp // Set line height
    ),
    displaySmall = TextStyle(
        fontFamily = arabicFontFamily,
        fontSize = 20.sp,
        lineHeight = 26.sp // Set line height
    ),
    headlineLarge = TextStyle(
        fontFamily = arabicFontFamily,
        fontSize = 18.sp,
        lineHeight = 24.sp // Set line height
    ),
    headlineMedium = TextStyle(
        fontFamily = arabicFontFamily,
        fontSize = 16.sp,
        lineHeight = 22.sp // Set line height
    ),
    headlineSmall = TextStyle(
        fontFamily = arabicFontFamily,
        fontSize = 14.sp,
        lineHeight = 20.sp // Set line height
    ),
    bodyLarge = TextStyle(
        fontFamily = arabicFontFamily,
        fontSize = 16.sp,
        lineHeight = 22.sp // Set line height
    ),
    bodyMedium = TextStyle(
        fontFamily = arabicFontFamily,
        fontSize = 14.sp,
        lineHeight = 20.sp // Set line height
    ),
    bodySmall = TextStyle(
        fontFamily = arabicFontFamily,
        fontSize = 12.sp,
        lineHeight = 18.sp // Set line height
    ),
    labelLarge = TextStyle(
        fontFamily = arabicFontFamily,
        fontSize = 16.sp,
        lineHeight = 22.sp // Set line height
    ),
    labelMedium = TextStyle(
        fontFamily = arabicFontFamily,
        fontSize = 14.sp,
        lineHeight = 20.sp // Set line height
    ),
    labelSmall = TextStyle(
        fontFamily = arabicFontFamily,
        fontSize = 12.sp,
        lineHeight = 16.sp // Set line height
    ),
    titleLarge = TextStyle(
        fontFamily = arabicFontFamily,
        fontSize = 22.sp,
        lineHeight = 28.sp // Set line height
    ),
    titleMedium = TextStyle(
        fontFamily = arabicFontFamily,
        fontSize = 18.sp,
        lineHeight = 24.sp // Set line height
    ),
    titleSmall = TextStyle(
        fontFamily = arabicFontFamily,
        fontSize = 16.sp,
        lineHeight = 22.sp // Set line height
    ),

)
