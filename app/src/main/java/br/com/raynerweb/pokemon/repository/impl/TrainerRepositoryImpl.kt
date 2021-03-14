package br.com.raynerweb.pokemon.repository.impl

import br.com.raynerweb.pokemon.repository.TrainerRepository
import br.com.raynerweb.pokemon.repository.preferences.PokemonTypePreferences
import br.com.raynerweb.pokemon.repository.preferences.UsernamePreferences
import javax.inject.Inject

class TrainerRepositoryImpl @Inject constructor(
    private val usernamePreferences: UsernamePreferences,
    private val pokemonTypePreferences: PokemonTypePreferences,

    ) : TrainerRepository {

    override fun setTrainer(username: String) {
        usernamePreferences.username = username
    }

    override fun getTrainer() = usernamePreferences.username

    override fun setPokemonType(id: Long) {
        pokemonTypePreferences.pokemonType = id
    }

    override fun getPokemonType() = pokemonTypePreferences.pokemonType

}