package com.najih.android.dataClasses

import LanguageContent
import Lesson
import kotlinx.serialization.Serializable

@Serializable
data class PurchaseRequest(
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
    val purchasedLessons: Map<String, List<String>>,
    val recorderLessonsIds: List<String>,
    val bill: Bill,
    val userName: String,
    val userEmail: String,
    val recorderLessons: List<Streams>,
    val status: String,
    val price: Int
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