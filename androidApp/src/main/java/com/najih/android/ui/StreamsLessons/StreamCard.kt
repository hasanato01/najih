package com.najih.android.ui.StreamsLessons

import Lesson
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.najih.android.R
import com.najih.android.dataClasses.Streams
import com.najih.android.util.GlobalFunctions



@Composable
fun StreamCard(
    stream: Streams,
    isCheckableMode: Boolean,
    isChecked: Boolean,
    isPurchased: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onLessonPurchasedClick: () -> Unit,
    onPreviewLessonClick: (String) -> Unit
) {
    val context = LocalContext.current
    val currentLanguage by remember { mutableStateOf(GlobalFunctions.getUserLanguage(context) ?: "ar") }
    val lessonName = if (currentLanguage == "ar") stream.name.ar else stream.name.en
    val lessonDescription = if (currentLanguage == "ar") stream.description.ar else stream.description.en
    val isFree = stream.isFree

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(230.dp) // Adjusted height for a better layout
            .padding(16.dp)
            .border(width = 1.dp, color = Color(0xFFDADADA), shape = RoundedCornerShape(12.dp))
            .shadow(5.dp, shape = RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Checkbox at Top-Left Corner
            if (isCheckableMode && !isPurchased && !isFree) {
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.TopEnd) // Positioned in the top-left corner
                ) {
                    Checkbox(
                        checked = isChecked,
                        onCheckedChange = onCheckedChange,
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color(0xFF2196F3),
                            uncheckedColor = Color.Gray,
                            checkmarkColor = Color.White
                        ),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Left Section: Lesson Details
                    Column(
                        modifier = Modifier.weight(2f),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = lessonName,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            text = lessonDescription,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(top = 4.dp)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Date Information
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.baseline_date_range_24),
                                contentDescription = "Start Date",
                                modifier = Modifier.size(20.dp),
                                colorFilter = ColorFilter.tint(Color(0xFF2196F3))
                            )
                            Text(
                                text = stringResource(id = R.string.start_date, stream.startDate),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(top = 4.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.baseline_date_range_24),
                                contentDescription = "End Date",
                                modifier = Modifier.size(20.dp),
                                colorFilter = ColorFilter.tint(Color(0xFF2196F3))
                            )
                            Text(
                                text = stringResource(id = R.string.end_date, stream.endDate),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                    }

                    // Right Section: Buttons
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        // Preview Button
                        Button(
                            onClick = { onPreviewLessonClick(stream.link) },
                            modifier = Modifier
                                .width(130.dp)
                                .height(40.dp),
                            contentPadding = PaddingValues(0.dp),
                            shape = RoundedCornerShape(5.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3))
                        ) {
                            Text(
                                text = stringResource(R.string.preview_lesson),
                                color = Color.White,
                                fontSize = 14.sp,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }

                        // Watch Button (for Purchased/Free Lessons)
                        if (isPurchased || isFree) {
                            Button(
                                onClick = { /* Handle action for purchased or free lesson */ },
                                modifier = Modifier
                                    .width(130.dp)
                                    .height(40.dp),
                                shape = RoundedCornerShape(5.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF50C878))
                            ) {
                                Text(
                                    text = stringResource(R.string.watch_lesson),
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }

            // Tags (Purchased / Free)
            if (isPurchased) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Tag(text = stringResource(R.string.purchased), color = Color(0xFF50C878))
                }
            }
            if (isFree) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Tag(text = stringResource(R.string.free), color = Color(0xFFFFA500))
                }
            }
        }
    }
}



@Composable
fun Tag(text: String, color: Color, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(color = color, shape = RoundedCornerShape(4.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
    }
}