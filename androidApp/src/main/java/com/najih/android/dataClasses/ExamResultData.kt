package com.najih.android.dataClasses

import LanguageContent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


data class UserExamResult(
    val examId: String,
    val userId: String,
    val results: List<QuestionResultData>,
    val correctAnswersCount: Int,
    val examName: LanguageContent,
    val totalQuestions: Int,
    val submittedAt: String
)

@Serializable
data class QuestionResultData(
    val question:QuestionResult ,
    val userAnswer: String,
    val correctAnswer: String,
    val isCorrect: Boolean,
    )
@Serializable
data class QuestionResult(
    val A: Boolean,
    val B: Boolean,
    val C: Boolean,
    val D: Boolean,
    val image: ResultImage
)
@Serializable
data class ResultImage(
    val filename: String,
    val url: String,

)
data class Answer(
    val questionIndex: Int,
    val selectedOption: Char
)

@Serializable
data class SubmittedExamResponse(
    val status: String
)
@Serializable
data class SubmitExamRequest(
    @SerialName("_id") val id: String? = null,
    val examId: String,
    val userId: String,
    val results: List<QuestionResultData>,
    val correctAnswersCount: Int,
    val examName: LanguageContent,
    val totalQuestions: Int,
    val submittedAt: String
)

