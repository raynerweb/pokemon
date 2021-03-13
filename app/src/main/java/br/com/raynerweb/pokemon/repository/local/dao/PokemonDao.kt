package br.com.raynerweb.pokemon.repository.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.raynerweb.pokemon.repository.local.entity.PokemonEntity
import br.com.raynerweb.pokemon.repository.local.entity.TypesWithPokemons

@Dao
interface PokemonDao {

    @Insert
    fun save(pokemonEntity: PokemonEntity)

    @Query("SELECT * FROM type")
    fun findAllGroupByType(): List<TypesWithPokemons>

}