package com.najih.android.dataClasses

import LanguageContent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsItem(
    @SerialName("_id") val id: String,
    val title:  LanguageContent,
    val des: LanguageContent,
    val image: NewsImage,
    val homePage: Boolean,
    val createdAt: String,
    val updatedAt: String,
    @SerialName("__v") val version: Int
)



@Serializable
data class NewsImage(
    val filename: String,
    val url: String,
    val message: String,
    val status: Int
)
