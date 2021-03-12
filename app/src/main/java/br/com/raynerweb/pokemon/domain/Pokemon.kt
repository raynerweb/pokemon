package br.com.raynerweb.pokemon.domain

data class Pokemon(
    val image: String,
    val name: String,
    val type: List<String>,
)
