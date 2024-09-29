package com.najih.android.util

import android.content.Context
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import GetSubjectsResponse
import android.provider.ContactsContract.CommonDataKinds.Email
import com.najih.android.dataClasses.UserInfo


object  GlobalFunctions {
    // Function to save user info
    fun saveUserInfo(context: Context, token: String,userId : String, userName: String, userEmail: String) {
        val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        // Save each piece of information
        editor.putString("USER_ID", userId)
        editor.putString("ACCESS_TOKEN", token)
        editor.putString("USER_NAME", userName)
        editor.putString("USER_EMAIL", userEmail)
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

        // Return an instance of UserInfo with the retrieved values
        return UserInfo(
            token = token ?: "",
            userId = userId ?: "",
            userName = userName ?: "",
            userEmail = userEmail ?: ""
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


    fun serializeSubjects(subjects: List<GetSubjectsResponse>): String {
        return Json.encodeToString(subjects)
    }


}

