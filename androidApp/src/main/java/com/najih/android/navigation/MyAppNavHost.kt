import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.najih.android.api.CreateHttpClient
import com.najih.android.ui.ContactUsForm
import com.najih.android.ui.StreamsLessons.Streams
import com.najih.android.ui.StreamsLessons.Teachers
import com.najih.android.ui.auth.SignIn
import com.najih.android.ui.auth.SignUp
import com.najih.android.ui.exams.ExamPaper
import com.najih.android.ui.exams.Exams
import com.najih.android.ui.homePage.HomePage
import com.najih.android.ui.recordedLessons.Lessons
import com.najih.android.ui.subjects.Subjects
import io.ktor.client.engine.android.Android

@Composable
fun MyAppNavHost() {
    val navController = rememberNavController()
    val httpClient = CreateHttpClient(Android)
    val context = LocalContext.current

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
        composable("contact_us") {
            ContactUsForm(navController)
        }
        composable("subjects/{type}/{endpoint}") { backStackEntry ->
            val type = backStackEntry.arguments?.getString("type")
            val endpoint = backStackEntry.arguments?.getString("endpoint")
            if (type != null && endpoint != null) {
                Subjects(navController, type, endpoint)
            }
        }

        composable("subject_lessons/{subjectId}") { backStackEntry ->
            val subjectId = backStackEntry.arguments?.getString("subjectId")
            if (subjectId != null) {
                Lessons(navController, subjectId )
            }
        }
        composable("subject_teachers/{subjectId}") { backStackEntry ->
            val subjectId = backStackEntry.arguments?.getString("subjectId")
            if (subjectId != null) {
                Teachers(navController, subjectId )
            }
        }
        composable("streams/{subjectId}/{teacherId}") { backStackEntry ->
            val subjectId = backStackEntry.arguments?.getString("subjectId")
            val teacherId = backStackEntry.arguments?.getString("teacherId")

            if (subjectId != null && teacherId != null) {
                Streams(navController, subjectId , teacherId )
            }
        }
        composable("exams") {
          Exams(navController = navController, httpClient  , context  )
        }
        composable("exam_paper/{examId}") { backStackEntry ->
            val examId = backStackEntry.arguments?.getString("examId") ?: return@composable
            ExamPaper(navController, httpClient, context,examId )
        }

    }
}
