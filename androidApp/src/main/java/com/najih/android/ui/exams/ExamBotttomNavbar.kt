package com.najih.android.ui.exams

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.najih.android.R


@Composable
fun ExamBottomNavBar(
    onReviewClick: () -> Unit,
    onSubmitClick: () -> Unit,
    onNextClick: () -> Unit
) {
    BottomAppBar(
        containerColor = Color.White,
        contentColor = Color.Black,
        modifier = Modifier
    ) {
        IconButton(onClick = onReviewClick, modifier = Modifier.weight(1f)) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Filled.Create, contentDescription = stringResource(id = R.string.review_content_description))
                Text(text = stringResource(id = R.string.review_button))
            }
        }

        IconButton(onClick = onSubmitClick, modifier = Modifier.weight(1f)) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Filled.Check, contentDescription = stringResource(id = R.string.submit_content_description))
                Text(text = stringResource(id = R.string.submit_button))
            }
        }

        IconButton(onClick = onNextClick, modifier = Modifier.weight(1f)) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Filled.ArrowForward, contentDescription = stringResource(id = R.string.next_content_description))
                Text(text = stringResource(id = R.string.next_button))
            }

        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewExamBottomNavBar() {
    MaterialTheme {
        ExamBottomNavBar(
            onReviewClick = {},
            onSubmitClick = {},
            onNextClick = {}
        )
    }
}