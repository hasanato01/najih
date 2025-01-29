package com.najih.android.dataClasses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

    @Serializable
    data class SignInRequest(val email: String, val password: String)

@Serializable
data class SignInResponse(
    val success: Boolean,
    val status: String,
    val accessToken: String?=null, // Optional if not returned in error cases
    val user: User?=null,
    val err: ErrorDetails? = null // Optional field for error details
)

@Serializable
data class ErrorDetails(
    val name: String? = null,
    val message: String? = null
)
@Serializable
data class CheckJWTResponse(
    val status: String,
    val accessToken: String? = null,
    val user: refetchedUser? = null
)

@Serializable
data class refetchedUser(
    @SerialName("_id") val id: String,
    val name: String,
    val role: String,
    val verificationToken: String,
    val verification: Boolean,
    val username: String,
    val teacherId: String,
    val submitedExams: List<String>,
    val purchasedLessons: List<Map<String, List<String>>>,
    val recorderLessonsIds: List<String>,
    val teachersLessonsIds: List<String>,
    val createdAt: String,
    val updatedAt: String,
    val __v: Int,
    val teacherDetails: TeacherDetails,
    val lastResendTime: String? = null,
    val image: String? = null,
    val cv: String? = null,
    val allowedPages: List<String>? = emptyList()
)

@Serializable
data class User(
    @SerialName("_id") val id: String,
    val name: String,
    val role: String,
    val verificationToken: String? = null,
    val verification: Boolean,
    val username: String,
    val teacherId: String,
    val submitedExams: List<String>,
    val purchasedLessons: List<Map<String, List<String>>>,
    val recorderLessonsIds: List<String>,
    val teachersLessonsIds: List<String>,
    val salt: String? = null,
    val hash:  String? = null,
    val createdAt: String,
    val updatedAt: String,
    val __v: Int,
    val teacherDetails: TeacherDetails,
    val lastResendTime: String? = null,
    val image: String? = null,
    val cv: String? = null,
    val allowedPages: List<String>? = emptyList(),
    val privatesLessonsIds: List<String>,
    val courseLessonsIds: List<String>,

    )

@Serializable
data class TeacherDetails(
    val levels: List<String>
)

@Serializable
data class SignUpRequest(
    val name: String,
    val fatherName: String,
    val lastName: String,
    val gender: String,
    val nationality: String,
    val birthYear: Int,
    val schoolType: String,
    val residence: String,
    val whatsApp: String,
    val heardFrom: String,
    val email: String,
    val password: String,
    val confirmPassword: String,
    val role: String
)


@Serializable
data class SignUpResponse(
    val success: Boolean,
    val status: String,
    val userId: String
)


data class UserInfo(
    val token: String,
    val userId: String,
    val userName: String,
    val userEmail: String,
    val purchasedLessons: List<Map<String, List<String>>>
)
@Serializable
data class VerificationRequest(
    val email: String,
    val code: String
)
@Serializable
data class VerificationResponse(
    val success: Boolean? = null,
    val message: String? = null
)