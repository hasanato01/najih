package com.najih.android.ui.homePage.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.najih.android.R
import com.najih.android.dataClasses.Stage



@Composable
fun Stages(navController: NavController) {
    val stages = listOf(
        Stage(stage = stringResource(R.string.highschool), type = "High School", image = R.drawable.image_highschool),
        Stage(stage = stringResource(R.string.preparatory), type = "Preparatory", image = R.drawable.image_preparatory),
        Stage(stage = stringResource(R.string.primary), type = "Primary", image = R.drawable.image_primary),
        Stage(stage = stringResource(R.string.kindergarten), type = "Kindergarten", image = R.drawable.image_kindergarten1),
    )

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)) {
        stages.forEach { stage ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                    StageCard(
                        stage = stage.stage,
                        type = stage.type,
                        image = stage.image,
                        modifier = Modifier
                            .weight(1f)
                            .height(110.dp)
                            .padding(2.dp),
                        navController = navController
                    )
                }
            }
        }
    }


