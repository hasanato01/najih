package com.najih.android.ui.recordedLessons

import Lesson
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import com.najih.android.R
import com.najih.android.api.purchasedLessons.savePurchasedLessons
import com.najih.android.util.uriToFile
import io.ktor.client.HttpClient
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun UploadFileDialog(
    onDismiss: () -> Unit,
    purchasedLessons: SnapshotStateMap<String, MutableList<String>>,
    recorderLessonsIds: SnapshotStateList<String>,
    recorderLessons: SnapshotStateList<Lesson>,
    context: Context,
    httpClient: HttpClient
) {
    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }
    var selectedFile by remember { mutableStateOf<File?>(null) }
    val coroutineScope = rememberCoroutineScope()

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            selectedFileUri = uri
            selectedFile = uri?.let { uriToFile(it, context) }
        }
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(id = R.string.upload_file)) },
        text = {
            Column {
                Text(text = stringResource(id = R.string.upload_prompt))
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        filePickerLauncher.launch(arrayOf("application/pdf", "image/*"))
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(id = R.string.upload_image_pdf))
                }

                selectedFileUri?.let {
                    Text(text = "Selected file: $it", modifier = Modifier.padding(top = 8.dp))
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    selectedFile?.let { file ->
                        coroutineScope.launch {
                            savePurchasedLessons(
                                httpClient = httpClient,
                                context = context,
                                purchasedLessons = purchasedLessons,
                                recorderLessonsIds = recorderLessonsIds,
                                recorderLessons = recorderLessons,
                                file = file
                            )
                        }
                    }
                },
                enabled = selectedFileUri != null // Enable only if a file is selected
            ) {
                Text(text = stringResource(id = R.string.submit))
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(text = stringResource(id = R.string.cancel))
            }
        }
    )
}
