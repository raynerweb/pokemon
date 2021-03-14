package br.com.raynerweb.pokemon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.raynerweb.pokemon.domain.PokemonType
import br.com.raynerweb.pokemon.ext.SingleLiveEvent
import br.com.raynerweb.pokemon.repository.PokemonRepository
import br.com.raynerweb.pokemon.repository.TrainerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PokemonTypeViewModel @Inject constructor(
    private val trainerRepository: TrainerRepository,
    private val pokemonRepository: PokemonRepository,
) : ViewModel() {

    val username = MutableLiveData<String>()

    private val _pokemonTypes = MutableLiveData<List<PokemonType>>()
    val pokemonTypesState: MutableLiveData<List<PokemonType>> get() = _pokemonTypes

    private val _pokemonTypeSaved = SingleLiveEvent<Unit>()
    val pokemonTypeSaved: LiveData<Unit> get() = _pokemonTypeSaved

    fun init() = viewModelScope.launch {
        username.value = trainerRepository.getTrainer()
        _pokemonTypes.value = pokemonRepository.findAllTypes().map {
            PokemonType(
                id = it.typeId,
                name = it.name.toLowerCase(Locale.getDefault())
                    .capitalize(Locale.getDefault()),
                image = it.image,
            )
        }
    }

    fun saveSelectedPokemonType(pokemonType: PokemonType) {
        trainerRepository.setPokemonType(pokemonType.id)
        _pokemonTypeSaved.call()
    }
}
