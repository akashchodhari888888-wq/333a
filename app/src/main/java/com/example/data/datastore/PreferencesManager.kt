package com.example.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class PreferencesManager(context: Context) {
    private val dataStore = context.dataStore

    companion object {
        val THEME_MODE = stringPreferencesKey("theme_mode") // "light", "dark", "system"
        val LANGUAGE = stringPreferencesKey("language") // "en", "hi", "es", "ar", "pt", "id"
        val DEFAULT_QUALITY = stringPreferencesKey("default_quality") // "HD", "SD"
    }

    val themeMode: Flow<String> = dataStore.data.map { it[THEME_MODE] ?: "system" }
    val language: Flow<String> = dataStore.data.map { it[LANGUAGE] ?: "en" }
    val defaultQuality: Flow<String> = dataStore.data.map { it[DEFAULT_QUALITY] ?: "HD" }

    suspend fun setThemeMode(mode: String) {
        dataStore.edit { it[THEME_MODE] = mode }
    }

    suspend fun setLanguage(lang: String) {
        dataStore.edit { it[LANGUAGE] = lang }
    }

    suspend fun setDefaultQuality(quality: String) {
        dataStore.edit { it[DEFAULT_QUALITY] = quality }
    }
}
