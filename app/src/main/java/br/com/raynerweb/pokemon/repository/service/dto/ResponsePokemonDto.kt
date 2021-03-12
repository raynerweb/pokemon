package br.com.raynerweb.pokemon.repository.service.dto

data class ResponsePokemonDto(
    val id: Int,
    val thumbnailImage: String,
    val name: String,
    val type: List<String>,
)