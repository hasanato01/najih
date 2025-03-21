package com.najih.android.util
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.LifecycleOwner
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun YouTubePlayerDialog(videoId: String?, onDismiss: () -> Unit) {
    if (videoId.isNullOrEmpty()) {
        Log.e("YouTubePlayer", "Invalid video ID: $videoId")
        onDismiss()  // Close dialog if video ID is null or empty
        return
    }

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    Dialog(onDismissRequest = onDismiss) {
        AndroidView(
            factory = { context ->
                YouTubePlayerView(context).apply {
                    // Disable automatic initialization
                    enableAutomaticInitialization = false

                    lifecycleOwner.lifecycle.addObserver(this)

                    // Now manually initialize the YouTube player
                    initialize(
                        object : AbstractYouTubePlayerListener() {
                            override fun onReady(youTubePlayer: YouTubePlayer) {
                                Log.d("YouTubePlayer", "Player ready: Loading video $videoId")
                                try {
                                    youTubePlayer.loadVideo(videoId, 0f)
                                } catch (e: Exception) {
                                    Log.e("YouTubePlayer", "Error loading video: ", e)
                                    onDismiss()
                                }
                            }

                            override fun onError(
                                youTubePlayer: YouTubePlayer,
                                error: PlayerConstants.PlayerError
                            ) {
                                Log.e("YouTubePlayer", "YouTube Player error: $error")
                                onDismiss()
                            }
                        }
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(Color.Black, shape = RoundedCornerShape(8.dp))
        )
    }
}