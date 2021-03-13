package br.com.raynerweb.pokemon.repository.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon")
data class PokemonEntity(
    @PrimaryKey(autoGenerate = true)
    val pokemonId: Int = 0,
    val image: String,
    val name: String,
)
