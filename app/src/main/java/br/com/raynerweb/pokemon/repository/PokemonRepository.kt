package br.com.raynerweb.pokemon.repository

import br.com.raynerweb.pokemon.repository.local.entity.PokemonEntity
import br.com.raynerweb.pokemon.repository.local.entity.PokemonTypeEntity
import br.com.raynerweb.pokemon.repository.local.entity.TypeEntity
import br.com.raynerweb.pokemon.repository.local.entity.TypesWithPokemons
import br.com.raynerweb.pokemon.repository.service.dto.ResponsePokemonDto
import br.com.raynerweb.pokemon.repository.service.dto.ResponsePokemonTypeDto

interface PokemonRepository {
    suspend fun findGroupedByTypes(): List<TypesWithPokemons>

    suspend fun findAllTypes(): List<TypeEntity>

    suspend fun findTypesByName(names: List<String>): List<TypeEntity>

    suspend fun saveTypes(types: List<TypeEntity>)

    suspend fun findAllPokemons(): List<PokemonEntity>

    suspend fun savePokemon(pokemon: PokemonEntity): Long

    suspend fun savePokemonType(pokemonTypeEntityList: List<PokemonTypeEntity>)

    suspend fun getPokemonsTypes(): ResponsePokemonTypeDto?

    suspend fun getPokemons(): Set<ResponsePokemonDto>?
}