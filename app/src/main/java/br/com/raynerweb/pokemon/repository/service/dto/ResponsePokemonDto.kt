package br.com.raynerweb.pokemon.repository.service.dto

data class ResponsePokemonDto(
    val id: Int,
    val thumbnailImage: String,
    val name: String,
    val type: List<String>,
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ResponsePokemonDto

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}