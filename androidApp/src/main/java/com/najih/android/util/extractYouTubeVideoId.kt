package com.najih.android.util
fun extractYouTubeVideoId(url: String): String? {
    val regex = Regex("(?:https?://)?(?:www\\.)?(?:youtube\\.com/watch\\?v=|youtu\\.be/)([a-zA-Z0-9_-]{11})")
    return regex.find(url)?.groupValues?.get(1)
}