package data

import android.content.Context

class WallpaperPreferences(private val context: Context) {
    private val prefs = context.getSharedPreferences("WallpaperPrefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_VIDEO_URI = "selected_video_uri"
        private const val DEFAULT_ASSET = "asset:///cyber_punk_loop.mp4"
    }

    fun setWallpaperUri(uriString: String) {
        prefs.edit().putString(KEY_VIDEO_URI, uriString).apply()
    }

    fun getWallpaperUri(): String {
        return prefs.getString(KEY_VIDEO_URI, DEFAULT_ASSET) ?: DEFAULT_ASSET
    }
}
