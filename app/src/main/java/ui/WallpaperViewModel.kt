package ui

import android.app.Application
import android.app.WallpaperManager
import android.content.ComponentName
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import data.WallpaperPreferences
import service.LiveVideoWallpaperService
import kotlinx.flow.MutableStateFlow
import kotlinx.flow.StateFlow

class WallpaperViewModel(application: Application) : AndroidViewModel(application) {
    
    private val prefs = WallpaperPreferences(application)
    private val _currentWallpaper = MutableStateFlow(prefs.getWallpaperUri())
    val currentWallpaper: StateFlow<String> = _currentWallpaper

    fun selectWallpaper(uriString: String) {
        prefs.setWallpaperUri(uriString)
        _currentWallpaper.value = uriString
        triggerSystemWallpaperChooser()
    }

    private fun triggerSystemWallpaperChooser() {
        val context = getApplication<Application>().applicationContext
        val intent = Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER).apply {
            putExtra(
                WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                ComponentName(context, LiveVideoWallpaperService::class.java)
            )
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }
}
