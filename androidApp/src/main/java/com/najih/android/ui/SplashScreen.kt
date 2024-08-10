package com.najih.android.ui

import android.window.SplashScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.najih.android.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(modifier: Modifier = Modifier , onTimeout : () -> Unit = {}) {
    LaunchedEffect(key1 = true) {
        delay(2000L)
        onTimeout()
    }
Box(modifier = Modifier
    .fillMaxSize()
    .background(Color(0xFF3F51B5)))
{
    Image(
        painter = painterResource(id = R.drawable.logo), // Replace with your image resource
        contentDescription = null,
        modifier = Modifier
            .size(150.dp) // Set specific width and height
            .align(Alignment.Center), // Center the image
        contentScale = ContentScale.Fit

    )
}
}

@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen()
}