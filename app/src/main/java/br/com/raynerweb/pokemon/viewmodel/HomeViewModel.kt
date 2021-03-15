package br.com.raynerweb.pokemon.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.raynerweb.pokemon.domain.Pokemon
import br.com.raynerweb.pokemon.domain.PokemonType
import br.com.raynerweb.pokemon.repository.PokemonRepository
import br.com.raynerweb.pokemon.repository.TrainerRepository
import br.com.raynerweb.pokemon.repository.local.entity.SortSelect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository,
    private val trainerRepository: TrainerRepository,
) :
    ViewModel() {

    var sortSelectState = MutableLiveData<SortSelect>()
    private val _searching = MutableLiveData<String>()

    private val _pokemonsState = MutableLiveData<List<Pokemon>>()
    val pokemonsState = MediatorLiveData<List<Pokemon>>()

    init {
        pokemonsState.addSource(_pokemonsState) {
            pokemonsState.value = it
        }
        pokemonsState.addSource(sortSelectState) {
            val list = _pokemonsState.value ?: mutableListOf()
            if (sortSelectState.value == SortSelect.DESC) {
                _pokemonsState.value = list.sortedBy { it.name }.reversed()
            } else {
                _pokemonsState.value = list.sortedBy { it.name }
            }
        }
    }

    val pokemonTypesState = MutableLiveData<List<PokemonType>>()
    val errorState = MutableLiveData<String>()

    fun changePokemonType(pokemonType: PokemonType) {
        trainerRepository.setPokemonType(pokemonType.id)
        pokemons()
    }

    fun sort() {
        sortSelectState.value =
            if (sortSelectState.value == SortSelect.ASC) SortSelect.DESC else SortSelect.ASC
    }


    fun filter(newText: String?) {
        newText?.let {
            pokemonsState.value =
                _pokemonsState.value?.filter { it.name.contains(newText, ignoreCase = true) }
        }
    }

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
        _pokemonsState.value = pokemonRepository.findGroupedByTypes()
            .filter { grouped -> grouped.type.typeId == trainerRepository.getPokemonType() }
            .first()
            .pokemons.map {
                Pokemon(
                    name = it.name,
                    image = it.image,
                )
            }
        sortSelectState.value = SortSelect.ASC
        _searching.value = ""
    }

}