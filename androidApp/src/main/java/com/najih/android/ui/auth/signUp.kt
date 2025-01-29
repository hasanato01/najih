package com.najih.android.ui.auth



import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.najih.android.R
import com.najih.android.api.CreateHttpClient
import com.najih.android.api.auth.ResendCode
import com.najih.android.api.auth.VerifyCode
import com.najih.android.api.auth.signUp
import com.najih.android.dataClasses.PurchaseResponse
import com.najih.android.dataClasses.SignUpRequest
import com.najih.android.dataClasses.SignUpResponse
import com.najih.android.ui.uitilis.Navbar
import io.ktor.client.engine.android.Android
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUp(navController: NavController) {
    val context = LocalContext.current
    val httpClient = CreateHttpClient(Android)
    val coroutineScope = rememberCoroutineScope()

    // States for form fields
    var name by remember { mutableStateOf("") }
    var fatherName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var nationality by remember { mutableStateOf("") }
    var birthYear by remember { mutableStateOf("") }
    var schoolType by remember { mutableStateOf("") }
    var residence by remember { mutableStateOf("") }
    var whatsApp by remember { mutableStateOf("") }
    var heardFrom by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val role = "student"

    // States for validation errors
    var nameError by remember { mutableStateOf(false) }
    var fatherNameError by remember { mutableStateOf(false) }
    var lastNameError by remember { mutableStateOf(false) }
    var genderError by remember { mutableStateOf(false) }
    var nationalityError by remember { mutableStateOf(false) }
    var birthYearError by remember { mutableStateOf(false) }
    var schoolTypeError by remember { mutableStateOf(false) }
    var residenceError by remember { mutableStateOf(false) }
    var whatsAppError by remember { mutableStateOf(false) }
    var heardFromError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var confirmPasswordError by remember { mutableStateOf(false) }

    // Dropdown and loading state
    var genderExpanded by remember { mutableStateOf(false) }
    var schoolExpanded by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var showVerificationDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }
    var isSignUpSuccess by remember { mutableStateOf(false) }
    var verificationCode by remember { mutableStateOf("") }
    var isCountdownActive by remember { mutableStateOf(false) }  // To track if countdown is active
    var countdownTime by remember { mutableStateOf(3) }  // Start countdown from 3 seconds

    Scaffold(
        topBar = {
            Navbar(
                navController = navController,
                backText = stringResource(R.string.welcome_to_najih_community),
                titleText = stringResource(R.string.sign_up)
            )
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(16.dp)
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Name

                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp) // Adds spacing between the fields
                    ) {
                        // Name field
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it; nameError = false },
                            label = { Text(stringResource(R.string.Name)) },
                            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                            isError = nameError,
                            singleLine = true,
                            modifier = Modifier.weight(1f) // Ensures the field takes equal space
                        )

                        // Last Name field
                        OutlinedTextField(
                            value = lastName,
                            onValueChange = { lastName = it; lastNameError = false },
                            label = { Text(stringResource(R.string.last_name)) },
                            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                            isError = lastNameError,
                            singleLine = true,
                            modifier = Modifier.weight(1f) // Ensures the field takes equal space
                        )
                    }
                }
                    // Father's Name and Gender
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Father's Name Field
                            OutlinedTextField(
                                value = fatherName,
                                onValueChange = { fatherName = it; fatherNameError = false },
                                label = { Text(stringResource(R.string.fathers_name)) },
                                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                                isError = fatherNameError,
                                singleLine = true,
                                modifier = Modifier.weight(1f)
                            )

                            // Gender Dropdown
                            Box(modifier = Modifier.weight(1f)) {
                                ExposedDropdownMenuBox(
                                    expanded = genderExpanded,
                                    onExpandedChange = { genderExpanded = !genderExpanded }
                                ) {
                                    OutlinedTextField(
                                        value = gender,
                                        onValueChange = {},
                                        label = { Text(stringResource(R.string.gender)) },
                                        readOnly = true,
                                        isError = genderError,
                                        trailingIcon = {
                                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = genderExpanded)
                                        },
                                        modifier = Modifier.menuAnchor().fillMaxWidth()
                                    )
                                    DropdownMenu(
                                        expanded = genderExpanded,
                                        onDismissRequest = { genderExpanded = false }
                                    ) {
                                        listOf("male", "female").forEach { option ->
                                            DropdownMenuItem(
                                                text = { Text(option) },
                                                onClick = {
                                                    gender = option
                                                    genderExpanded = false
                                                    genderError = false
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }

}





                        // Nationality
                item {
                    OutlinedTextField(
                        value = nationality,
                        onValueChange = { nationality = it; nationalityError = false },
                        label = { Text(stringResource(R.string.Nationality)) },
                        leadingIcon = {  Icon(
                            painter = painterResource(id = R.drawable.country),
                            modifier = Modifier.size(24.dp),// Replace ic_call with your drawable name
                            contentDescription = null
                        ) },
                        isError = nationalityError,
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                }

                // Birth Year
                item {
                    OutlinedTextField(
                        value = birthYear,
                        onValueChange = { birthYear = it; birthYearError = false },
                        label = { Text(stringResource(R.string.birth_year)) },
                        leadingIcon = {  Icon(
                            painter = painterResource(id = R.drawable.calendar),
                            modifier = Modifier.size(24.dp),// Replace ic_call with your drawable name
                            contentDescription = null
                        ) },
                        isError = birthYearError,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )

                }

                // School Type Dropdown
                item {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        ExposedDropdownMenuBox(
                            expanded = schoolExpanded,
                            onExpandedChange = { schoolExpanded = !schoolExpanded }
                        ) {
                            OutlinedTextField(
                                value = schoolType,
                                onValueChange = {},
                                label = { Text(stringResource(R.string.SchoolType)) },
                                readOnly = true,
                                isError = schoolTypeError,
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = schoolExpanded)
                                },
                                modifier = Modifier.menuAnchor().fillMaxWidth()
                            )
                            DropdownMenu(
                                expanded = schoolExpanded,
                                onDismissRequest = { schoolExpanded = false }
                            ) {
                                listOf("public", "language").forEach { option ->
                                    DropdownMenuItem(
                                        text = { Text(option) },
                                        onClick = {
                                            schoolType = option
                                            schoolExpanded = false
                                            schoolTypeError = false
                                        }
                                    )
                                }
                            }
                        }

                    }
                }

                // Residence
                item {
                    OutlinedTextField(
                        value = residence,
                        onValueChange = { residence = it; residenceError = false },
                        label = { Text(stringResource(R.string.Residence)) },
                        leadingIcon = {  Icon(
                            painter = painterResource(id = R.drawable.residenc),
                            modifier = Modifier.size(24.dp),// Replace ic_call with your drawable name
                            contentDescription = null
                        ) },
                        isError = residenceError,
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                }

                // WhatsApp
                item {
                    OutlinedTextField(
                        value = whatsApp,
                        onValueChange = { whatsApp = it; whatsAppError = false },
                        label = { Text(stringResource(R.string.whatsApp)) },
                        leadingIcon = {  Icon(
                            painter = painterResource(id = R.drawable.whatsapp22),
                            modifier = Modifier.size(24.dp),// Replace ic_call with your drawable name
                            contentDescription = null
                        ) },
                        isError = whatsAppError,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )

                }

                // Heard From
                item {
                    OutlinedTextField(
                        value = heardFrom,
                        onValueChange = { heardFrom = it; heardFromError = false },
                        label = { Text(stringResource(R.string.heardFrom)) },
                        leadingIcon = {  Icon(
                            painter = painterResource(id = R.drawable.ask),
                            modifier = Modifier.size(24.dp),// Replace ic_call with your drawable name
                            contentDescription = null
                        ) },
                        isError = heardFromError,
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                }

                // Email
                item {
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it; emailError = false },
                        label = { Text(stringResource(R.string.email_label)) },
                        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                        isError = emailError,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier.fillMaxWidth()

                    )

                }

                // Password
                item {
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it; passwordError = false },
                        label = { Text(stringResource(R.string.password_label)) },
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                        isError = passwordError,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Next
                        ),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                }

                // Confirm Password
                item {
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it; confirmPasswordError = false },
                        label = { Text(stringResource(R.string.confirm_password)) },
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                        isError = confirmPasswordError,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Next
                        ),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                }

                // Submit Button
                item {
                    Button(
                        onClick = {
                            // Validation logic
                            nameError = name.isBlank()
                            fatherNameError = fatherName.isBlank()
                            lastNameError = lastName.isBlank()
                            genderError = gender.isBlank()
                            nationalityError = nationality.isBlank()
                            birthYearError = birthYear.isBlank()
                            schoolTypeError = schoolType.isBlank()
                            residenceError = residence.isBlank()
                            whatsAppError = whatsApp.isBlank()
                            heardFromError = heardFrom.isBlank()
                            emailError = email.isBlank()
                            passwordError = password.isBlank()
                            confirmPasswordError = confirmPassword.isBlank() || confirmPassword != password

                            if (listOf(
                                    nameError, fatherNameError, lastNameError, genderError,
                                    nationalityError, birthYearError, schoolTypeError, residenceError,
                                    whatsAppError, heardFromError, emailError, passwordError, confirmPasswordError
                                ).any { it }
                            ) {
                                Toast.makeText(context, "Fix validation errors!", Toast.LENGTH_SHORT).show()
                                return@Button
                            }

                            // Make the sign-up request
                            isLoading = true
                            coroutineScope.launch {
                                try {
                                    val response = signUp(
                                        httpClient,
                                        context,
                                        SignUpRequest(
                                            name = name,
                                            fatherName = fatherName,
                                            lastName = lastName,
                                            gender = gender,
                                            nationality = nationality,
                                            birthYear = birthYear.toIntOrNull() ?: 0,
                                            schoolType = schoolType,
                                            residence = residence,
                                            whatsApp = whatsApp,
                                            heardFrom = heardFrom,
                                            email = email,
                                            password = password,
                                            confirmPassword = confirmPassword,
                                            role = role
                                        )
                                    )
                                    if (response.success) {
                                        dialogMessage = "You have successfully signed up"
                                        showDialog = true
                                        coroutineScope.launch {
                                            delay(3000) // Wait for 3 seconds
                                            navController.navigate("sign_in")
                                        }
                                    }


                                } catch (e: Exception) {
                                    dialogMessage = if (e.message != null && e.message!!.contains("UserExistsError")) {
                                        "A user with the given username is already registered"
                                    } else {
                                        "Sign-up failed. Please try again."
                                    }
                                    showDialog = true
                                }
                                finally {
                                    isLoading = false

                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = Color.White
                            )
                        } else {
                            Text("Sign Up")
                        }
                    }
                }
            }
        }
    )

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Sign-up Error") },
            text = { Text(dialogMessage) },
            confirmButton = {
                Button(onClick = { showDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
//    if (showVerificationDialog) {
//        AlertDialog(
//            onDismissRequest = { showVerificationDialog = false },
//            title = { Text("Enter Verification Code") },
//            text = {
//                Column(
//                    modifier = Modifier.fillMaxWidth(),
//                    verticalArrangement = Arrangement.spacedBy(8.dp),
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    OutlinedTextField(
//                        value = verificationCode,
//                        onValueChange = { verificationCode = it },
//                        label = { Text("Verification Code") },
//                        singleLine = true,
//                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
//                        textStyle = TextStyle(fontSize = 30.sp),  // Set the font size to a large value
//                        modifier = Modifier.fillMaxWidth()
//                    )
//
//
//                    Button(
//                        onClick = {
//                            coroutineScope.launch {
//                                try {
//                                    val response = VerifyCode(httpClient, email, verificationCode)
//                                    if (response.success == true) {
//                                        showVerificationDialog = false
//                                        isCountdownActive = true  // Start the countdown dialog
//                                        for (i in 3 downTo 1) {
//                                            countdownTime = i  // Update the countdown time
//                                            delay(1000) // Wait 1 second
//                                        }
//                                        Toast.makeText(context, response.message ?: "Verification Successful!", Toast.LENGTH_LONG).show()
//                                        navController.navigate("sign_in")  // Navigate after countdown finishes
//                                    }
//                                } catch (e: Exception) {
//                                    Toast.makeText(context, e.message ?: "Verification failed", Toast.LENGTH_SHORT).show()
//                                }
//                            }
//                        },
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        Text("Verify")
//                    }
//
//                    Button(
//                        onClick = {
//                            coroutineScope.launch {
//                                try {
//                                    ResendCode(httpClient, email)
//                                    Toast.makeText(context, "Verification email resent!", Toast.LENGTH_SHORT).show()
//                                } catch (e: Exception) {
//                                    Toast.makeText(context, e.message ?: "Failed to resend email", Toast.LENGTH_SHORT).show()
//                                }
//                            }
//                        },
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        Text("Resend Email")
//                    }
//
//                    Button(
//                        onClick = { showVerificationDialog = false },
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        Text("Cancel")
//                    }
//                }
//            },
//            confirmButton = {},
//            dismissButton = {}
//        )
//    }
//    if (isCountdownActive) {
//        AlertDialog(
//            onDismissRequest = { },
//            title = { Text("Verification Complete") },
//            text = {
//                Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                    Text(
//                        text = "You will be redirected to sign-in in:",
//                        fontSize = 20.sp
//                    )
//                    Text(
//                        text = "$countdownTime",  // Countdown time displayed
//                        fontSize = 50.sp,
//                        fontWeight = FontWeight.Bold
//                    )
//                }
//            },
//            confirmButton = {
//                // No need for a confirm button, this will just show the countdown
//            }
//        )
//    }


}
