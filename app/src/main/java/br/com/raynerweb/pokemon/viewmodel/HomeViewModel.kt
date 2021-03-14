package br.com.raynerweb.pokemon.viewmodel

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

    private var sortState = SortSelect.ASC

    val pokemonsState = MutableLiveData<List<Pokemon>>()
    val pokemonTypesState = MutableLiveData<List<PokemonType>>()
    val errorState = MutableLiveData<String>()

    fun changePokemonType(pokemonType: PokemonType) {
        trainerRepository.setPokemonType(pokemonType.id)
        pokemons()
    }

    fun sort() {
        sortState = if (sortState == SortSelect.ASC) SortSelect.DESC else SortSelect.ASC
        pokemonsState.value = pokemonsState.value?.reversed()
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
        val list = pokemonRepository.findGroupedByTypes()
            .filter { grouped -> grouped.type.typeId == trainerRepository.getPokemonType() }.first()
            .pokemons.map {
                Pokemon(
                    name = it.name,
                    image = it.image,
                )
            }.sortedBy { pokemon -> pokemon.name }

        if (sortState == SortSelect.DESC) {
            pokemonsState.value = list.reversed()
        } else {
            pokemonsState.value = list
        }
    }
}