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
        pokemonRepository.getPokemonsTypes()?.let { dto ->
            pokemonTypesState.value =
                dto.results.map {
                    PokemonType(
                        image = it.thumbnailImage,
                        name = it.name.capitalize(Locale.getDefault())
                    )
                }.sortedBy { it.name }
        } ?: run {
            errorState.value = "There is no data!"
        }
    }

    fun pokemons() = viewModelScope.launch {
        pokemonRepository.getPokemons()?.let { dto ->
            pokemonsState.value = dto.map {
                Pokemon(
                    id = it.id,
                    name = it.name,
                    image = it.thumbnailImage,
                    type = it.type
                )
            }
        } ?: run {
            errorState.value = "There is no data!"
        }
    }
}