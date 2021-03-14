package br.com.raynerweb.pokemon.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.raynerweb.pokemon.domain.Pokemon
import br.com.raynerweb.pokemon.repository.PokemonRepository
import br.com.raynerweb.pokemon.repository.local.entity.TypesWithPokemons
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var repository: PokemonRepository

    @InjectMocks
    lateinit var viewModel: HomeViewModel

    @Test
    fun tets() {
        testCoroutineRule.runBlockingTest {
            Mockito.doReturn(emptyList<TypesWithPokemons>()).`when`(repository.findGroupedByTypes())
        }
        val observer = spy<Observer<List<Pokemon>>>()
        viewModel.pokemonsState.observeForever(observer)
        verifyNoMoreInteractions(observer)
    }

}

inline fun <reified T : Any> spy(): T {
    return Mockito.spy(T::class.java)!!
}