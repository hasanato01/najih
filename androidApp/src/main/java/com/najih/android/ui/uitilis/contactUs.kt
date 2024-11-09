package com.najih.android.ui.uitilis

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.najih.android.R
import com.najih.android.api.CreateHttpClient
import com.najih.android.api.contactUsForm.postContactForm
import io.ktor.client.engine.android.Android
import kotlinx.coroutines.launch

@Composable
fun ContactUsForm(navController: NavController) {
    val httpClient = CreateHttpClient(Android)
    val coroutineScope = rememberCoroutineScope()
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var isDialogVisible by remember { mutableStateOf(false) } // To control the success dialog visibility
    val isFormValid =
        name.isNotEmpty() && email.isNotEmpty() && phoneNumber.isNotEmpty() && country.isNotEmpty() && message.isNotEmpty()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { Navbar(navController, backText = stringResource(R.string.contact_us), titleText = stringResource(R.string.Get_in_touch)) },
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(color = Color(0xfff9f9f9))
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Name Input
            StyledTextField(
                value = name,
                onValueChange = { name = it },
                label = "Name",
                icon = painterResource(id = R.drawable.user_blue)
            )
            Spacer(modifier = Modifier.height(8.dp))
            StyledTextField(
                value = email,
                onValueChange = { email = it },
                label = "Email",
                icon = painterResource(id = R.drawable.email_blue)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Phone Number Input
            StyledTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = "Phone Number",
                icon = painterResource(id = R.drawable.phone_blue)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Country Input
            StyledTextField(
                value = country,
                onValueChange = { country = it },
                label = "Country",
                icon = painterResource(id = R.drawable.location_blue)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                StyledTextField(
                    value = message,
                    onValueChange = { message = it },
                    label = "Message",
                    modifier = Modifier
                        .fillMaxSize()
                        .height(100.dp)
                )
            }





            Spacer(modifier = Modifier.height(16.dp))

            // Send Button
            Button(
                onClick = {
                    coroutineScope.launch {
                        try {
                            val response = postContactForm(
                                httpClient,
                                name,
                                email,
                                phoneNumber,
                                country,
                                message
                            )
                            Log.d("ApiClient", " $response")

                            if (response.status == "success") { // Assuming 200 is success
                                isDialogVisible = true // Show success dialog
                            } else {
                                // Optionally show error if the status is not 200
                                Log.e("ApiClient", "Failed to send the message.")
                            }
                        } catch (e: Exception) {
                            Log.e("ApiClient", "Error sending the contact form: ${e.message}")
                        }
                    }
                },
                enabled = isFormValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0096c7),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Send",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            // Social Media Icons Row
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .horizontalScroll(rememberScrollState()) // Horizontal scrolling
            ) {
                SocialMediaIcon(iconResId = R.drawable.whatsapp, url = "https://wa.me/yourwhatsappnumber")
                SocialMediaIcon(iconResId = R.drawable.facebook, url = "https://www.facebook.com/yourpage")
                SocialMediaIcon(iconResId = R.drawable.instagram, url = "https://www.instagram.com/yourprofile")
                SocialMediaIcon(iconResId = R.drawable.threads, url = "https://www.threads.net/yourprofile")
                SocialMediaIcon(iconResId = R.drawable.twitter, url = "https://twitter.com/yourprofile")
                SocialMediaIcon(iconResId = R.drawable.youtube, url = "https://www.youtube.com/c/yourchannel")
                SocialMediaIcon(iconResId = R.drawable.tiktok, url = "https://www.tiktok.com/@yourprofile")
            }
        }
    }

    // Success Dialog
    if (isDialogVisible) {
        SuccessDialog(
            onDismiss = { isDialogVisible = false } // Close dialog when dismissed
        )
    }
}

@Composable
fun SuccessDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center)
            ) {
                // Custom Success Icon
                Icon(
                    painter = painterResource(id = R.drawable.success_checkmark), // Custom icon resource
                    contentDescription = "Success",
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.CenterHorizontally),
                    tint = Color.Unspecified
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Success Message Text
                Text(
                    text = "Your message has been sent successfully ",
                    fontSize = 16.sp,
                    color = Color(0xFF333333),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Close Button
                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0096c7), // Blue background for the button
                        contentColor = Color.White // White text
                    )
                ) {
                    Text(
                        text = "Close",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}


@Composable
fun SocialMediaIcon(iconResId: Int, url: String) {
    val context = LocalContext.current
    IconButton(onClick = {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent) // Open the URL in browser or respective app
    }) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = "Social Media Icon",
            modifier = Modifier.size(32.dp),
            tint = Color.Unspecified
        )
    }
}