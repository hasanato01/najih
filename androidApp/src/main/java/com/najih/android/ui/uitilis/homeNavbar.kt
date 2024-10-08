package com.najih.android.ui.uitilis

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.najih.android.R
import com.najih.android.util.GlobalFunctions


@Composable
fun HomeNavbar(navController: NavController) {

    val context = LocalContext.current
    val window = (context as Activity).window
    var userInfo by remember { mutableStateOf(GlobalFunctions.getUserInfo(context)) }
    val isLoggedIn = userInfo.userId.isNotEmpty() && userInfo.token.isNotEmpty()
    val greetingText = userInfo.userName.ifEmpty { "Welcome to Najih" }

    // Side effect to handle window insets and status bar color
    SideEffect {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false
        window.statusBarColor = Color(0xFF00004B).toArgb()
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
                .height(120.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {

            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                // Greeting Text (Username or Welcome message)
                Column(modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(0.7f)) {
                    Text(

                        text = greetingText,
                        color = Color.LightGray,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Explore our Categories",
                        color = Color.White,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        style = androidx.compose.ui.text.TextStyle(lineHeight = 36.sp)
                    )
                }

                // Icon Button for Log Out or Sign In
                IconButton(modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(0.3f),
                    onClick = {
                        if (isLoggedIn) {
                            GlobalFunctions.clearUserInfo(context)
                            userInfo = GlobalFunctions.getUserInfo(context)
                        } else {
                            navController.navigate("sign_in")
                        }
                    }
                ) {
                    Icon(
                        painter = if (isLoggedIn) painterResource(id = R.drawable.logout) else painterResource(
                            id = R.drawable.user
                        ),
                        contentDescription = if (isLoggedIn) "Log Out" else "Sign In",
                        modifier = Modifier.size(40.dp),
                        tint = Color.Unspecified
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun HomePre(){
    val navController = rememberNavController()
    HomeNavbar(navController )
}