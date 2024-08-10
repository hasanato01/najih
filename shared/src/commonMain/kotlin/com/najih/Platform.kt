package com.najih

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform