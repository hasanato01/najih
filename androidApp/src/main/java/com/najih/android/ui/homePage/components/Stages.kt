package com.najih.android.ui.homePage.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.najih.android.dataClasses.Stage

@Composable
fun Stages(navController: NavController) {
    val stages = listOf(
        Stage(stage = "HighSchool", type = "High School"),
        Stage(stage = "Preparatory", type = "Preparatory"),
        Stage(stage = "Primary", type = "Primary"),
        Stage(stage = "Kindergarten1", type = "Kindergarten 1"),
        Stage(stage = "Kindergarten2", type = "Kindergarten 2")
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {

            stages.chunked(2).forEach { rowItems ->
                // Correctly display two cards in a row
                rowItems.forEach { stage ->
                    StageCard(
                        stage = stage.stage,
                        type = stage.type,
                        modifier = Modifier.weight(1f),
                        navController = navController
                    )

            }
        }
    }
}
@Preview
@Composable
fun StagesPreview(){
//    Stages()
}