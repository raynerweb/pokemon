package br.com.raynerweb.pokemon.repository.preferences

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class PokemonTypePreferences @Inject constructor(
    private val preferences: SharedPreferences
) {
    companion object {
        private const val POKEMON_TYPE_ID = "POKEMON_TYPE_ID"
    }

    var pokemonType: Long
        get() = preferences.getLong(POKEMON_TYPE_ID, 0)
        set(value) = preferences.edit { putLong(POKEMON_TYPE_ID, value) }

}