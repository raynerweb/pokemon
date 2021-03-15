package br.com.raynerweb.pokemon.viewmodel

import androidx.annotation.CallSuper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.raynerweb.pokemon.domain.Pokemon
import br.com.raynerweb.pokemon.domain.PokemonType
import br.com.raynerweb.pokemon.repository.PokemonRepository
import br.com.raynerweb.pokemon.repository.TrainerRepository
import br.com.raynerweb.pokemon.repository.local.entity.PokemonEntity
import br.com.raynerweb.pokemon.repository.local.entity.TypeEntity
import br.com.raynerweb.pokemon.repository.local.entity.TypesWithPokemons
import br.com.raynerweb.pokemon.test.CoroutineTestRule
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.MockitoAnnotations

class HomeViewModelTest {

    private val trainerRepository = mock<TrainerRepository>()
    private val pokemonRepository = mock<PokemonRepository>()

    @InjectMocks
    lateinit var viewModel: HomeViewModel

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
    fun `Should notify _pokemons based on trainer preference`(): Unit = runBlocking {
        val result = TypesWithPokemons(
            type = TypeEntity(
                typeId = 1,
                image = "image",
                name = "name",
            ),
            pokemons = mutableListOf(
                PokemonEntity(
                    pokemonId = 1,
                    name = "Z",
                    image = "Z"
                ),
                PokemonEntity(
                    pokemonId = 2,
                    name = "C",
                    image = "C"
                ),
                PokemonEntity(
                    pokemonId = 3,
                    name = "D",
                    image = "D"
                ),
            )
        )
        whenever(trainerRepository.getPokemonType()).thenReturn(1)
        whenever(pokemonRepository.findOneTypeWithPokemons(ArgumentMatchers.anyLong())).thenReturn(
            result
        )

        viewModel.findPokemons()
        val observer = spy<Observer<List<Pokemon>>>()
        viewModel.pokemons.observeForever(observer)
        verify(observer).onChanged(
            eq(
                mutableListOf(
                    Pokemon(
                        image = "C",
                        name = "C"
                    ),
                    Pokemon(
                        image = "D",
                        name = "D"
                    ),
                    Pokemon(
                        image = "Z",
                        name = "Z"
                    ),
                )
            )
        )
    }

    @Test
    fun `Should notify _pokemons based on user click`(): Unit = runBlocking {
        val result = TypesWithPokemons(
            type = TypeEntity(
                typeId = 1,
                image = "image",
                name = "name",
            ),
            pokemons = mutableListOf(
                PokemonEntity(
                    pokemonId = 1,
                    name = "Z",
                    image = "Z"
                ),
                PokemonEntity(
                    pokemonId = 2,
                    name = "C",
                    image = "C"
                ),
                PokemonEntity(
                    pokemonId = 3,
                    name = "D",
                    image = "D"
                ),
            )
        )
        whenever(pokemonRepository.findOneTypeWithPokemons(ArgumentMatchers.anyLong())).thenReturn(
            result
        )

        viewModel.findPokemons(
            PokemonType(
                id = 1,
                name = "name",
                image = "image"
            )
        )
        val observer = spy<Observer<List<Pokemon>>>()
        viewModel.pokemons.observeForever(observer)
        verify(observer).onChanged(
            eq(
                mutableListOf(
                    Pokemon(
                        image = "C",
                        name = "C"
                    ),
                    Pokemon(
                        image = "D",
                        name = "D"
                    ),
                    Pokemon(
                        image = "Z",
                        name = "Z"
                    ),
                )
            )
        )
    }

    @Test
    fun `Should notify _pokemons filtered`(): Unit = runBlocking {
        val result = TypesWithPokemons(
            type = TypeEntity(
                typeId = 1,
                image = "image",
                name = "name",
            ),
            pokemons = mutableListOf(
                PokemonEntity(
                    pokemonId = 1,
                    name = "Z",
                    image = "Z"
                ),
                PokemonEntity(
                    pokemonId = 2,
                    name = "C",
                    image = "C"
                ),
                PokemonEntity(
                    pokemonId = 3,
                    name = "D",
                    image = "D"
                ),
            )
        )
        whenever(pokemonRepository.findOneTypeWithPokemons(ArgumentMatchers.anyLong())).thenReturn(
            result
        )

        viewModel.findPokemons()
        viewModel.filter("C")

        val observer = spy<Observer<List<Pokemon>>>()
        viewModel.pokemons.observeForever(observer)
        verify(observer, atLeast(2)).onChanged(
            eq(
                mutableListOf(
                    Pokemon(
                        image = "C",
                        name = "C"
                    ),
                )
            )
        )
    }

    @Test
    fun `Should notify _pokemons ordered`(): Unit = runBlocking {
        val result = TypesWithPokemons(
            type = TypeEntity(
                typeId = 1,
                image = "image",
                name = "name",
            ),
            pokemons = mutableListOf(
                PokemonEntity(
                    pokemonId = 1,
                    name = "aba",
                    image = "aba"
                ),
                PokemonEntity(
                    pokemonId = 2,
                    name = "aab",
                    image = "aab"
                ),
                PokemonEntity(
                    pokemonId = 3,
                    name = "baa",
                    image = "baa"
                ),
            )
        )
        whenever(pokemonRepository.findOneTypeWithPokemons(ArgumentMatchers.anyLong())).thenReturn(
            result
        )

        viewModel.findPokemons()
        viewModel.sort()

        val observer = spy<Observer<List<Pokemon>>>()
        viewModel.pokemons.observeForever(observer)
        verify(observer, atLeast(2)).onChanged(
            eq(
                mutableListOf(
                    Pokemon(
                        image = "baa",
                        name = "baa"
                    ),
                    Pokemon(
                        image = "aba",
                        name = "aba"
                    ),
                    Pokemon(
                        image = "aab",
                        name = "aab"
                    ),
                )
            )
        )
    }

}