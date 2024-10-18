package com.najih.android

import CustomTypography
import MyAppNavHost
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import com.najih.android.util.GlobalFunctions


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        val savedLanguage = GlobalFunctions.getUserLanguage(this) ?: "en" // Default to English if not set
        val localizedContext = GlobalFunctions.setAppLocale(this, savedLanguage)
        setContent {
            MaterialTheme(
                typography = CustomTypography // Use the custom typography
            ) {
                MyAppNavHost(localizedContext) // Your navigation host here
            }
        }
    }
}

