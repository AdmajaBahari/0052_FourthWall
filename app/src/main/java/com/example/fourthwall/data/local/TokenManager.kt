package com.example.fourthwall.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.fourthwall.data.model.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Satu instance DataStore per aplikasi, dibuat lewat delegate di level file.
private val Context.authDataStore by preferencesDataStore(name = "auth_prefs")

class TokenManager(context: Context) {

    private val dataStore = context.applicationContext.authDataStore

    private object Keys {
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        val USER_ID = intPreferencesKey("user_id")
        val USER_NAMA = stringPreferencesKey("user_nama")
        val USER_EMAIL = stringPreferencesKey("user_email")
        val USER_ROLE = stringPreferencesKey("user_role")
    }

    val accessToken: Flow<String?> = dataStore.data.map { it[Keys.ACCESS_TOKEN] }
    val refreshToken: Flow<String?> = dataStore.data.map { it[Keys.REFRESH_TOKEN] }
    val role: Flow<String?> = dataStore.data.map { it[Keys.USER_ROLE] }

    // Simpan semua data sesi dalam satu transaksi supaya tidak setengah tersimpan.
    suspend fun saveSession(accessToken: String, refreshToken: String, user: UserData) {
        dataStore.edit { prefs ->
            prefs[Keys.ACCESS_TOKEN] = accessToken
            prefs[Keys.REFRESH_TOKEN] = refreshToken
            prefs[Keys.USER_ID] = user.id
            prefs[Keys.USER_NAMA] = user.nama
            prefs[Keys.USER_EMAIL] = user.email
            prefs[Keys.USER_ROLE] = user.role
        }
    }

    // Dipakai saat logout.
    suspend fun clearSession() {
        dataStore.edit { it.clear() }
    }
}
