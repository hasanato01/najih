package com.najih.android.ui.uitilis

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.najih.android.R
import com.najih.android.util.GlobalFunctions

@Composable
fun LanguageSelectionScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FFFE))
            .padding(horizontal = 25.dp, vertical = 47.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "Please Select Language",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 12.dp)
        )

        Spacer(modifier = Modifier.height(53.dp))


        LanguageOption(navController , languageName ="en" , flagResourceId = R.drawable.uk)
        LanguageOption(navController , languageName ="ar" , flagResourceId = R.drawable.egypt)

    }
}
@Composable
fun LanguageOption(navController: NavController,languageName: String, flagResourceId: Int) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(100.dp))
            .background(Color.White)
            .border(1.dp, Color(0x2B000000), RoundedCornerShape(100.dp))
            .padding(6.dp, 8.dp)
            .clickable {
                GlobalFunctions.saveUserLanguage(context, languageName)
                GlobalFunctions.setFirstTimeUser(context, false)
                navController.navigate("Home_page") {
                    popUpTo("LanguageSelection") { inclusive = true }
                }
            },
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            painter = painterResource(id = flagResourceId),
            contentDescription = "$languageName flag",
            modifier = Modifier.size(51.dp)
        )

        Spacer(modifier = Modifier.width(44.dp))

        Text(
            text = languageName,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}