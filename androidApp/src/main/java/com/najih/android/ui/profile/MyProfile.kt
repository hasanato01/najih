import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import androidx.navigation.NavController
import com.najih.android.ui.uitilis.BottomNavBar
import com.najih.android.ui.uitilis.navbar
import com.najih.android.util.GlobalFunctions

@Composable
fun MyProfile(
    navController: NavController,

) {
    val context = LocalContext.current
    val userInfo by remember { mutableStateOf(GlobalFunctions.getUserInfo(context)) }
    val isLoggedIn = userInfo.userId.isNotEmpty() && userInfo.token.isNotEmpty()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            Image(
                painter = rememberAsyncImagePainter(model = "https://via.placeholder.com/150"), // Placeholder image
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentScale = ContentScale.Crop
            )

            // User Info
            Text(
                text = userInfo.userName.ifEmpty { "Guest user" },
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )

            Text(
                text = userInfo.userEmail.ifEmpty { "No Email Available" },
                fontSize = 16.sp,
                color = Color.Gray
            )


            Spacer(modifier = Modifier.height(20.dp))

            // My Exams Button
            Button(
                onClick = { navController.navigate("user_exams") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary)
            ) {
                Text("My Exams", fontSize = 18.sp)
            }

            // Logout Button
            Button(
                onClick = { "" },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(Color.Red)
            ) {
                Text("Logout", fontSize = 18.sp, color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyProfilePreview() {
    val context = LocalContext.current
    MyProfile(
        navController = NavController(context),
    )
}
