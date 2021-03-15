package br.com.raynerweb.pokemon.viewmodel

import androidx.annotation.CallSuper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.raynerweb.pokemon.repository.PokemonRepository
import br.com.raynerweb.pokemon.repository.local.entity.PokemonEntity
import br.com.raynerweb.pokemon.repository.local.entity.TypeEntity
import br.com.raynerweb.pokemon.test.CoroutineTestRule
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.MockitoAnnotations

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
    fun `Should notify loadingState on startup viewmodel`() = runBlocking {
        val observer = spy<Observer<Boolean>>()
        viewModel.loadingState.observeForever(observer)
        verify(observer).onChanged(eq(false))
    }

    private fun setupMock() = runBlocking {
        val typesEntityList = typesEntityList()
        whenever(repository.findAllTypes()).thenReturn(typesEntityList)

        val pokemonEntityList = pokemonEntityList()
        whenever(repository.findAllPokemons()).thenReturn(pokemonEntityList)
    }

    private fun typesEntityList(): List<TypeEntity> {
        val list = mutableListOf<TypeEntity>()
        list.add(
            TypeEntity(
                typeId = 1,
                name = "eletric",
                image = "image"
            )
        )
        return list
    }

    private fun pokemonEntityList(): List<PokemonEntity> {
        return mutableListOf(
            PokemonEntity(
                pokemonId = 1,
                image = "image",
                name = "name",
            )
        ).toList()
    }

}