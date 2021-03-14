package br.com.raynerweb.pokemon.repository.preferences

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class UsernamePreferences @Inject constructor(
    private val preferences: SharedPreferences
) {

    companion object {
        private const val USERNAME = "USERNAME"
    }

    var username: String
        get() = preferences.getString(USERNAME, "") ?: ""
        set(value) = preferences.edit { putString(USERNAME, value) }

}