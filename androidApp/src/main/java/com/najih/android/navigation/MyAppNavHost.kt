import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.najih.android.api.CreateHttpClient
import com.najih.android.api.globalData.GlobalData
import com.najih.android.dataClasses.Exam
import com.najih.android.ui.ContactUsForm
import com.najih.android.ui.auth.SignIn
import com.najih.android.ui.auth.SignUp
import com.najih.android.ui.exams.ExamPaper
import com.najih.android.ui.exams.Exams
import com.najih.android.ui.homePage.HomePage
import com.najih.android.ui.recordedLessons.Lessons
import com.najih.android.ui.recordedLessons.RecordedLessons
import io.ktor.client.engine.android.Android
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

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
        composable("exams") {
          Exams(navController = navController, httpClient  , context  )
        }
        composable("exam_paper/{examId}") { backStackEntry ->
            val examId = backStackEntry.arguments?.getString("examId") ?: return@composable
            ExamPaper(navController, httpClient, context,examId )
        }

    }
}
