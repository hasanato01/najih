package com.najih.android.ui.recordedLessons

import GetSubjectLessons
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.najih.android.R

@Composable
fun SubjectInfo(
    subjectInfo: GetSubjectLessons?
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9))
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            // Enrollment Details Title
            Text(
                text = stringResource(id = R.string.enrollment_details),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF003366),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Pricing Section
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.back_button),
                    contentDescription = "Pricing Icon",
                    modifier = Modifier.size(20.dp),
                    tint = Color(0xFF0066CC)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(id = R.string.pricing),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333),
                    modifier = Modifier.alignByBaseline()
                )
            }
            Column(modifier = Modifier.padding(start = 28.dp)) {
                Text(text = stringResource(id = R.string.price_per_lesson, subjectInfo?.lessonPrice ?: "Unknown"), fontSize = 14.sp)
                Text(text = stringResource(id = R.string.total_price, subjectInfo?.lessonPriceAll ?: "Unknown"), fontSize = 14.sp)
            }

            // Other Options Section
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.back_button),
                    contentDescription = "Other Options Icon",
                    modifier = Modifier.size(20.dp),
                    tint = Color(0xFF0066CC)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(id = R.string.other_options),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333),
                    modifier = Modifier.alignByBaseline()
                )
            }
            Column(modifier = Modifier.padding(start = 28.dp)) {
                subjectInfo?.otherPrices?.forEach { price ->
                    Text(
                        text = "${price.key} Lessons: ${price.value}",
                        fontSize = 14.sp
                    )
                }
            }

            // Course Dates Section
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.back_button),
                    contentDescription = "Calendar Icon",
                    modifier = Modifier.size(20.dp),
                    tint = Color(0xFF0066CC)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(id = R.string.course_dates),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333),
                    modifier = Modifier.alignByBaseline()
                )
            }
            Column(modifier = Modifier.padding(start = 28.dp)) {
                Text(text = stringResource(id = R.string.start_date, subjectInfo?.startDate ?: "Unknown"), fontSize = 14.sp)
                Text(text = stringResource(id = R.string.end_date, subjectInfo?.endDate ?: "Unknown"), fontSize = 14.sp)
            }

            // Payment Method Section
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.back_button),
                    contentDescription = "Payment Icon",
                    modifier = Modifier.size(20.dp),
                    tint = Color(0xFF0066CC)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(id = R.string.payment_method),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333),
                    modifier = Modifier.alignByBaseline()
                )
            }
            Column(modifier = Modifier.padding(start = 28.dp)) {
                Text(text = subjectInfo?.paymentMethod ?: "Unknown", fontSize = 14.sp)
            }

            // Seats Info Section
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.back_button),
                    contentDescription = "Seats Icon",
                    modifier = Modifier.size(20.dp),
                    tint = Color(0xFF0066CC)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(id = R.string.seats_info),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333),
                    modifier = Modifier.alignByBaseline()
                )
            }
            Column(modifier = Modifier.padding(start = 28.dp)) {
                Text(text = stringResource(id = R.string.available_seats, subjectInfo?.availableSeats ?: "Unknown"), fontSize = 14.sp)
                Text(text = stringResource(id = R.string.remaining_seats, subjectInfo?.remainingSeats ?: "Unknown"), fontSize = 14.sp)
            }

            // Continue Enrollment Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = { /* Handle enrollment click */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0066CC)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.continue_enrollment),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                }
            }
        }
    }
}
