package com.najih.android.ui.uitilis

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.core.view.WindowCompat
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.core.view.WindowInsetsControllerCompat
import com.najih.android.R

@Composable
fun Navbar(navController: NavController, backText: String, titleText: String) {
 // Get the current context and window
 val context = LocalContext.current
 val window = (context as Activity).window
 // Set up status bar and allow drawing under it

 SideEffect {
  // Set status bar color
  WindowCompat.setDecorFitsSystemWindows(window, false) // Allow content to extend behind the status bar
  WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false // Set light/dark status bar
  window.statusBarColor = Color(0xFF00004B).toArgb() // Set status bar color
 }

 Box(
  modifier = Modifier
   .fillMaxWidth()
   .background(Color(0xFF00004B))
   .padding(horizontal = 16.dp)
   .statusBarsPadding()
 ) {
  Column(
   modifier = Modifier
    .fillMaxWidth()
    .height(150.dp),
   verticalArrangement = Arrangement.Center,
   horizontalAlignment = Alignment.Start
  ) {
   // Back Icon
   IconButton(onClick = { navController.navigateUp() }) {
    Icon(
     painter = painterResource(id = R.drawable.back_button),
     contentDescription = "Back",
     tint = Color.White
    )
   }
   // Texts for back text and title
   Column(modifier = Modifier.padding(start = 8.dp)) {
    Text(
     text = backText,
     color = Color.LightGray,
     fontSize = 16.sp,
     fontWeight = FontWeight.Bold
    )
    Text(
     text = titleText,
     color = Color.White,
     fontSize = 30.sp,
     fontWeight = FontWeight.Bold
    )
   }
  }
 }
}

