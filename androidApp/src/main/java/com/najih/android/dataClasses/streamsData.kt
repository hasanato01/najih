package com.najih.android.dataClasses

import LanguageContent
import Lesson
import OtherPrice
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class StreamsInfo(
    val subject : StreamsSubjects,
    val lessons : List<Streams>,
    val teacher: Teacher

)
@Serializable
data class StreamsSubjects(
    @SerialName("_id") val id: String,
    val name: LanguageContent,
    val level: LanguageContent,
    val lessonCount: Int,
    val startDate: String,
    val endDate: String,
    val availableSeats: Int,
    val remainingSeats: Int,
    val lessonDuration: String,
    val lessonPrice: String,
    val type: LanguageContent,
    val paymentMethod: String,
    val lessonPriceAll: String,
    @SerialName("class") val classNumber: Int,
    val otherPrices: List<OtherPrice>,
    val teachersIds: List<String>,
    val createdAt: String,
    val updatedAt: String,
    @SerialName("__v") val version: Int
)
@Serializable
data class StreamsSubjectWithTeachers(
    @SerialName("_id") val id: String,
    val name: LanguageContent,
    val level: LanguageContent,
    val lessonCount: Int,
    val startDate: String,
    val endDate: String,
    val availableSeats: Int,
    val remainingSeats: Int,
    val lessonDuration: String,
    val lessonPrice: String,
    val type: LanguageContent,
    val paymentMethod: String,
    val lessonPriceAll: String,
    @SerialName("class") val classNumber: Int,
    val otherPrices: List<OtherPrice>,
    val teachersIds: List<Teacher>,
    val createdAt: String,
    val updatedAt: String,
    @SerialName("__v") val version: Int
)
@Serializable
data class Teacher(
    @SerialName("_id") val id: String,
    val name: String,
    val age: String,
    val levels: LanguageContent,
    val schoolName: String,
    val experience: String,
    val subjects: String,
    val address: String,
    val workedOnZoom: Boolean,
    val email: String,
    val phoneNumber: String,
    val status: String,
    val lessons: List<Lesson>,
    val image: TeacherImage?, // Made nullable
    val userId: String,
    val createdAt: String,
    val updatedAt: String,
    @SerialName("__v") val version: Int
)

@Serializable
data class TeacherImage(
    val filename: String?, // Made nullable
    val url: String?       // Made nullable
)

@Serializable
data class Streams(
    @SerialName("_id") val id: String,
    val name: LanguageContent,
    val description: LanguageContent,
    val startDate: String,
    val endDate: String,
    val link: String,
    val teacherId: String,
    val subjectId: String,
    val createdAt: String,
    val updatedAt: String,
    @SerialName("__v") val version: Int
)