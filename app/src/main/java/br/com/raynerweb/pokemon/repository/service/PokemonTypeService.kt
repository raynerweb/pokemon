package br.com.raynerweb.pokemon.repository.service

import br.com.raynerweb.pokemon.repository.service.dto.ResponsePokemonTypeDto
import retrofit2.Call
import retrofit2.http.GET

interface PokemonTypeService {

    @GET("types.json")
    fun get(): Call<ResponsePokemonTypeDto>

}