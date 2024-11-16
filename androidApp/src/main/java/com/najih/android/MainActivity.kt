package com.najih.android

import ArabicTypography
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.najih.android.appNavigation.MyAppNavHost
import com.najih.android.util.EnglishTypography
import com.najih.android.util.GlobalFunctions


class MainActivity : ComponentActivity() {
    private var currentLanguage by mutableStateOf("ar") // State for the current language

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        // Retrieve the saved language or default to English
        currentLanguage = GlobalFunctions.getUserLanguage(this) ?: "ar"
        updateLocale(currentLanguage)

        setContent {
            MaterialTheme(
                typography = when (currentLanguage) {
                    "ar" -> ArabicTypography
                    else -> EnglishTypography
                }
            ) {
                MyAppNavHost()
            }
        }
    }

    // Function to update the locale and the language state
    fun updateLocale(language: String) {
        currentLanguage = language
        GlobalFunctions.setAppLocale(this, language) // Update the locale in your GlobalFunctions
    }
}
