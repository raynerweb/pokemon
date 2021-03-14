package br.com.raynerweb.pokemon.repository

import br.com.raynerweb.pokemon.repository.preferences.PokemonTypePreferences
import br.com.raynerweb.pokemon.repository.preferences.UsernamePreferences
import javax.inject.Inject

class TrainerRepository @Inject constructor(
    private val usernamePreferences: UsernamePreferences,
    private val pokemonTypePreferences: PokemonTypePreferences,

    ) {

    fun setTrainer(username: String) {
        usernamePreferences.username = username
    }

    fun getTrainer() = usernamePreferences.username

    fun setPokemonType(id: Long) {
        pokemonTypePreferences.pokemonType = id
    }

    fun getPokemonType() = pokemonTypePreferences.pokemonType

}