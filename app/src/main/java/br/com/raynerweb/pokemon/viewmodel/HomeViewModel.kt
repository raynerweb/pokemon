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
import br.com.raynerweb.pokemon.repository.local.entity.TypesWithPokemons
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

    private val _pokemonTypePreference = MutableLiveData(trainerRepository.getPokemonType())

    private val _groupState = MutableLiveData<List<TypesWithPokemons>>()

    private var _sortSelectState = MutableLiveData(SortSelect.ASC)

    private val _pokemonsState = MutableLiveData<List<Pokemon>>()
    val pokemonsState = MediatorLiveData<List<Pokemon>>()

    init {
        pokemonsState.addSource(_pokemonsState) {
            pokemonsState.value = it
        }
        pokemonsState.addSource(_sortSelectState) {
            val list = _pokemonsState.value ?: mutableListOf()
            if (_sortSelectState.value == SortSelect.DESC) {
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
        _sortSelectState.value =
            if (_sortSelectState.value == SortSelect.ASC) SortSelect.DESC else SortSelect.ASC
    }


    fun filter(newText: String?) {

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
        _groupState.value = pokemonRepository.findGroupedByTypes()

        _pokemonsState.value = pokemonRepository.findGroupedByTypes()
            .filter { grouped -> grouped.type.typeId == trainerRepository.getPokemonType() }.first()
            .pokemons.map {
                Pokemon(
                    name = it.name,
                    image = it.image,
                )
            }.sortedBy { it.name }

    }

}