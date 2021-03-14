package br.com.raynerweb.pokemon.repository.service.dto

data class ResponsePokemonTypeDto(
    val results: Set<PokemonTypeDto>,
)

data class PokemonTypeDto(
    val thumbnailImage: String,
    val name: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PokemonTypeDto

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}
