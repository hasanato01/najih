package com.najih.android.ui.uitilis


import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.najih.android.R
import com.najih.android.util.GlobalFunctions
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController ,context: Context) {
    LaunchedEffect(Unit) {
        delay(500L)
        try {
            if (GlobalFunctions.isFirstTimeUser(context)) {
                navController.navigate("LanguageSelection") {
                    popUpTo("splash_screen") { inclusive = true }

                }
            } else {
                navController.navigate("Home_page") {
                    popUpTo("splash_screen") { inclusive = true }
                }
            }
        } catch (e: Exception) {
            Log.e("SplashScreenError", "Navigation error: ${e.message}", e)
        }
    }
Box(modifier = Modifier
    .fillMaxSize()
    .background(Color.White))
{
    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = null,
        modifier = Modifier
            .size(150.dp)
            .align(Alignment.Center),
        contentScale = ContentScale.Fit

    )
}
}

//@Preview
//@Composable
//fun SplashScreenPreview() {
//    SplashScreen()
//}