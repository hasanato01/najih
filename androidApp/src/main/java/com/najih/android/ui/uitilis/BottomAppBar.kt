package com.najih.android.ui.uitilis

import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.najih.android.R
import androidx.navigation.NavController
import com.najih.android.dataClasses.NavigationItem
import androidx.compose.foundation.layout.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp


@Composable
fun BottomNavBar(navController: NavController) {
        BottomAppBar(
            modifier = Modifier.fillMaxWidth(),
            contentColor = Color.Black,
            containerColor = Color.White
        ) {
            val navItems = listOf(
                NavigationItem(stringResource(R.string.label_home), painterResource(R.drawable.home), "home_page"),
                NavigationItem(stringResource(R.string.label_exams), painterResource(R.drawable.exams_blue), "exams"),
                NavigationItem(stringResource(R.string.label_profile), painterResource(R.drawable.user_blue), "My_profile"),
                NavigationItem(stringResource(R.string.setting), painterResource(R.drawable.seeting), "setting")
            )

            navItems.forEach { item ->
                IconButton(
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentHeight(),
                    onClick = {
                    navController.navigate(item.route)
                }) {
                    Column(

                        horizontalAlignment = Alignment.CenterHorizontally) {

                        Icon(
                            painter = item.icon,
                            contentDescription = item.label,
                            modifier = Modifier.size(24.dp),
                            tint = Color.Unspecified
                        )
                        Text(text = item.label, fontSize = 10.sp)
                    }
                }
            }
        }
    }


