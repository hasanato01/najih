package com.najih.android.ui.Streamsstreams

import android.content.Intent
import android.net.Uri
import com.najih.android.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.najih.android.dataClasses.Streams
import com.najih.android.util.YouTubePlayerDialog

@Composable
fun StreamItem(stream: Streams, navController: NavController) {
    var previewVideoId by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .shadow(6.dp, shape = RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.White, Color(0xFFEFEFEF))
                    )
                )
                .padding(16.dp)
        ) {
            // Stream Title
            Text(
                text = stream.name.ar,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333),
                modifier = Modifier.padding(bottom = 6.dp)
            )

            // Stream Description
            Text(
                text = stream.description.ar,
                fontSize = 14.sp,
                color = Color(0xFF666666),
                lineHeight = 18.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Divider(color = Color.LightGray, thickness = 1.dp)

            Spacer(modifier = Modifier.height(8.dp))

            // Date & Time Row
            Column(
                modifier = Modifier.fillMaxWidth(0.7f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                DateItem(R.drawable.calendar, "Start", stream.startDate, Modifier.fillMaxWidth())
                DateItem(R.drawable.calendar, "End", stream.endDate, Modifier.fillMaxWidth())
            }
            Spacer(modifier = Modifier.height(12.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(stream.link))
                        context.startActivity(intent)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(42.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9E9EEC)) // Light Purple
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.play),
                        contentDescription = "Watch",
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = stringResource(id = R.string.join_now), fontSize = 14.sp, color = Color.White)
                }

                Spacer(modifier = Modifier.width(10.dp))

                Button(
                    onClick = { navController.navigate("subject_teachers/${stream.subjectId}") },
                    modifier = Modifier
                        .weight(1f)
                        .height(42.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE3E3E3)) // Light Gray
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.location_blue),
                        contentDescription = "Subject",
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))


                            Text(
                                text = stringResource(id = R.string.go_to_subject),
                                fontSize = 14.sp,
                                color = Color.Black
                            )
                }
            }
        }
    }

    // **Fix: Show the YouTubePlayerDialog inside the composable scope**
    if (previewVideoId != null) {
        YouTubePlayerDialog(
            videoId = previewVideoId!!,
            onDismiss = { previewVideoId = null }
        )
    }
}

// Custom DateItem for Better Visibility
@Composable
fun DateItem(iconRes: Int, label: String, date: String, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .height(30.dp)
            .background(Color(0xFFEAEAEA), shape = RoundedCornerShape(10.dp))
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = "$label: $date",
            fontSize = 12.sp,
            color = Color(0xFF555555)
        )
    }
}