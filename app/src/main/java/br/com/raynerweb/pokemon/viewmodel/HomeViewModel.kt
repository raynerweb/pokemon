package br.com.raynerweb.pokemon.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.raynerweb.pokemon.domain.Pokemon
import br.com.raynerweb.pokemon.domain.PokemonType
import br.com.raynerweb.pokemon.ext.SingleLiveEvent
import br.com.raynerweb.pokemon.repository.PokemonRepository
import br.com.raynerweb.pokemon.repository.TrainerRepository
import br.com.raynerweb.pokemon.repository.local.entity.PokemonEntity
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

    private val _filter = MutableLiveData<String>()
    var sort = SingleLiveEvent<SortSelect>()

    private val _groupedPokemons = MutableLiveData<TypesWithPokemons>()
    val pokemons = MediatorLiveData<List<Pokemon>>()

    val pokemonTypesState = MutableLiveData<List<PokemonType>>()

    init {
        pokemons.addSource(_groupedPokemons) {
            prepare(it.pokemons)
        }
        pokemons.addSource(_filter) { filter ->
            _groupedPokemons.value?.let {
                prepare(it.pokemons)
            }
        }
        pokemons.addSource(sort) { sort ->
            _groupedPokemons.value?.let {
                prepare(it.pokemons)
            }
        }
    }

    private fun prepare(
        list: List<PokemonEntity>
    ) {
        var orderedList = list.map { entity ->
            Pokemon(
                name = entity.name,
                image = entity.image,
            )
        }.sortedBy { pokemon -> pokemon.name }
        val filter = _filter.value ?: ""
        if (filter.isNotBlank()) {
            orderedList = orderedList.filter { it.name.contains(filter, ignoreCase = true) }
        }
        val sortSelect = sort.value ?: SortSelect.ASC
        if (sortSelect == SortSelect.DESC) {
            orderedList = orderedList.reversed()
        }
        pokemons.value = orderedList
    }

    fun sort() {
        sort.value =
            if (sort.value == SortSelect.ASC || sort.value == null) SortSelect.DESC else SortSelect.ASC
    }

    fun filter(newText: String?) {
        _filter.value = newText ?: ""
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

    fun findPokemons(pokemonType: PokemonType? = null) = viewModelScope.launch {
        pokemonType?.let {
            _groupedPokemons.value = pokemonRepository.findOneTypeWithPokemons(it.id)
        } ?: run {
            _groupedPokemons.value =
                pokemonRepository.findOneTypeWithPokemons(trainerRepository.getPokemonType())
        }
    }

}