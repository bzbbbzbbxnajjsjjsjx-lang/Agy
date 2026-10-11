package com.example.agy.data

import android.content.Context
import android.content.SharedPreferences

/**
 * Desteklenen tema modları: Sistem varsayılanı, Açık tema, Koyu tema.
 */
enum class ThemeMode {
    SYSTEM,
    LIGHT,
    DARK
}

/**
 * Platform yerleşik SharedPreferences mekanizması ile tema tercihini
 * kalıcı olarak saklayan ve okuyan sınıf.
 */
class ThemePreference(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("agy_preferences", Context.MODE_PRIVATE)

    fun getThemeMode(): ThemeMode {
        val stored = prefs.getString(KEY_THEME_MODE, ThemeMode.SYSTEM.name)
        return try {
            ThemeMode.valueOf(stored ?: ThemeMode.SYSTEM.name)
        } catch (_: IllegalArgumentException) {
            ThemeMode.SYSTEM
        }
    }

    fun setThemeMode(mode: ThemeMode) {
        prefs.edit().putString(KEY_THEME_MODE, mode.name).apply()
    }

    companion object {
        private const val KEY_THEME_MODE = "key_theme_mode"
    }
}
