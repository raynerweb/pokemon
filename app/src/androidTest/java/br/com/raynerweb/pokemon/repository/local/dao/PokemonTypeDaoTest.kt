package br.com.raynerweb.pokemon.repository.local.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.raynerweb.pokemon.repository.local.AppDatabase
import br.com.raynerweb.pokemon.repository.local.entity.PokemonEntity
import br.com.raynerweb.pokemon.repository.local.entity.PokemonTypeEntity
import br.com.raynerweb.pokemon.repository.local.entity.TypeEntity
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class PokemonTypeDaoTest {

    private lateinit var pokemonTypeDao: PokemonTypeDao
    private lateinit var typeDao: TypeDao
    private lateinit var pokemonDao: PokemonDao
    private lateinit var db: AppDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        pokemonTypeDao = db.pokemonTypeDao()
        typeDao = db.typeDao()
        pokemonDao = db.pokemonDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(IOException::class)
    fun shouldSaveRelationshipAndFetchData() {
        val types = mutableListOf<TypeEntity>()
        types.add(
            TypeEntity(
                image = "",
                name = "AAAA",
            )
        )
        typeDao.save(types)

        val idList = mutableListOf<Long>()
        idList.add(
            pokemonDao.save(
                PokemonEntity(
                    image = "image",
                    name = "some pokemon",
                )
            )
        )
        idList.add(
            pokemonDao.save(
                PokemonEntity(
                    image = "image",
                    name = "other pokemon",
                )
            )
        )

        pokemonTypeDao.save(idList.map {
            PokemonTypeEntity(
                pokemonId = it,
                typeId = typeDao.findAll().first().typeId
            )
        })

        val grouped = typeDao.findGroupedByTypes()

        assertEquals(grouped.size, 1)
        assertEquals(grouped.first().pokemons.size, idList.size)
    }


}