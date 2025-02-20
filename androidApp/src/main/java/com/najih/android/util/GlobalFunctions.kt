package com.najih.android.util

import android.content.Context
import android.content.res.Configuration
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Locale
import com.najih.android.dataClasses.UserInfo


object  GlobalFunctions {
    fun saveUserInfo(
        context: Context,
        token: String,
        userId: String,
        userName: String,
        userEmail: String,
        purchasedLessons: List<Map<String, List<String>>>,
        recorderLessonsIds: List<String>,
        teachersLessonsIds: List<String>
    ) {
        val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val gson = Gson()

        editor.putString("USER_ID", userId)
        editor.putString("ACCESS_TOKEN", token)
        editor.putString("USER_NAME", userName)
        editor.putString("USER_EMAIL", userEmail)

        // Save JSON Strings
        editor.putString("PURCHASED_LESSONS", gson.toJson(purchasedLessons))
        editor.putString("RECORDER_LESSONS_IDS", gson.toJson(recorderLessonsIds))
        editor.putString("TEACHERS_LESSONS_IDS", gson.toJson(teachersLessonsIds))

        editor.apply()
    }


    // Function to get user info
    fun getUserInfo(context: Context): UserInfo {
        val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)

        val userId = sharedPreferences.getString("USER_ID", null) ?: ""
        val token = sharedPreferences.getString("ACCESS_TOKEN", null) ?: ""
        val userName = sharedPreferences.getString("USER_NAME", null) ?: ""
        val userEmail = sharedPreferences.getString("USER_EMAIL", null) ?: ""

        val gson = Gson()

        // Retrieve and parse JSON arrays safely
        val purchasedLessonsJson = sharedPreferences.getString("PURCHASED_LESSONS", "[]")
        val purchasedLessons: List<Map<String, List<String>>> = try {
            gson.fromJson(purchasedLessonsJson, object : TypeToken<List<Map<String, List<String>>>>() {}.type) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }

        val recorderLessonsJson = sharedPreferences.getString("RECORDER_LESSONS_IDS", "[]")
        val recorderLessonsIds: List<String> = try {
            gson.fromJson(recorderLessonsJson, object : TypeToken<List<String>>() {}.type) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }

        val teachersLessonsJson = sharedPreferences.getString("TEACHERS_LESSONS_IDS", "[]")
        val teachersLessonsIds: List<String> = try {
            gson.fromJson(teachersLessonsJson, object : TypeToken<List<String>>() {}.type) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }

        return UserInfo(
            token = token,
            userId = userId,
            userName = userName,
            userEmail = userEmail,
            purchasedLessons = purchasedLessons,
            recorderLessonsIds = recorderLessonsIds,
            teachersLessonsIds = teachersLessonsIds
        )
    }

    fun clearUserInfo(context: Context) {
        val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        // Remove user information
        editor.remove("USER_ID")
        editor.remove("ACCESS_TOKEN")
        editor.remove("USER_NAME")
        editor.remove("USER_EMAIL")
        editor.apply() // Apply changes asynchronously
    }

    // Function to save the language selection and mark the user as not first-time
    fun setFirstTimeUser(context: Context, isFirstTime: Boolean) {
        val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("IS_FIRST_TIME_USER", isFirstTime)
        editor.apply()
    }

    // Function to save the selected language
    fun saveUserLanguage(context: Context, language: String) {
        val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("USER_LANGUAGE", language)
        editor.apply()
    }

    // Function to retrieve the saved language
    fun getUserLanguage(context: Context): String? {
    val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
    return sharedPreferences.getString("USER_LANGUAGE", "en") // Default language is English ("en")
}
    fun setAppLocale(context: Context, languageCode: String): Context {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val resources = context.resources
        val configuration = Configuration(resources.configuration)
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
        // Create a new context with the updated configuration
        return context.createConfigurationContext(configuration)
    }

    fun saveUserCredentials(context: Context, email: String, password: String) {
        val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("SAVED_EMAIL", email)
        editor.putString("SAVED_PASSWORD", password)
        editor.putBoolean("REMEMBER_ME", true) // Save the remember me state
        editor.apply()
    }

    fun getUserCredentials(context: Context): Pair<String?, String?> {
        val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("SAVED_EMAIL", null)
        val password = sharedPreferences.getString("SAVED_PASSWORD", null)
        return Pair(email, password)
    }

    fun isRememberMeEnabled(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("REMEMBER_ME", false)
    }

    fun clearUserCredentials(context: Context) {
        val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("SAVED_EMAIL")
        editor.remove("SAVED_PASSWORD")
        editor.putBoolean("REMEMBER_ME", false)
        editor.apply()
    }

}

