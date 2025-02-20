package com.najih.android.ui.recordedLessons

import Lesson
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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
import com.najih.android.R
import com.najih.android.api.auth.checkJWT
import com.najih.android.api.purchasedLessons.savePurchasedLessons
import com.najih.android.dataClasses.PurchaseResponse
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
                    if (file.length() > 1024 * 1024) { // 1MB limit
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
        title = { Text(text = stringResource(id = R.string.upload_file)) },
        text = {
            Column {
                Button(
                    onClick = {
                        filePickerLauncher.launch(arrayOf("application/pdf", "image/*"))
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(id = R.string.upload_image_pdf))
                }

                selectedFileUri?.let {
                    Text(text = stringResource(R.string.selected_file, it), modifier = Modifier.padding(top = 8.dp))
                }
            }
        },
        confirmButton = {
            Button(
                modifier = Modifier.padding(10.dp),
                onClick = {
                    selectedFile?.let { file ->
                        coroutineScope.launch {
                             purchaseResponse = savePurchasedLessons(
                                httpClient = httpClient,
                                context = context,
                                purchasedLessons = purchasedLessons,
                                recorderLessonsIds = recorderLessonsIds,
                                recorderLessons = recorderLessons,
                                 subjectNameAR,
                                 subjectClass,
                                 lessonsPrice,
                                file = file
                            )

                            showDialog = true
                        }
                    }
                },
                enabled = selectedFileUri != null // Enable only if a file is selected
            ) {
                Text(text = stringResource(id = R.string.submit))
            }
        },
        dismissButton = {
            Button(onClick = onDismiss, modifier = Modifier.padding(10.dp)) {
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