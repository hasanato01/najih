package com.najih.android.util

import android.content.Context
import android.content.res.Configuration
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Locale
import com.najih.android.dataClasses.UserInfo


object  GlobalFunctions {
    // Function to save user info
    fun saveUserInfo(
        context: Context,
        token: String,
        userId: String,
        userName: String,
        userEmail: String,
        purchasedLessons: List<Map<String, List<String>>>
    ) {
        val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Convert the list of purchased lessons to a JSON string using Gson
        val gson = Gson()
        val purchasedLessonsJson = gson.toJson(purchasedLessons)

        // Save each piece of information
        editor.putString("USER_ID", userId)
        editor.putString("ACCESS_TOKEN", token)
        editor.putString("USER_NAME", userName)
        editor.putString("USER_EMAIL", userEmail)
        editor.putString("PURCHASED_LESSONS", purchasedLessonsJson)

        // Commit the changes
        editor.apply()
    }

    // Function to get user info
    fun getUserInfo(context: Context): UserInfo {
        val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)

        // Retrieve each piece of information
        val userId = sharedPreferences.getString("USER_ID", null)
        val token = sharedPreferences.getString("ACCESS_TOKEN", null)
        val userName = sharedPreferences.getString("USER_NAME", null)
        val userEmail = sharedPreferences.getString("USER_EMAIL", null)

        // Retrieve the purchasedLessons from JSON
        val gson = Gson()
        val purchasedLessonsJson = sharedPreferences.getString("PURCHASED_LESSONS", "")
        val purchasedLessons: List<Map<String, List<String>>> = if (purchasedLessonsJson.isNullOrEmpty()) {
            emptyList()
        } else {
            val type = object : TypeToken<List<Map<String, List<String>>>>() {}.type
            gson.fromJson(purchasedLessonsJson, type)
        }

        // Return an instance of UserInfo with the retrieved values
        return UserInfo(
            token = token ?: "",
            userId = userId ?: "",
            userName = userName ?: "",
            userEmail = userEmail ?: "",
            purchasedLessons = purchasedLessons
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

    // Function to check if it's the user's first time selecting a language
    fun isFirstTimeUser(context: Context): Boolean {
    val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean("IS_FIRST_TIME_USER", true)
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



}

