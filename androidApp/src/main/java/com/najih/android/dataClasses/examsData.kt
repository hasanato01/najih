package com.najih.android.dataClasses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Exam(
    @SerialName("_id") val id: String,
    val name: Name,
    val describtion: Description,
    val time: Int,
    val questions: List<Question>,
    val createdAt: String,
    val updatedAt: String,
    @SerialName("__v") val version: Int
)

@Serializable
data class Name(
    val en: String,
    val ar: String
)

@Serializable
data class Description(
    val en: String,
    val ar: String
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

data class Answer(
    val questionIndex: Int,
    val selectedOption: Char
)

