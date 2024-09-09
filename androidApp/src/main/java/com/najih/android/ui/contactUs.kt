package com.najih.android.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Contact Us", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(8.dp))

        // Name Input
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Email Input
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp)),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Phone Number Input
        TextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Phone Number") },
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp)),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Country Input
        TextField(
            value = country,
            onValueChange = { country = it },
            label = { Text("Country") },
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Message Input
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .verticalScroll(rememberScrollState()) // Enables scrolling
        ) {
            TextField(
                value = message,
                onValueChange = { message = it },
                label = { Text("Message") },
                modifier = Modifier.fillMaxSize().height(200.dp).clip(RoundedCornerShape(8.dp)),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = false, // Allows multiline input
                maxLines = Int.MAX_VALUE // Allows unlimited lines, but will scroll
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        // Send Button
        Button(
            onClick = { coroutineScope.launch {
                try{
                    val status = postContactForm(httpClient,name,email,phoneNumber,country,message)
                    Log.d("ApiClient" , " $status")
                }catch(e : Exception){
                    Log.e("ApiClient" , "there was error in sending the contact form data ${e.message}")
                }
            } },
            enabled = isFormValid,
            modifier = Modifier.fillMaxWidth().background(Color.White)
        ) {
            Text("Send")
        }
    }
}

@Preview
@Composable
fun  ContactUs () {
//    ContactUsForm()
}