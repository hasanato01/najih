package com.najih.android.ui.examResults

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.najih.android.dataClasses.QuestionResultData

import androidx.compose.ui.res.stringResource
import com.najih.android.R

@Composable
fun QuestionResultCard(result: QuestionResultData, questionIndex: Int) {
    var isExpanded by remember { mutableStateOf(false) }

    // Display if the question was correct or not
    val isCorrectText = if (result.isCorrect) stringResource(id = R.string.correct) else stringResource(id = R.string.incorrect)
    val correctnessIcon = if (result.isCorrect) {
        Icons.Default.CheckCircle // Use a checkmark icon for correct answers
    } else {
        Icons.Default.Clear // Use a cross icon for incorrect answers
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable { isExpanded = !isExpanded },  // Toggles expanded state
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Row for Question index and correctness, with the dropdown icon
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f) // Allow this row to take available space
                ) {
                    Icon(
                        imageVector = correctnessIcon,
                        contentDescription = isCorrectText,
                        tint = if (result.isCorrect) Color.Green else Color.Red,
                        modifier = Modifier.padding(end = 8.dp) // Space between icon and text
                    )
                    Text(
                        text = stringResource(id = R.string.question_index, questionIndex),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                // Expand/Collapse Icon
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (isExpanded) stringResource(id = R.string.collapse) else stringResource(id = R.string.expand),
                    modifier = Modifier.clickable { isExpanded = !isExpanded }
                )
            }

            // Expanded content (visible when isExpanded is true)
            if (isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))

                // Conditionally display image if available
                if (result.question.image.url.isNotEmpty()) {
                    AsyncImage(
                        model = result.question.image.url,
                        contentDescription = stringResource(id = R.string.question_image),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .padding(8.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                }

                // Display answer options
                Text(text = stringResource(id = R.string.your_answer, result.userAnswer), fontSize = 14.sp)
                Text(text = stringResource(id = R.string.correct_answer, result.correctAnswer), fontSize = 14.sp)
            }
        }
    }
}
