package com.najih.android.ui.homePage.components


import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.najih.android.R
import com.najih.android.api.CreateHttpClient
import com.najih.android.api.globalData.RECORDED_SUBJECTS_ENDPOINT
import com.najih.android.api.globalData.STREAMS_SUBJECTS_ENDPOINT
import com.najih.android.util.GlobalFunctions
import io.ktor.client.engine.android.Android
import kotlinx.coroutines.launch

@Composable
fun StageCard(
    stage: String,
    type: String,
    modifier: Modifier = Modifier, // Provide a default modifier
    navController: NavController
) {
    Card(
        modifier = modifier
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .background(Color.White)
            .padding(16.dp), // Adjusted padding
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(0.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left image
            Image(
                painter = painterResource(id = R.drawable.k),
                contentDescription = "$stage image 1",
                modifier = Modifier
                    .size(40.dp)
                    .weight(0.2f) // Smaller weight for image
            )

            // Column with stage text and buttons
            Column(
                modifier = Modifier
                    .weight(0.8f)
                    .padding(start = 16.dp), // Space between image and text
                horizontalAlignment = Alignment.Start
            ) {
                // Stage text
                Text(
                    text = stage,
                    fontSize = 18.sp, // Slightly larger font for clarity
                    fontWeight = FontWeight.Bold, // Bold to give more emphasis
                    color = Color.Black
                )

                // Row of two buttons (Live and Recorded Lessons)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp), // Space between text and buttons
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Button 1: Live Lessons
                    StageButton(
                        iconId = R.drawable.microphone,
                        label = stringResource(R.string.live_lessons),
                        onClick = { navController.navigate("subjects/$type/$stage/$STREAMS_SUBJECTS_ENDPOINT") }
                    )

                    // Button 2: Recorded Lessons
                    StageButton(
                        iconId = R.drawable.video_camera,
                        label = stringResource(R.string.recorded_lessons),
                        onClick = { navController.navigate("subjects/$type/$stage/$RECORDED_SUBJECTS_ENDPOINT") }
                    )
                }
            }
        }
    }
}

// Reusable Button Composable
@Composable
fun StageButton(
   iconId: Int,
    label: String,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .height(30.dp)

            .border(
                width = 1.dp, // Border width
                color = Color.Gray, // Border color
                shape = RoundedCornerShape(16.dp) // Rounded corner radius
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 4.dp) // Padding inside the button
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = label,
            modifier = Modifier.size(16.dp),
            tint = Color.Unspecified
        )
        Spacer(modifier = Modifier.width(8.dp)) // Space between icon and text
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.Black
        )
    }
}

@Preview
@Composable
fun StageCardPreview() {
    val navController = rememberNavController() // Correcting to rememberNavController
    val modifier = Modifier // Ensure Modifier is initialized
    StageCard(stage = "High School", type = "sss", modifier = modifier, navController = navController)
}
