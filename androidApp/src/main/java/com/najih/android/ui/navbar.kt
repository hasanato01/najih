package com.najih.android.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.najih.android.R
import com.najih.android.util.GlobalFunctions

@Composable
fun navbar(navController: NavController) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var languageExpanded by remember { mutableStateOf(false) }
    var selectedLanguage by remember { mutableStateOf("English") }
    var profileMenuExpanded by remember { mutableStateOf(false) }
    var userInfo by remember { mutableStateOf(GlobalFunctions.getUserInfo(context)) }

    var userName = userInfo.userName
    val greetingText = userInfo.userName ?: "Welcome to Najih"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(
                color = colorResource(id = R.color.primaryColor),
                shape = RoundedCornerShape(60.dp)
            )
            .padding(start = 10.dp, end = 10.dp)
    ) {
        // First Section: Profile and Icons
        Row(
            modifier = Modifier
                .fillMaxWidth(),
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
                Text(text = "Hi $greetingText" , fontSize = 18.sp, color = Color.White)
            }
            DropdownMenu(expanded = profileMenuExpanded, onDismissRequest = { profileMenuExpanded = false }) {
                if(userName != null)
                { DropdownMenuItem(
                    text = { Text(text = "Log out") },
                    onClick = {
                        GlobalFunctions.clearUserInfo(context)
                        userInfo = GlobalFunctions.getUserInfo(context)
                        profileMenuExpanded = false }
                )
//
                }else {

                    DropdownMenuItem(
                        text = { Text(text = "sign in") },
                        onClick = { profileMenuExpanded = false ; navController.navigate("sign_in") }
                    )
                }


//
            }
            // Icons for Language and List
            Row {
                IconButton(onClick = { languageExpanded = !languageExpanded }) {
                    val languageIcon = if (selectedLanguage == "English") {
                        painterResource(id = R.drawable.uk)
                    } else {
                        painterResource(id = R.drawable.egypt)
                    }
                    Icon(
                        painter = languageIcon,
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
                    DropdownMenuItem(
                        text = { Text("Label text") },
                        onClick = { /* Handle link 1 click */ })
                    DropdownMenuItem(
                        text = { Text("Label text") },
                        onClick = { /* Handle link 2 click */ })
                    DropdownMenuItem(
                        text = { Text("Label text") },
                        onClick = { /* Handle link 3 click */ })
                }
            }
        }

    }
}

@Preview
@Composable
fun PreviewNavBar() {
//    navbar()
}