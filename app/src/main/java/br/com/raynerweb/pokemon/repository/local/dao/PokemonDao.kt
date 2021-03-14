package br.com.raynerweb.pokemon.repository.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.raynerweb.pokemon.repository.local.entity.PokemonEntity

@Dao
interface PokemonDao {

    @Insert
    fun save(pokemons: List<PokemonEntity>)

    @Insert
    fun save(pokemon: PokemonEntity): Long

    @Query("SELECT * FROM pokemon")
    fun findAll(): List<PokemonEntity>

}