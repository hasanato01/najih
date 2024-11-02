package com.najih.android.dataClasses

import kotlinx.serialization.Serializable

@Serializable
data class Stage(
    val stage: String,
    val type: String,
    val image:Int
)