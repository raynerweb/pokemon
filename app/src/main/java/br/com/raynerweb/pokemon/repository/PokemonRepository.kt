package br.com.raynerweb.pokemon.repository

import br.com.raynerweb.pokemon.repository.local.dao.PokemonDao
import br.com.raynerweb.pokemon.repository.local.dao.PokemonTypeDao
import br.com.raynerweb.pokemon.repository.local.dao.TypeDao
import br.com.raynerweb.pokemon.repository.local.entity.PokemonEntity
import br.com.raynerweb.pokemon.repository.local.entity.PokemonTypeEntity
import br.com.raynerweb.pokemon.repository.local.entity.TypeEntity
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
    private val pokemonTypeDao: PokemonTypeDao,
    private val pokemonDao: PokemonDao,
    private val typeDao: TypeDao,
) {

    suspend fun findAllTypes() = withContext(context = Dispatchers.IO) {
        return@withContext typeDao.findAll()
    }

    suspend fun findTypesByName(names: List<String>) = withContext(context = Dispatchers.IO) {
        return@withContext typeDao.findByNames(names)
    }

    suspend fun saveTypes(types: List<TypeEntity>) =
        withContext(context = Dispatchers.IO) {
            typeDao.save(types)
        }

    suspend fun findAllPokemons() = withContext(context = Dispatchers.IO) {
        return@withContext pokemonDao.findAll()
    }

    suspend fun savePokemon(pokemon: PokemonEntity): Long {
        return withContext(context = Dispatchers.IO) {
            pokemonDao.save(pokemon)
        }
    }

    suspend fun savePokemonType(pokemonTypeEntityList: List<PokemonTypeEntity>) =
        withContext(context = Dispatchers.IO) {
            pokemonTypeDao.save(pokemonTypeEntityList)
        }

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