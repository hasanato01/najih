package com.najih.android.ui.uitilis

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.najih.android.api.CreateHttpClient
import com.najih.android.api.contactUsForm.postContactForm
import io.ktor.client.engine.android.Android
import kotlinx.coroutines.launch

@Composable
fun ContactUsForm (navController: NavController) {
    val httpClient = CreateHttpClient(Android)
    val coroutineScope = rememberCoroutineScope()
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    val isFormValid =
        name.isNotEmpty() && email.isNotEmpty() && phoneNumber.isNotEmpty() && country.isNotEmpty() && message.isNotEmpty()


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { navbar(navController) },
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(color = Color(0xfff9f9f9))
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(innerPadding)
        ) {
            navbar(navController)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Get in touch", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(8.dp))

            // Name Input
            StyledTextField(
                value = name,
                onValueChange = { name = it },
                label = "Name",
                icon = {
                    Icon(
                        Icons.Filled.Person,
                        contentDescription = "Person Icon"
                    )
                } // Optional icon
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Email Input
            StyledTextField(
                value = email,
                onValueChange = { email = it },
                label = "Email",
                icon = { Icon(Icons.Filled.Email, contentDescription = "Email Icon") }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Phone Number Input
            StyledTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = "Phone Number",
                icon = { Icon(Icons.Filled.Phone, contentDescription = "Phone Icon") }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Country Input
            StyledTextField(
                value = country,
                onValueChange = { country = it },
                label = "Country",
                icon = {
                    Icon(
                        Icons.Filled.LocationOn,
                        contentDescription = "Location Icon"
                    )
                } // Country Icon
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Message Input
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .verticalScroll(rememberScrollState()) // Enables scrolling
            ) {
                StyledTextField(
                    value = message,
                    onValueChange = { message = it },
                    label = "Message",
                    modifier = Modifier
                        .fillMaxSize()
                        .height(200.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            // Send Button
            Button(
                onClick = {
                    coroutineScope.launch {
                        try {
                            val status = postContactForm(
                                httpClient,
                                name,
                                email,
                                phoneNumber,
                                country,
                                message
                            )
                            Log.d("ApiClient", " $status")
                        } catch (e: Exception) {
                            Log.e(
                                "ApiClient",
                                "there was error in sending the contact form data ${e.message}"
                            )
                        }
                    }
                },
                enabled = isFormValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                Text("Send")
            }
        }
    }
}

@Preview
@Composable
fun  ContactUs () {
    val navController = rememberNavController()
    ContactUsForm(navController)
}