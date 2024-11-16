package com.najih.android.ui.exams

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.najih.android.R


@Composable
fun ExamBottomNavBar(
    onReviewClick: () -> Unit,
    onSubmitClick: () -> Unit,
    onNextClick: () -> Unit
) {
    BottomAppBar(
        containerColor = Color.White,
        contentColor = Color.Black, // This sets the default content color
        modifier = Modifier.border(BorderStroke(1.dp, color = colorResource(id = R.color.secondColor)), shape = RectangleShape)
    ) {
        // Review Button
        IconButton(onClick = onReviewClick, modifier = Modifier.weight(1f)) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(id = R.drawable.review),
                    contentDescription = stringResource(id = R.string.review_content_description),
                    modifier = Modifier.size(20.dp),
                    tint = Color.Unspecified
                )
                Text(
                    text = stringResource(id = R.string.review_button),

                )
            }
        }

        // Submit Button
        IconButton(onClick = onSubmitClick, modifier = Modifier.weight(1f)) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(id = R.drawable.submit),
                    contentDescription = stringResource(id = R.string.submit_content_description),
                    modifier = Modifier.size(20.dp),
                    tint = Color.Unspecified
                )
                Text(
                    text = stringResource(id = R.string.submit_button),

                )
            }
        }

        // Next Button
        IconButton(onClick = onNextClick, modifier = Modifier.weight(1f)) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(id = R.drawable.next_question),
                    contentDescription = stringResource(id = R.string.next_content_description),
                    modifier = Modifier.size(20.dp),
                    tint = Color.Unspecified
                )
                Text(
                    text = stringResource(id = R.string.next_button),

                )
            }
        }
    }
}
