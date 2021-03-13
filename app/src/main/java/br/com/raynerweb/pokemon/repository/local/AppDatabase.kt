package br.com.raynerweb.pokemon.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.raynerweb.pokemon.repository.local.dao.PokemonDao
import br.com.raynerweb.pokemon.repository.local.dao.TypeDao
import br.com.raynerweb.pokemon.repository.local.entity.PokemonEntity
import br.com.raynerweb.pokemon.repository.local.entity.PokemonTypeEntity
import br.com.raynerweb.pokemon.repository.local.entity.TypeEntity

@Database(
    entities = [PokemonEntity::class, TypeEntity::class, PokemonTypeEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao
    abstract fun typeDao(): TypeDao

}