package com.example.taskshmanager

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore // Import de la extensión de delegado
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Propiedad de extensión declarada fuera de la clase
private val Context.dataStore by preferencesDataStore(name = "settings")

class ThemePreferences(private val context: Context) {
    companion object {
        val THEME_KEY = stringPreferencesKey("selected_theme_id")
    }

    val selectedThemeId: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[THEME_KEY] ?: "default"
    }

    suspend fun saveThemeId(themeId: String) {
        context.dataStore.edit { prefs ->
            prefs[THEME_KEY] = themeId
        }
    }
}