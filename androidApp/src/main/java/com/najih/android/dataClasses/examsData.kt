package com.najih.android.dataClasses

import LanguageContent
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


@Serializable
data class Result(
    val question: Question,
    val userAnswer: String,
    val correctAnswer: String,
    val isCorrect: Boolean,
    @SerialName("_id") val id: String,
)
@Serializable
data class ExamResults(
    val results: List<Result>
)
@Serializable
data class SubmittedExamResponse(
    val status: String
)
@Serializable
data class SubmitExamRequest(
    val examId: String,
    val userId: String,
    val results: ExamResults,
    val correctAnswersCount: Int,
    val examName: LanguageContent,
    val totalQuestions: Int,
    val submittedAt: String
)


data class QuestionResult(
    val question: Map<String, Boolean>,
    val userAnswer: String,
    val correctAnswer: String,
    val isCorrect: Boolean,
    val _id: String
)
data class ImageData(val filename: String, val url: String)
