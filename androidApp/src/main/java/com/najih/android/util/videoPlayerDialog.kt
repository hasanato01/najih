package com.najih.android.util

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.najih.android.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoPlayerDialog(
    videoUrl: String,
    onDismiss: () -> Unit,
    height: Dp = 500.dp
) {
    Log.d("ApiClient",videoUrl)
    BasicAlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier.fillMaxWidth(),
        content = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height)
            ) {
                VideoPlayer(videoUrl = videoUrl)

                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp).size(30.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.exit),
                        contentDescription = "Close Player",
                        tint = Color.Unspecified
                    )
                }
            }
        }
    )
}
