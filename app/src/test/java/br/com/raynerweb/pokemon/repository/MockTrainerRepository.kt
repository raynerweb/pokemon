package br.com.raynerweb.pokemon.repository

class MockTrainerRepository : TrainerRepository {

    override fun setTrainer(username: String) {

    }

    override fun getTrainer() = "Teste"

    override fun setPokemonType(id: Long) {
    }

    override fun getPokemonType() = 0L
}