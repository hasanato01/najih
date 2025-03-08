package com.najih.android.ui.settings

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.najih.android.MainActivity
import com.najih.android.R
import com.najih.android.ui.uitilis.BottomNavBar
import com.najih.android.ui.uitilis.Navbar
import com.najih.android.ui.uitilis.SettingCard
import com.najih.android.util.GlobalFunctions

@Composable
fun Settings(navController: NavController) {
    val context = LocalContext.current
    val userInfo by remember { mutableStateOf(GlobalFunctions.getUserInfo(context)) }
    val userName = userInfo.userName
    var currentLanguage by remember { mutableStateOf(GlobalFunctions.getUserLanguage(context) ?: "en") }

    // Determine language resources
    val languageIcon = if (currentLanguage == "en") R.drawable.uk else R.drawable.emirates
    val languageText = if (currentLanguage == "en") "English" else "العربية"


    val openUrl: (String) -> Unit = { baseUrl ->
        val url = when (currentLanguage) {
            "ar" -> "https://najih1.com/ar/$baseUrl"
            else -> "https://najih1.com/en/$baseUrl"
        }
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { Navbar(navController, backText = userName, titleText = stringResource(R.string.setting)) },
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(innerPadding),
        ) {
            item {
                SettingCard(
                    title = stringResource(R.string.contact_us),
                    icon = painterResource(id = R.drawable.comments),
                    onClick = { navController.navigate("contact_us") }
                )
            }
            item {
                SettingCard(
                    title = stringResource(R.string.privacy_policy),
                    icon = painterResource(id = R.drawable.location_blue),
                    onClick = { openUrl("privacyPolicy") }
                )
            }
            item {
                SettingCard(
                    title = stringResource(R.string.cookies_policy),
                    icon = painterResource(id = R.drawable.location_blue),
                    onClick = { openUrl("cookiesPolicy") }
                )
            }
            item {
                SettingCard(
                    title = languageText,
                    icon = painterResource(id = languageIcon),
                    onClick = {
                        // Toggle language logic
                        currentLanguage = if (currentLanguage == "en") "ar" else "en"
                        GlobalFunctions.saveUserLanguage(context, currentLanguage)
                        GlobalFunctions.setAppLocale(context, currentLanguage)

                        // Restart to apply the new language
                        val restartIntent = Intent(context, MainActivity::class.java)
                        restartIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        context.startActivity(restartIntent)
                    }
                )
            }
        }
    }
}

