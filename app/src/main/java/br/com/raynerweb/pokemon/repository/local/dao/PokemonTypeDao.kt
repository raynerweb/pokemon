package br.com.raynerweb.pokemon.repository.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import br.com.raynerweb.pokemon.repository.local.entity.PokemonTypeEntity

@Dao
interface PokemonTypeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun save(join: List<PokemonTypeEntity>)

}