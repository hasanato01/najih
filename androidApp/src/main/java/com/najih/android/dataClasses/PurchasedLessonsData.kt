package com.najih.android.dataClasses

import LanguageContent
import Lesson
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PurchaseRequest(
    val source : String,
    val purchasedLessons: Map<String, List<String>>,
    val recorderLessonsIds: List<String>,
    val bill: Bill,
    val userName: String,
    val userEmail: String,
    val recorderLessons: List<Lesson>,
    val status: String,
    val price: Int,
    val lessonsPrice : Double,
    val subjectName: String,
    val subjectClass: String
)
@Serializable
data class StreamsPurchaseRequest(
    val source : String,
    val purchasedLessons: Map<String, List<String>>,
    val teachersLessonsIds: List<String>,
    val bill: Bill,
    val userName: String,
    val userEmail: String,
    val teachersLessons: List<Streams>,
    val status: String,
    val price: Int,
    val lessonsPrice : Double,
    val subjectId : String,
    val teacherId : String,
    val userId : String,
    val subjectName: String,
    val subjectClass: String
)


@Serializable
data class Bill(
    val filename: String,
    val url: String,
    val message: String,
    val status: Int? = null
)


@Serializable
data class PurchaseResponse(
    val status: String,

)
@Serializable
data class MyRecorderLessonsResponse(
    val isFree: Boolean,
    @SerialName("_id") val id: String,
    val name: LanguageContent,
    val description: LanguageContent,
    val startDate: String,
    val endDate: String,
    val link: String,
    val exLink: String,
    val subjectId: String,
    val createdAt: String,
    val updatedAt: String,
    @SerialName("__v") val version: Int
)