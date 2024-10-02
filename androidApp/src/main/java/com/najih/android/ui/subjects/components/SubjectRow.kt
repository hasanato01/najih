package com.najih.android.ui.subjects.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun SubjectRow(navController: NavController, subjects: List<Pair<String, String>>, endPoint: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)  // Space between rows
    ) {
        subjects.forEach { (subjectName, subjectId) ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center // Center button horizontally
            ) {
                SubjectButton(navController, subjectName, subjectId, endPoint, Modifier.fillMaxWidth())
            }
        }
    }
}

