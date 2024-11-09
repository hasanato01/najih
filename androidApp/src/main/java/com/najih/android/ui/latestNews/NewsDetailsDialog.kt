package com.najih.android.ui.latestNews

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.najih.android.dataClasses.NewsItem

@Composable
fun NewsDetailsDialog(
    newsItem: NewsItem,
    currentLanguage: String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(if (currentLanguage == "ar") newsItem.title.ar else newsItem.title.en)
        },
        text = {
            Column {
                Text(if (currentLanguage == "ar") newsItem.des.ar else newsItem.des.en)
                Image(
                    painter = rememberAsyncImagePainter(newsItem.image.url.replace("http://", "https://")),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}
