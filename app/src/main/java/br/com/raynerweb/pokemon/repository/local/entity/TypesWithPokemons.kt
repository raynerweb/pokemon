package br.com.raynerweb.pokemon.repository.local.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class TypesWithPokemons(
    @Embedded val type: TypeEntity,
    @Relation(
        parentColumn = "typeId",
        entityColumn = "pokemonId",
        associateBy = Junction(PokemonTypeEntity::class)
    )
    val pokemons: List<PokemonEntity>
)

