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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.najih.android.R
import com.najih.android.dataClasses.Stage

@Composable
fun Stages(navController: NavController) {
    val stages = listOf(
        Stage(stage = stringResource(R.string.highschool), type = "High School"),
        Stage(stage = stringResource(R.string.preparatory), type = "Preparatory"),
        Stage(stage = stringResource(R.string.primary), type = "Primary"),
        Stage(stage = stringResource(R.string.kindergarten1), type = "Kindergarten 1"),
        Stage(stage = stringResource(R.string.kindergarten2), type = "Kindergarten 2")
    )

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)) {
        stages.forEach { stage ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp) // Space between the two StageCards
            ) {
                    StageCard(
                        stage = stage.stage,
                        type = stage.type,
                        modifier = Modifier
                            .weight(1f) // Ensure each StageCard takes equal space
                            .height(100.dp) // Adjust height as needed
                            .padding(4.dp),
                        navController = navController
                    )
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
