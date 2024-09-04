package com.najih.android.ui.homePage.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.najih.android.R
import androidx.compose.runtime.*
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@Composable
fun HomePage_navbar(navController: NavController) {
    var expanded by remember { mutableStateOf(false) }
    var languageExpanded by remember { mutableStateOf(false) }
    var selectedLanguage by remember { mutableStateOf("English") }
    var profileMenuExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(
                color = colorResource(id = R.color.primaryColor),
                shape = RoundedCornerShape(24.dp)
            )
            .padding(16.dp)
    ) {
        // First Section: Profile and Icons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically , modifier = Modifier.clickable { profileMenuExpanded = true }) {
                Image(
                    painter = painterResource(id = R.drawable.user),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "welcome to najih", fontSize = 18.sp, color = Color.White)
            }
            DropdownMenu(expanded = profileMenuExpanded, onDismissRequest = { profileMenuExpanded = false }) {
                DropdownMenuItem(
                    text = { Text(text = "sign in") },
                    onClick = { profileMenuExpanded = false ; navController.navigate("sign_in") }
                )
//                DropdownMenuItem(
//                    text = { Text(text = "sign in") },
//                    onClick = { profileMenuExpanded = false ; navController.navigate("sign_in") }
//                )
            }

            // Icons for Language and List
            Row {
                IconButton(onClick = { languageExpanded = !languageExpanded }) {
                    val languageIcon = if (selectedLanguage == "English") {
                        painterResource(id = R.drawable.uk)
                    }else {
                        painterResource(id = R.drawable.egypt)
                    }
                    Icon(
                        painter =languageIcon,
                        contentDescription = "Language",
                        modifier = Modifier.size(24.dp),
                        tint = Color.Unspecified
                    )
                }
                DropdownMenu(
                    expanded = languageExpanded,
                    onDismissRequest = { languageExpanded = false },
                    modifier = Modifier.background(Color.White)
                ) {
                    DropdownMenuItem(
                        text = { Text("English") },
                        onClick = {
                            selectedLanguage = "English"
                            languageExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Other") },
                        onClick = {
                            selectedLanguage = "Other"
                            languageExpanded = false
                        }
                    )
                }
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        painter = painterResource(id = R.drawable.four_circle),
                        contentDescription = "List",
                        modifier = Modifier.size(24.dp),
                        tint = Color.Unspecified
                    )
                }
                // Dropdown Menu for List Icon
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(Color.White)
                ) {
                    DropdownMenuItem(text = { Text("Label text") }, onClick = { /* Handle link 1 click */ })
                    DropdownMenuItem(text = { Text("Label text") }, onClick = { /* Handle link 2 click */ })
                    DropdownMenuItem(text = { Text("Label text") }, onClick = { /* Handle link 3 click */ })
                }
            }
        }

        // Second Section: Header and Search Bar
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Najih Education",
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier
                    .padding(bottom = 8.dp)
            )
            Text(
                text = "with Najih, always successful",
                fontSize = 12.sp,
                color = Color.White,
                modifier = Modifier
                    .padding(bottom = 8.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .background(Color.White, RoundedCornerShape(30.dp))
            ) {
                TextField(
                    value = "",
                    onValueChange = { /* Handle search input */ },
                    placeholder = { Text(text = "Search for lessons...") },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.White,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewNavBar() {
    val navController = rememberNavController()

    // Pass the mock NavController to your composable
    HomePage_navbar(navController = navController)
}


