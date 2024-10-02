package com.najih.android.ui.uitilis

import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.najih.android.R
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.najih.android.dataClasses.NavigationItem
import androidx.compose.foundation.layout.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


@Composable
fun BottomNavBar(navController: NavController) {
        BottomAppBar(
            modifier = Modifier.fillMaxWidth(),
            contentColor = Color.Black,
            containerColor = Color.White
        ) {
            val navItems = listOf(
                NavigationItem("Home", painterResource(R.drawable.home), "home_page"),
                NavigationItem("Exams",painterResource(R.drawable.exam_results), "exams"),
                NavigationItem("Notifications",painterResource(R.drawable.menu), "exams"),
                NavigationItem("Profile", painterResource(R.drawable.profile), "My_profile")
            )

            navItems.forEach { item ->
                IconButton(
                    modifier = Modifier
                        .weight(1f) // Each item takes equal space
                        .wrapContentHeight(),
                    onClick = {
                    navController.navigate(item.route)
                }) {
                    Column(

                        horizontalAlignment = Alignment.CenterHorizontally) {

                        Icon(
                            painter = item.icon,
                            contentDescription = item.label,
                            modifier = Modifier.size(24.dp),  // Set the size of the icon
                            tint = Color.Unspecified  // Apply a tint color to the icon
                        )
                        Text(text = item.label, fontSize = 10.sp) // Tiny text below the icon
                    }
                }
            }
        }
    }


@Preview(showBackground = true)
@Composable
fun BottomNavBarPreview() {
    val navController = rememberNavController() // Mock NavController for preview
    BottomNavBar(navController)
}