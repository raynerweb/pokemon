package br.com.raynerweb.pokemon.repository.service

import br.com.raynerweb.pokemon.repository.service.dto.ResponsePokemonDto
import retrofit2.Call
import retrofit2.http.GET

interface PokemonService {

    @GET("pokemons.json")
    fun get(): Call<List<ResponsePokemonDto>>

}