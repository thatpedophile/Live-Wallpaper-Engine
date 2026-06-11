package service

import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import data.WallpaperPreferences

class LiveVideoWallpaperService : WallpaperService() {

    override fun onCreateEngine(): Engine {
        return VideoEngine()
    }

    inner class VideoEngine : Engine() {
        private var exoPlayer: ExoPlayer? = null
        private lateinit var prefs: WallpaperPreferences

        override fun onCreate(surfaceHolder: SurfaceHolder?) {
            super.onCreate(surfaceHolder)
            prefs = WallpaperPreferences(applicationContext)
        }

        override fun onSurfaceCreated(holder: SurfaceHolder) {
            super.onSurfaceCreated(holder)
            
            exoPlayer = ExoPlayer.Builder(applicationContext).build().apply {
                volume = 0f
                repeatMode = Player.REPEAT_MODE_ALL
                
                val videoUriStr = prefs.getWallpaperUri()
                setMediaItem(MediaItem.fromUri(videoUriStr))
                setVideoSurfaceHolder(holder)
                prepare()
                playWhenReady = true
            }
        }

        override fun onVisibilityChanged(visible: Boolean) {
            if (visible) {
                exoPlayer?.play()
            } else {
                exoPlayer?.pause()
            }
        }

        override fun onSurfaceDestroyed(holder: SurfaceHolder) {
            super.onSurfaceDestroyed(holder)
            exoPlayer?.release()
            exoPlayer = null
        }
    }
}
