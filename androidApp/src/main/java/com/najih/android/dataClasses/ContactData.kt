package com.najih.android.dataClasses

import kotlinx.serialization.Serializable



@Serializable
data class ContactUsRequest(
    val name: String,
    val email: String,
    val phoneNumber: String,
    val country: String,
    val message: String
)

@Serializable
data class ContactUsResponse(
    val status: String
)
