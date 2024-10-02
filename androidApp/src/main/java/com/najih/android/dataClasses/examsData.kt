package com.najih.android.dataClasses

import LanguageContent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Exam(
    @SerialName("_id") val id: String,
    val name: LanguageContent,
    val describtion: LanguageContent,
    val time: Int,
    val questions: List<Question>,
    val createdAt: String,
    val updatedAt: String,
    @SerialName("__v") val version: Int
)

@Serializable
data class Question(
    val A: Boolean,
    val B: Boolean,
    val C: Boolean,
    val D: Boolean,
    val image: Image
)

@Serializable
data class Image(
    val filename: String,
    val url: String,
    val message: String,
    val status: Int
)






