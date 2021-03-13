package br.com.raynerweb.pokemon.repository.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.raynerweb.pokemon.repository.local.entity.TypeEntity

@Dao
interface TypeDao {

    @Insert
    fun save(typeEntity: TypeEntity)

    @Query("SELECT * FROM type")
    fun findAll(): List<TypeEntity>

}