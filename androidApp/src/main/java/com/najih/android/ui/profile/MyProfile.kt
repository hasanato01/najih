import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import androidx.navigation.NavController
import com.najih.android.R
import com.najih.android.ui.uitilis.BottomNavBar
import com.najih.android.ui.uitilis.HomeNavbar
import com.najih.android.ui.uitilis.Navbar

import com.najih.android.util.GlobalFunctions


@Composable
fun MyProfile(navController: NavController) {
    val context = LocalContext.current
    var userInfo by remember { mutableStateOf(GlobalFunctions.getUserInfo(context)) }
    val isLoggedIn = userInfo.userId.isNotEmpty() && userInfo.token.isNotEmpty()
    val userName = userInfo.userName
    // Make currentLanguage a mutable state
    var currentLanguage by remember { mutableStateOf(GlobalFunctions.getUserLanguage(context)) }

    // Use a derived state for the language icon and text
    val languageIcon = if (currentLanguage == "en") R.drawable.uk else R.drawable.emirates
    val languageText = if (currentLanguage == "en") "English" else "Arabic"

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { Navbar(navController  , backText = userName , titleText = "My Profile" ) },
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
            ) {
                // My Exams Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .clickable { navController.navigate("user_exams") },
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(5.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.List,
                            contentDescription = "My Exams",
                            modifier = Modifier.padding(end = 8.dp),
                            tint = Color.Gray
                        )
                        Text("My Exams", fontSize = 18.sp)
                    }
                }

                // Log Out Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .clickable {
                            GlobalFunctions.clearUserInfo(context)
                            userInfo = GlobalFunctions.getUserInfo(context)
                            navController.navigate("home_page")
                        },
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(5.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Log out",
                            modifier = Modifier.padding(end = 8.dp),
                            tint = Color.Gray
                        )
                        Text("Log out", fontSize = 18.sp)
                    }
                }

                // Language Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .clickable {
                            // Toggle language and save it
                            if (currentLanguage == "en") {
                                GlobalFunctions.saveUserLanguage(context, "ar")
                                currentLanguage = "ar" // Update current language
                            } else {
                                GlobalFunctions.saveUserLanguage(context, "en")
                                currentLanguage = "en" // Update current language
                            }
                        },
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(5.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row {
                            Icon(
                                painter = painterResource(languageIcon),
                                contentDescription = "Language",
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .height(20.dp),
                                tint = Color.Unspecified
                            )
                            Text(languageText, fontSize = 18.sp)
                        }
                        // Change Icon on the right
                        Icon(
                            imageVector = Icons.Default.Refresh, // Change icon
                            contentDescription = "Change Language",
                            tint = Color.Gray // Set icon color
                        )
                    }
                }
            }
        }
    }
}
