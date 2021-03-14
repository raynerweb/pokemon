package br.com.raynerweb.pokemon.repository.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.raynerweb.pokemon.repository.local.entity.TypeEntity

@Dao
interface TypeDao {

    @Insert
    fun save(typeEntityList: List<TypeEntity>)

    @Query("SELECT * FROM type ORDER BY name ASC")
    fun findAll(): List<TypeEntity>

    @Query("SELECT * FROM type ORDER BY name DESC")
    fun findAllOrderByDesc(): List<TypeEntity>

    @Query("SELECT * FROM type WHERE name IN (:names)")
    fun findByNames(names: List<String>): List<TypeEntity>
}