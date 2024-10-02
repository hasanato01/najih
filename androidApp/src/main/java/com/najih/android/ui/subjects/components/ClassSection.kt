package com.najih.android.ui.subjects.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ClassSection(className: String) {
    Box(
        modifier = Modifier

            .padding(8.dp)
            .padding(top = 20.dp, bottom = 20.dp)
    ) {
        Text(
            text = className,
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Light,
            modifier = Modifier.align(Alignment.Center) // Center the text within the Box
        )
    }

}
//@Preview
//@Composable
//fun previwClassSection () {
//    ClassSection(className = "class1")
//}