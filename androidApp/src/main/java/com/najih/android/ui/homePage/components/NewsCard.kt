package com.najih.android.ui.homePage.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NewsCard(
    headline: String,
    description: String,
    imagePainter: Painter,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(150.dp) // Fixed width for news cards
            .height(200.dp)
        .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp)) // Light grey border
    .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White // Set the card background color directly here
        ),
        shape = RoundedCornerShape(8.dp) // Rounded corners
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)

        ) {
            // Image takes up half of the height of the Card
            Image(
                painter = imagePainter,
                contentDescription = "News Image",
                modifier = Modifier
                    .height(80.dp) // Half of the card height
                    .fillMaxWidth() // Fill the width of the card
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                    .background(Color.Transparent) // Rounded corners on top
            )

            // Column for text elements
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Take up the remaining space
                    .padding(4.dp)
                // Space between image and text
            ) {
                Text(
                    text = headline,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent) // Fill the width of the container
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    fontSize = 10.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent) // Fill the width of the container
                )
            }
        }
    }
}
