package com.najih.android.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Share
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { Navbar() }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // This will avoid overlapping with the Navbar
        ) {
         Categories()

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navbar() {
    CenterAlignedTopAppBar(
        modifier = Modifier
            .height(150.dp)
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(8.dp)),
    title = {
            // You can use the title slot for logo or any text
            Image(
                painter = painterResource(id = R.drawable.logo), // Replace with your logo resource
                contentDescription = "Logo",
                modifier = Modifier.size(80.dp) // Adjust size as needed
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


@Composable
fun Categories() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp)
    ) {
        Text(
            text = "Categories",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            CategoryBox(headerText = "Header 1", modifier = Modifier.weight(1f))
            CategoryBox(headerText = "Header 2", modifier = Modifier.weight(1f))

        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            CategoryBox(headerText = "Header 1", modifier = Modifier.weight(1f))
            CategoryBox(headerText = "Header 2", modifier = Modifier.weight(1f))
        }
    }
}
@Composable
fun CategoryBox(headerText: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(100.dp)
            .shadow(4.dp, RoundedCornerShape(8.dp)) // Add shadow and rounded corners
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .padding(8.dp)


        ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = headerText, // Display the header text
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(

                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { /* TODO: Handle click */ }) {
                    Icon(
                        imageVector = Icons.Filled.Favorite, // Replace with your first icon
                        contentDescription = "Favorite Icon"
                    )
                }
                IconButton(onClick = { /* TODO: Handle click */ }) {
                    Icon(
                        imageVector = Icons.Filled.Share, // Replace with your second icon
                        contentDescription = "Share Icon"
                    )
                }
            }
        }
    }
}
@Preview
@Composable
fun PreviewHomePage() {
    HomePage()
}