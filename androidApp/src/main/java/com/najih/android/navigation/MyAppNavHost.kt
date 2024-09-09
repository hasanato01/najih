import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.najih.android.ui.auth.SignIn
import com.najih.android.ui.auth.SignUp
import com.najih.android.ui.homePage.HomePage
import com.najih.android.ui.recordedLessons.Lessons
import com.najih.android.ui.recordedLessons.RecordedLessons
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@Composable
fun MyAppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "Home_page"
    ) {
        composable("Home_page") {
            HomePage(navController)
        }
        composable("sign_in") {
            SignIn(navController)
        }
        composable("sign_up") {
            SignUp(navController)
        }
        composable("subjects/{serializedSubjects}") { backStackEntry ->
            val serializedSubjects = backStackEntry.arguments?.getString("serializedSubjects")
            val subjects = serializedSubjects?.let {
                Json.decodeFromString<List<GetSubjectsResponse>>(Uri.decode(it))
            } ?: emptyList()
            RecordedLessons(navController, resultObjects = subjects)
        }
        composable("subject_lessons/{serializedSubjectInfo}") { backStackEntry ->
            val serializedSubjectInfo = backStackEntry.arguments?.getString("serializedSubjectInfo")
            val subjectInfo = serializedSubjectInfo?.let {
                try {
                    Json.decodeFromString<GetSubjectLessons>(Uri.decode(it))
                } catch (e: Exception) {

                    null
                }
            }
            Lessons(navController, subjectInfo = subjectInfo)
        }

    }
}
