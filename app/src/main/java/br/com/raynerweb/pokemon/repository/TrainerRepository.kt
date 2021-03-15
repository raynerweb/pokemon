package br.com.raynerweb.pokemon.repository

interface TrainerRepository {

    fun setTrainer(username: String)

    fun getTrainer(): String

    fun setPokemonType(id: Long)

    fun getPokemonType(): Long

}