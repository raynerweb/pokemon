package br.com.raynerweb.pokemon.repository.local.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.raynerweb.pokemon.repository.local.AppDatabase
import br.com.raynerweb.pokemon.repository.local.entity.PokemonEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class PokemonDaoTest {

    private lateinit var pokemonDao: PokemonDao
    private lateinit var db: AppDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        pokemonDao = db.pokemonDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun shouldSavePokemon() = runBlocking {
        val entity = PokemonEntity(
            image = "image",
            name = "some name",
        )
        pokemonDao.save(entity)
        assertTrue(pokemonDao.findAll().isNotEmpty())
    }


}