package com.najih.android.ui.auth

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.najih.android.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.najih.android.api.CreateHttpClient
import com.najih.android.api.auth.signIn
import com.najih.android.ui.uitilis.Navbar
import com.najih.android.util.GlobalFunctions
import io.ktor.client.engine.android.Android
import kotlinx.coroutines.launch


@Composable
fun SignIn(navController: NavController) {
    val context = LocalContext.current
    val httpClient = CreateHttpClient(Android)
    val coroutineScope = rememberCoroutineScope()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var signInError by remember { mutableStateOf<String?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Navbar(
                navController,
                backText = stringResource(R.string.nice_to_have_you_back),
                titleText = stringResource(R.string.sign_in)
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(10.dp)
                    .consumeWindowInsets(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(12.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Logo",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .height(200.dp)
                            .width(200.dp)
                            .padding(24.dp)
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text(stringResource(id = R.string.email_label), color = Color.Gray) },
                        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = Color(0xFFc0c0c0)) },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            focusedBorderColor = Color(0xFFc0c0c0),
                            unfocusedBorderColor = Color.Gray,
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .background(
                                Color.LightGray.copy(alpha = 0.1f),
                                shape = RoundedCornerShape(12.dp)
                            )
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text(stringResource(id = R.string.password_label), color = Color.Gray) },
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = Color(0xFFc0c0c0)) },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            focusedBorderColor = Color(0xFFc0c0c0),
                            unfocusedBorderColor = Color.Gray,
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .background(
                                Color.LightGray.copy(alpha = 0.1f),
                                shape = RoundedCornerShape(12.dp)
                            )
                    )

                    Button(
                        onClick = {
                            coroutineScope.launch {
                                try {
                                    val response = signIn(httpClient, context, email, password)
                                    Log.d("ApiClient", "Response: $response")  // Log response details

                                    if (response.success) {
                                        navController.navigate("Home_Page")
                                    } else {
                                        signInError = response.err?.message?.let { message ->
                                            Log.d("ApiClient", "Error details: $message")
                                            when (message) {
                                                "Missing credentials" -> context.getString(R.string.missing_credentials_message)
                                                "Password or username is incorrect" -> context.getString(R.string.incorrect_credentials_message)
                                                else -> context.getString(R.string.generic_error_message)
                                            }
                                        } ?: context.getString(R.string.generic_error_message)
                                        showDialog = true
                                    }
                                } catch (e: Exception) {
                                    signInError = e.message ?: context.getString(R.string.sign_in_failed)
                                    showDialog = true
                                    Log.e("ApiClient", "Exception: ${e.message}")
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(top = 10.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
                    ) {
                        Text(
                            text = stringResource(id = R.string.sign_in_button),
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    }



                    signInError?.let {
                        if (showDialog) {
                            AlertDialog(
                                onDismissRequest = { showDialog = false },
                                title = { Text(stringResource(id = R.string.sign_in_error)) },
                                text = { Text(it) },  // Directly use the existing `signInError` message
                                confirmButton = {
                                    TextButton(onClick = { showDialog = false }) {
                                        Text(stringResource(id = R.string.ok))
                                    }
                                }
                            )
                        }
                    }


                    TextButton(
                        onClick = { navController.navigate("sign_up") },
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text(stringResource(id = R.string.sign_up_button), color = Color.Blue)
                    }
                }
            }
        }
    )
}


@Preview
@Composable
fun SignInPreview() {
    val navController = rememberNavController()
    SignIn(navController)


}
