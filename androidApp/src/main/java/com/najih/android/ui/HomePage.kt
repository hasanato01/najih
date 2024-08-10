package com.najih.android.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.najih.android.R
import kotlinx.coroutines.delay

@Composable
fun HomePage(modifier: Modifier = Modifier) {
Navbar()

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun Navbar() {
    CenterAlignedTopAppBar(
        title = {
            // You can use the title slot for logo or any text
            Image(
                painter = painterResource(id = R.drawable.logo), // Replace with your logo resource
                contentDescription = "Logo",
                modifier = Modifier.size(40.dp) // Adjust size as needed
            )
        },
        actions = {
            // User Profile
            Image(
                painter = painterResource(id = R.drawable.logo), // Replace with your user profile image resource
                contentDescription = "User Profile",
                modifier = Modifier.size(40.dp) // Adjust size as needed
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}