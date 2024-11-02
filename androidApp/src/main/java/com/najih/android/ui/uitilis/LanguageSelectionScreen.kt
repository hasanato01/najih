package com.najih.android.ui.uitilis

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.najih.android.MainActivity
import com.najih.android.R
import com.najih.android.util.GlobalFunctions

@Composable
fun LanguageSelectionScreen(navController: NavController) {

    Scaffold(
        topBar = {
            Navbar(navController, backText = "App language", titleText = "Select Language")
        },
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FFFE))
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .background(Color(0xFFF9FFFE))
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(16.dp))
            LanguageOption(navController, languageName = "en", flagResourceId = R.drawable.uk)
            Spacer(modifier = Modifier.height(16.dp))
            LanguageOption(navController, languageName = "ar", flagResourceId = R.drawable.egypt)
        }
    }
}

@Composable
fun LanguageOption(navController: NavController,languageName: String, flagResourceId: Int) {
    val context = LocalContext.current
    val activity = context as MainActivity
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(100.dp))
            .height(50.dp)
            .background(Color.White)
            .border(1.dp, Color(0x2B000000), RoundedCornerShape(100.dp))
            .padding(10.dp)
            .clickable {
                GlobalFunctions.saveUserLanguage(context, languageName)
                activity.updateLocale(languageName)
                GlobalFunctions.setFirstTimeUser(context, false)
                navController.navigate("Home_page") {
                    popUpTo("LanguageSelection") { inclusive = true }
                }
            },
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            painter = painterResource(id = flagResourceId),
            contentDescription = "$languageName flag",
            modifier = Modifier.size(51.dp)
        )

        Spacer(modifier = Modifier.width(44.dp))

        Text(
            text = when (languageName) {
                "ar" -> "Arabic"
                else -> "English"
            },
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

