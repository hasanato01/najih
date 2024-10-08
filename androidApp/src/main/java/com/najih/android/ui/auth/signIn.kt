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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.najih.android.api.CreateHttpClient
import com.najih.android.api.auth.signIn
import com.najih.android.ui.uitilis.Navbar
import io.ktor.client.engine.android.Android
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SignIn(navController: NavController ) {
    val context = LocalContext.current
    val httpClient = CreateHttpClient(Android)
    val coroutineScope = rememberCoroutineScope()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var signInError by remember { mutableStateOf<String?>(null) }
    Scaffold(
        topBar = { Navbar(navController, backText ="Nice to have you back" , titleText = "Sign in" )},
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(10.dp)  // Apply the padding values from the Scaffold
                    .consumeWindowInsets(paddingValues), // Consume the insets to avoid extra padding
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(12.dp)
                ) {
                    // Logo Image
                    Image(
                        painter = painterResource(id = R.drawable.logo), // Replace with your logo resource ID
                        contentDescription = "Logo",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .height(200.dp)
                            .width(200.dp)
                            .padding( 24.dp)
                    )

                    // Email Field
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email", color = Color.Gray) },
                        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = Color(0xFFc0c0c0 )) },  // Add a leading icon
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            focusedBorderColor =Color(0xFFc0c0c0 ),
                            unfocusedBorderColor = Color.Gray,

                            ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding( vertical = 8.dp)
                            .background(Color.LightGray.copy(alpha = 0.1f), shape = RoundedCornerShape(12.dp))
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("password", color = Color.Gray) },  // Use a subtle color for the label
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = Color(0xFFc0c0c0 )) },  // Add a leading icon
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),  // Rounded corners for a modern look
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            focusedBorderColor =Color(0xFFc0c0c0 ),
                            unfocusedBorderColor = Color.Gray,

                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding( vertical = 8.dp)  // Add some padding for better spacing
                            .background(Color.LightGray.copy(alpha = 0.1f), shape = RoundedCornerShape(12.dp))  // Light background for contrast
                    )




                    // Sign In Button
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                try {
                                    val response = signIn(
                                        httpClient,
                                        context,
                                        email = "admin@najih.com",
                                        password = "123123"
                                    )
                                    if(response.success){
                                        navController.navigate("Home_Page")
                                    }
                                    Log.d(
                                        "SignInButton",
                                        "Sign-in successful: ${response.accessToken}"
                                    )
                                } catch (e: Exception) {
                                    Log.e("SignInButton", "Sign-in failed: ${e.message}")
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
                        Text("Sign In", fontSize = 16.sp, color = Color.White)
                    }
                    // Show sign-in error if any
                    signInError?.let { error ->
                        Text(
                            text = error,
                            color = Color.Red,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }
                    // Sign Up Text Button
                    TextButton(
                        onClick = {navController.navigate("sign_up")},
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text("Sign Up", color = Color.Blue)
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
