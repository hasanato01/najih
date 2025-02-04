package com.najih.android.ui.profile


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
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
import com.najih.android.R
import com.najih.android.ui.uitilis.BottomNavBar
import com.najih.android.ui.uitilis.Navbar
import com.najih.android.ui.uitilis.SettingCard
import com.najih.android.util.GlobalFunctions


@Composable
fun MyProfile(navController: NavController) {
    val context = LocalContext.current
    var userInfo by remember { mutableStateOf(GlobalFunctions.getUserInfo(context)) }
    val isLoggedIn = userInfo.userId.isNotEmpty() && userInfo.token.isNotEmpty()
    val userName = userInfo.userName

    // State for the dialog
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { Navbar(navController, backText = userName, titleText = stringResource(R.string.my_profile)) },
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(innerPadding)
        ) {
            // My Recorded Lessons
            item {
                SettingCard(
                    title = stringResource(R.string.my_Recorded_lessons),
                    icon = painterResource(id = R.drawable.video_camera), // Replace with your drawable resource
                    onClick = {
                        if (isLoggedIn) {
                            navController.navigate("user_recorded_lessons")
                        } else {
                            // Show the dialog if not logged in
                            showDialog = true
                        }
                    }
                )
            }
            // My Exams Card
            item {
                SettingCard(
                    title = stringResource(R.string.my_strams),
                    icon = painterResource(id = R.drawable.microphone), // Replace with your drawable resource
                    onClick = {
                        if (isLoggedIn) {
                            navController.navigate("user_streams")
                        } else {
                            // Show the dialog if not logged in
                            showDialog = true
                        }
                    }
                )
            }
            // My Exams Card
            item {
                SettingCard(
                    title = stringResource(R.string.my_exams),
                    icon = painterResource(id = R.drawable.exams_blue), // Replace with your drawable resource
                    onClick = {
                        if (isLoggedIn) {
                            navController.navigate("user_exams")
                        } else {
                            // Show the dialog if not logged in
                            showDialog = true
                        }
                    }
                )
            }

            // Log Out or Log In Card
            item {
                SettingCard(
                    title = if (isLoggedIn) stringResource(R.string.log_out) else stringResource(R.string.sign_in),
                    icon = painterResource(id = if (isLoggedIn) R.drawable.logout_red else R.drawable.log_in), // Use appropriate icons
                    onClick = {
                        if (isLoggedIn) {
                            // Clear user info and navigate to home page
                            GlobalFunctions.clearUserInfo(context)
                            userInfo = GlobalFunctions.getUserInfo(context) // Update userInfo state
                            navController.navigate("home_page")
                        } else {
                            // Navigate to login screen
                            navController.navigate("sign_in")
                        }
                    }
                )
            }
        }

        // Dialog for not logged in
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text(text = stringResource(R.string.please_log_in_title)) },
                text = { Text(text = stringResource(R.string.log_in_required_message)) },
                confirmButton = {
                    TextButton(onClick = { showDialog = false ; navController.navigate("sign_in") }) {
                        Text(stringResource(R.string.sign_in))
                    }
                }
            )
        }
    }
}

