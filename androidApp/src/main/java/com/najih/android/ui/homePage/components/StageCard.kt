package com.najih.android.ui.homePage.components


import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.najih.android.R
import com.najih.android.api.CreateHttpClient
import com.najih.android.api.globalData.RECORDED_SUBJECTS_ENDPOINT
import com.najih.android.api.globalData.STREAMS_SUBJECTS_ENDPOINT
import com.najih.android.util.GlobalFunctions
import io.ktor.client.engine.android.Android
import kotlinx.coroutines.launch


@Composable
fun StageCard(
    stage: String,
    type: String,
    modifier: Modifier, // Pass modifier here
    navController: NavController
) {

    Card(
        modifier = modifier
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .background(Color.White)
            .padding(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(0.dp)

    ) {
            Text(
                text = stage,
                fontSize = 16.sp,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                color = Color.Black,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(33.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Image(
                    painter = painterResource(id = R.drawable.play),
                    contentDescription = "$stage image 1",
                    modifier = Modifier.size(37.dp) .clickable {
                        navController.navigate("subjects/$type/$STREAMS_SUBJECTS_ENDPOINT")
                    }
                )
                Image(
                    painter = painterResource(id = R.drawable.play),
                    contentDescription = "$stage image 2",
                    modifier = Modifier
                        .size(33.dp)
                        .clickable {
                            navController.navigate("subjects/$type/$RECORDED_SUBJECTS_ENDPOINT")
                        }
                )
            }
        }

}

@Preview
@Composable
fun StageCardPreview() {
    val navController = rememberNavController() // Correcting to rememberNavController
    val modifier = Modifier // Ensure Modifier is initialized
    StageCard(stage = "High School", type = "sss", modifier = modifier, navController = navController)
}
