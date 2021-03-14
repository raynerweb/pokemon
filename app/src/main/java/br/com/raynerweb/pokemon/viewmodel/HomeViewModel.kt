package br.com.raynerweb.pokemon.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.raynerweb.pokemon.domain.Pokemon
import br.com.raynerweb.pokemon.domain.PokemonType
import br.com.raynerweb.pokemon.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) :
    ViewModel() {

    val pokemonsState = MutableLiveData<List<Pokemon>>()
    val pokemonTypesState = MutableLiveData<List<PokemonType>>()
    val errorState = MutableLiveData<String>()

    fun pokemonTypes() = viewModelScope.launch {
        pokemonTypesState.value = pokemonRepository.findAllTypes().map {
            PokemonType(
                id = it.typeId,
                image = it.image,
                name = it.name.capitalize(Locale.getDefault())
            )
        }
    }

    fun pokemons() = viewModelScope.launch {
        pokemonsState.value = pokemonRepository.findAllPokemons().map {
            Pokemon(
                name = it.name,
                image = it.image,
            )
        }
    }
}