package br.com.raynerweb.pokemon.repository.service.dto

data class ResponsePokemonTypeDto(
    val results: List<PokemonTypeDto>,
)

data class PokemonTypeDto(
    val thumbnailImage: String,
    val name: String,
)
