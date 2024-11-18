package com.najih.android.ui.StreamsLessons
import GetSubjectLessons
import android.util.Log
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.najih.android.dataClasses.StreamsSubjects

@Composable
fun StreamsInfo(
    subjectInfo: StreamsSubjects,
    isCheckableMode: Boolean,
    showConfirmationDialog: Boolean,
    onToggleCheckableMode: (Boolean) -> Unit,
    onToggleConfirmDialog: (Boolean) -> Unit,
    selectedLessons: Map<String, Boolean>
) {
    // States to manage dropdown visibility for each section
    var showPricingSection by remember { mutableStateOf(false) }
    var showOtherOptionsSection by remember { mutableStateOf(false) }
    var showCourseDatesSection by remember { mutableStateOf(false) }
    var showPaymentMethodSection by remember { mutableStateOf(false) }
    var showSeatsInfoSection by remember { mutableStateOf(false) }
    Log.d("ApiClient",subjectInfo.toString())

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

            // Pricing Section with Dropdown Toggle
            DropdownSection(
                title = stringResource(id = R.string.pricing),
                iconId = R.drawable.back_button,
                isExpanded = showPricingSection,
                onToggleExpand = { showPricingSection = !showPricingSection }
            ) {
                Text(text = stringResource(id = R.string.price_per_lesson, subjectInfo?.lessonPrice ?: "Unknown"), fontSize = 14.sp)
                Text(text = stringResource(id = R.string.total_price, subjectInfo?.lessonPriceAll ?: "Unknown"), fontSize = 14.sp)
            }

            // Other Options Section
            DropdownSection(
                title = stringResource(id = R.string.other_options),
                iconId = R.drawable.back_button,
                isExpanded = showOtherOptionsSection,
                onToggleExpand = { showOtherOptionsSection = !showOtherOptionsSection }
            ) {
                subjectInfo?.otherPrices?.forEach { price ->
                    Text(text = "${price.key} Lessons: ${price.value}", fontSize = 14.sp)
                }
            }

            // Course Dates Section
            DropdownSection(
                title = stringResource(id = R.string.course_dates),
                iconId = R.drawable.back_button,
                isExpanded = showCourseDatesSection,
                onToggleExpand = { showCourseDatesSection = !showCourseDatesSection }
            ) {
                Text(text = stringResource(id = R.string.start_date, subjectInfo?.startDate ?: "Unknown"), fontSize = 14.sp)
                Text(text = stringResource(id = R.string.end_date, subjectInfo?.endDate ?: "Unknown"), fontSize = 14.sp)
            }

            // Payment Method Section
            DropdownSection(
                title = stringResource(id = R.string.payment_method),
                iconId = R.drawable.back_button,
                isExpanded = showPaymentMethodSection,
                onToggleExpand = { showPaymentMethodSection = !showPaymentMethodSection }
            ) {
                Text(text = subjectInfo?.paymentMethod ?: "Unknown", fontSize = 14.sp)
            }

            // Seats Info Section
            DropdownSection(
                title = stringResource(id = R.string.seats_info),
                iconId = R.drawable.back_button,
                isExpanded = showSeatsInfoSection,
                onToggleExpand = { showSeatsInfoSection = !showSeatsInfoSection }
            ) {
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
                if (isCheckableMode && selectedLessons.isNotEmpty()) {
                    Button(
                        onClick = {
                            onToggleConfirmDialog(true)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0066CC)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.confirm_and_upload),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    }
                } else {
                    Button(
                        onClick = { onToggleCheckableMode(!isCheckableMode) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0066CC)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.buy_now),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DropdownSection(
    title: String,
    iconId: Int,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit,
    content: @Composable () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { onToggleExpand() }
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = "$title Icon",
            modifier = Modifier.size(20.dp),
            tint = Color(0xFF0066CC)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333),
            modifier = Modifier.alignByBaseline()
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = if (isExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
            contentDescription = if (isExpanded) "Collapse" else "Expand"
        )
    }
    if (isExpanded) {
        Column(modifier = Modifier.padding(start = 28.dp)) {
            content()
        }
    }
}
