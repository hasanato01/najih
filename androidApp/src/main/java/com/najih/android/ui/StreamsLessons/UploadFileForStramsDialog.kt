package com.najih.android.ui.StreamsLessons

import Lesson
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.najih.android.R
import com.najih.android.api.purchasedLessons.savePurchasedLessons
import com.najih.android.api.purchasedStreams.savePurchasedStreams
import com.najih.android.dataClasses.PurchaseResponse
import com.najih.android.dataClasses.Streams
import com.najih.android.util.uriToFile
import io.ktor.client.HttpClient
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun UploadFileForStreamsDialog(
    onDismiss: () -> Unit,
    purchasedLessons: SnapshotStateMap<String, MutableList<String>>,
    recorderLessonsIds: SnapshotStateList<String>,
    recorderLessons: SnapshotStateList<Streams>,
    subjectId : String,
    teacherId : String,
    subjectNameAR : String,
    subjectClass : String,
    lessonsPrice : Double,
    context: Context,
    httpClient: HttpClient
) {
    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }
    var selectedFile by remember { mutableStateOf<File?>(null) }
    var purchaseResponse: PurchaseResponse? by remember { mutableStateOf(null) }
    var showDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            Log.d("ApiClient", "Selected URI: $uri") // Log the URI
            selectedFileUri = uri
            if (uri != null) {
                val file = uriToFile(uri, context)
                if (file != null) {
                    if (file.length() > 1024 * 1024) { // Check if file is > 1MB
                        selectedFile = null
                        selectedFileUri = null
                        Toast.makeText(context, "File too large! Must be under 1MB.", Toast.LENGTH_LONG).show()
                        Log.e("ApiClient", "File too large: ${file.length()} bytes")
                    } else {
                        selectedFile = file
                        Log.d("ApiClient", "File selected: ${file.length()} bytes")
                    }
                }
            } else {
                Log.e("ApiClient", "URI is null")
            }
        }
    )


    AlertDialog(
        onDismissRequest = onDismiss,
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Upload Button
                Button(
                    onClick = { filePickerLauncher.launch(arrayOf("application/pdf", "image/*")) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_cloud_upload_24),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = stringResource(id = R.string.upload_image_pdf))
                }

                // Selected File Display
                if (selectedFileUri != null) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp), // Added padding to avoid layout issues
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.LightGray.copy(alpha = 0.3f))
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_description_24),
                                contentDescription = null
                            )
                            Text(
                                text = selectedFileUri.toString(),
                                fontSize = 14.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    selectedFile?.let { file ->
                        coroutineScope.launch {
                            purchaseResponse = savePurchasedStreams(
                                httpClient = httpClient,
                                context = context,
                                purchasedLessons = purchasedLessons,
                                recorderLessonsIds = recorderLessonsIds,
                                recorderLessons = recorderLessons,
                                subjectId,
                                teacherId,
                                subjectNameAR,
                                subjectClass,
                                lessonsPrice,
                                file = file
                            )
                            showDialog = true
                        }
                    }
                },
                modifier = Modifier
                    .padding(10.dp) // Padding must be before fillMaxWidth()
                    .fillMaxWidth(),
                enabled = selectedFileUri != null
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_check_24),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = stringResource(id = R.string.submit))
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_cancel_24),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = stringResource(id = R.string.cancel))
            }
        }
    )
    // Show Purchase Dialog if there is a response
    if (showDialog) {
        PurchaseDialog(
            onDismiss = { showDialog = false },
            response = purchaseResponse
        )
    }
}

@Composable
fun PurchaseDialog(
    onDismiss: () -> Unit,
    response: PurchaseResponse?
) {
    response?.let {
        val isSuccess = it.status == "success"
        val iconRes = if (isSuccess) {
            R.drawable.success_checkmark // Success icon
        } else {
            R.drawable.exit // Error icon
        }

        // Load the icon using painterResource
        val painter = painterResource(id = iconRes)
        val iconColor = if (isSuccess) Color.Green else Color.Red
        val message = if (isSuccess) stringResource(R.string.purchase_successful) else stringResource(
            R.string.purchase_failed_please_try_again
        )

        AlertDialog(
            onDismissRequest = onDismiss,
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painter,
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = message)
                }
            },
            confirmButton = {
                Button(modifier = Modifier.fillMaxWidth(),onClick = onDismiss) {
                    Text(text = stringResource(R.string.ok))
                }
            }
        )
    }
}