package com.najih.android.dataClasses

import kotlinx.serialization.Serializable

    @Serializable
    data class SignInRequest(val email: String, val password: String)

    @Serializable
    data class SignInResponse(
        val success: Boolean,
        val status: String,
        val accessToken: String,
        val user: User
    )

    @Serializable
    data class User(
        val _id: String,
        val name: String,
        val role: String,
        val verificationToken: String,
        val verification: Boolean,
        val username: String,
        val teacherId: String,
        val submitedExams: List<String>,
        val recorderLessonsIds: List<String>,
        val teachersLessonsIds: List<String>,
        val salt: String,
        val hash: String,
        val createdAt: String,
        val updatedAt: String,
        val __v: Int
    )

    @Serializable
    data class SignUpRequest(val name: String, val phone: String, val email: String, val password: String , val role : String)

    @Serializable
    data class SignUpResponse(val token: String, val userId: String)

data class UserInfo(
    val token: String?,
    val userName: String?,
    val userEmail: String?
)
