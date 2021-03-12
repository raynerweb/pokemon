package br.com.raynerweb.pokemon.repository

import br.com.raynerweb.pokemon.repository.service.PokemonService
import br.com.raynerweb.pokemon.repository.service.PokemonTypeService
import br.com.raynerweb.pokemon.repository.service.dto.ResponsePokemonDto
import br.com.raynerweb.pokemon.repository.service.dto.ResponsePokemonTypeDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val pokemonTypeService: PokemonTypeService,
    private val pokemonService: PokemonService,
) {

    suspend fun getPokemonsTypes(): ResponsePokemonTypeDto? {
        return withContext(context = Dispatchers.IO) {
            val response = pokemonTypeService.get().execute()
            try {
                if (response.isSuccessful) {
                    response.body()
                } else {
                    throw Throwable("Response error. HttpCode: ${response.code()}")
                }
            } catch (e: Exception) {
                throw Throwable(e.message)
            }
        }
    }

    suspend fun getPokemons(): Set<ResponsePokemonDto>? {
        return withContext(context = Dispatchers.IO) {
            val response = pokemonService.get().execute()
            try {
                if (response.isSuccessful) {
                    response.body()
                } else {
                    throw Throwable("Response error. HttpCode: ${response.code()}")
                }
            } catch (e: Exception) {
                throw Throwable(e.message)
            }
        }
    }

}