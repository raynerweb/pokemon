package br.com.raynerweb.pokemon.repository.local.entity

import androidx.room.Entity

@Entity(primaryKeys = ["pokemonId", "typeId"])
data class PokemonTypeEntity(
    val pokemonId: Long,
    val typeId: Long,
)
