package com.najih.android.util

import android.content.Context
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import GetSubjectsResponse


object  GlobalFunctions {
    // Function to save the access token
    fun saveAccessToken(context: Context, token: String) {
        val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("ACCESS_TOKEN", token)
        editor.apply()
    }

    // Function to get the access token
    fun getAccessToken(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("ACCESS_TOKEN", null)
    }

    fun serializeSubjects(subjects: List<GetSubjectsResponse>): String {
        return Json.encodeToString(subjects)
    }


}

