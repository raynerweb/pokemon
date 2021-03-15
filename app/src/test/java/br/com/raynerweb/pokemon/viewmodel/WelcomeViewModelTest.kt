package br.com.raynerweb.pokemon.viewmodel

import androidx.annotation.CallSuper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.raynerweb.pokemon.ext.SingleLiveEvent
import br.com.raynerweb.pokemon.repository.PokemonRepository
import br.com.raynerweb.pokemon.repository.local.entity.TypeEntity
import br.com.raynerweb.pokemon.repository.service.dto.PokemonTypeDto
import br.com.raynerweb.pokemon.repository.service.dto.ResponsePokemonDto
import br.com.raynerweb.pokemon.repository.service.dto.ResponsePokemonTypeDto
import br.com.raynerweb.pokemon.test.CoroutineTestRule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.MockitoAnnotations
import java.lang.reflect.Field

class WelcomeViewModelTest {

    private val repository = mock<PokemonRepository>()

    @InjectMocks
    lateinit var viewModel: WelcomeViewModel

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    @CallSuper
    @Before
    fun setup() {
        setupMock()
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `Should populate the database when using the app for the first time`() = runBlocking {
        val f: Field = viewModel.javaClass.getDeclaredField("_databaseComplete");
        f.isAccessible = true;
        val databaseComplete = f.get(viewModel) as SingleLiveEvent<Boolean>
        assertTrue(databaseComplete.value ?: false)
    }

    private fun setupMock() = runBlocking {
        whenever(repository.findAllTypes()).thenReturn(emptyList())
        whenever(repository.findAllPokemons()).thenReturn(emptyList())

        val pokemonsTypes = getPokemonsTypes()
        whenever(repository.getPokemonsTypes()).thenReturn(pokemonsTypes)

        val pokemons = getPokemons()
        whenever(repository.getPokemons()).thenReturn(pokemons)

        whenever(repository.savePokemon(any())).thenReturn(1)

        whenever(repository.findTypesByName(any())).thenReturn(getTypesByName())
    }

    private fun getPokemonsTypes(): ResponsePokemonTypeDto {
        val set = mutableSetOf(
            PokemonTypeDto(
                thumbnailImage = "image",
                name = "eletric"
            )
        )
        return ResponsePokemonTypeDto(
            results = set
        )
    }

    private fun getPokemons(): MutableSet<ResponsePokemonDto> {
        return mutableSetOf(
            ResponsePokemonDto(
                id = 1,
                thumbnailImage = "image",
                name = "name",
                type = mutableListOf("eletric")
            )
        )
    }

    private fun getTypesByName(): MutableList<TypeEntity> {
        return mutableListOf(
            TypeEntity(
                typeId = 1,
                image = "image",
                name = "ELETRIC"
            )
        )
    }

}