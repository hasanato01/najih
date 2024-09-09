package com.najih.android.ui.homePage.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
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

    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
        stages.chunked(2).forEach { pair ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp) // Space between the two StageCards
            ) {
                pair.forEach { stage ->
                    StageCard(
                        stage = stage.stage,
                        type = stage.type,
                        modifier = Modifier
                            .weight(1f) // Ensure each StageCard takes equal space
                            .height(150.dp) // Adjust height as needed
                            .padding(4.dp),
                        navController = navController
                    )
                }

                // If there is only one item in this pair, add an empty space to fill the row
                if (pair.size < 2) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}



@Preview
@Composable
fun StagesPreview() {
    val navController = rememberNavController()
    Stages(navController)
}
