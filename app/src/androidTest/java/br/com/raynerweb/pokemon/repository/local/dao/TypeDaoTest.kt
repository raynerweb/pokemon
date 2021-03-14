package br.com.raynerweb.pokemon.repository.local.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.raynerweb.pokemon.repository.local.AppDatabase
import br.com.raynerweb.pokemon.repository.local.entity.TypeEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.IOException

class TypeDaoTest {

    private lateinit var typeDao: TypeDao
    private lateinit var db: AppDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        typeDao = db.typeDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun shouldSavePokemonType() = runBlocking {
        Assert.assertTrue(typeDao.findAll().isEmpty())

        val entity = TypeEntity(
            image = "image",
            name = "some name",
        )
        val types = mutableListOf<TypeEntity>()
        types.add(entity)
        typeDao.save(types)

        Assert.assertTrue(typeDao.findAll().isNotEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun shouldFetchAllTypesByName() = runBlocking {
        val types = mutableListOf<TypeEntity>()
        types.add(
            TypeEntity(
                image = "",
                name = "AAAA",
            )
        )
        types.add(
            TypeEntity(
                image = "",
                name = "BBBB",
            )
        )
        types.add(
            TypeEntity(
                image = "",
                name = "CCCC",
            )
        )

        typeDao.save(types)
        val saved = typeDao.findByNames(types.map { it.name })

        assertEquals(saved.size, types.size)
    }


}