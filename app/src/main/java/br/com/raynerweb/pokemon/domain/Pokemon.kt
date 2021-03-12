package br.com.raynerweb.pokemon.domain

data class Pokemon(
    val id: Int,
    val image: String,
    val name: String,
    val type: List<String>,
)
