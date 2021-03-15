package br.com.raynerweb.pokemon.viewmodel

import androidx.annotation.CallSuper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.raynerweb.pokemon.domain.PokemonType
import br.com.raynerweb.pokemon.repository.PokemonRepository
import br.com.raynerweb.pokemon.repository.TrainerRepository
import br.com.raynerweb.pokemon.repository.local.entity.TypeEntity
import br.com.raynerweb.pokemon.test.CoroutineTestRule
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.InjectMocks
import org.mockito.MockitoAnnotations

class PokemonTypeViewModelTest {

    private val trainerRepository = mock<TrainerRepository>()
    private val pokemonRepository = mock<PokemonRepository>()

    @InjectMocks
    lateinit var viewModel: PokemonTypeViewModel

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
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `Should notify username onInit`(): Unit = runBlocking {
        val username = "rayner"
        val typesEntityList = mutableListOf<TypeEntity>().toList()
        whenever(trainerRepository.getTrainer()).thenReturn(username)
        whenever(pokemonRepository.findAllTypes()).thenReturn(typesEntityList)

        viewModel.init()
        val observer = spy<Observer<String>>()
        viewModel.username.observeForever(observer)

        verify(observer).onChanged(eq(username))
    }

    @Test
    fun `Should notify pokemonTypesState onInit`(): Unit = runBlocking {
        val username = "rayner"
        whenever(trainerRepository.getTrainer()).thenReturn(username)

        val eletric = "eletric"
        val typesEntityList = mutableListOf<TypeEntity>()
        typesEntityList.add(
            TypeEntity(
                typeId = 1,
                name = eletric,
                image = "image"
            )
        )
        whenever(pokemonRepository.findAllTypes()).thenReturn(typesEntityList.toList())

        viewModel.init()
        val observer = spy<Observer<List<PokemonType>>>()
        viewModel.pokemonTypesState.observeForever(observer)

        val pokemonTypeList = mutableListOf<PokemonType>()
        pokemonTypeList.add(
            PokemonType(
                id = 1,
                image = "image",
                name = eletric.capitalize()
            )
        )
        verify(observer).onChanged(eq(pokemonTypeList.toList()))
    }

    @Test
    fun `Should notify pokemonTypeSaved on saveSelectedPokemonType`() {
        doNothing().`when`(trainerRepository).setPokemonType(anyLong())
        viewModel.saveSelectedPokemonType(
            PokemonType(
                id = 1,
                image = "image",
                name = "name"
            )
        )
        val observer = spy<Observer<Unit>>()
        viewModel.pokemonTypeSaved.observeForever(observer)

        verify(observer).onChanged(null)
    }
}