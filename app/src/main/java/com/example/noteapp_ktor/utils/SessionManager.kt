package com.example.noteapp_ktor.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

class SessionManager (val context: Context) {

    private val Context.dataStore : DataStore<Preferences> by preferencesDataStore("session_manager")

    suspend fun updateSession(token:String,name:String,email:String) {
        val jwtTokenKey = stringPreferencesKey(Constants.JWT_TOKEN_KEY)
        val nameKey = stringPreferencesKey(Constants.NAME_KEY)
        val emailKey = stringPreferencesKey(Constants.EMAIL_KEY)
        context.dataStore.edit { preferences ->
            preferences[jwtTokenKey] = token
            preferences[nameKey] = name
            preferences[emailKey] = email
        }
    }

    suspend fun getJwtToken():String? {
        val jwtTokenKey = stringPreferencesKey(Constants.JWT_TOKEN_KEY)
        val preferences = context.dataStore.data.first()

        return preferences[jwtTokenKey]
    }

    suspend fun getCurrentUserName():String? {
        val nameKey = stringPreferencesKey(Constants.NAME_KEY)
        val preferences = context.dataStore.data.first()
        return preferences[nameKey]
    }

    suspend fun getCurrentUserEmail():String? {
        val emailKey = stringPreferencesKey(Constants.EMAIL_KEY)
        val preferences = context.dataStore.data.first()
        return preferences[emailKey]
    }

    suspend fun logout(){
        context.dataStore.edit {
            it.clear()
        }
    }

}