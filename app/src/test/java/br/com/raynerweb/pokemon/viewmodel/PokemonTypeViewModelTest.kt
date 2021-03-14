package br.com.raynerweb.pokemon.viewmodel

import androidx.annotation.CallSuper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.raynerweb.pokemon.repository.PokemonRepository
import br.com.raynerweb.pokemon.repository.TrainerRepository
import br.com.raynerweb.pokemon.test.CoroutineTestRule
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import org.junit.Before
import org.junit.Rule
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


}