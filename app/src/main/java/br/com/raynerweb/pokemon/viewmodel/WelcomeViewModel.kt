package br.com.raynerweb.pokemon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.raynerweb.pokemon.ext.SingleLiveEvent
import br.com.raynerweb.pokemon.repository.PokemonRepository
import br.com.raynerweb.pokemon.repository.local.entity.PokemonEntity
import br.com.raynerweb.pokemon.repository.local.entity.PokemonTypeEntity
import br.com.raynerweb.pokemon.repository.local.entity.TypeEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository,
) : ViewModel() {

    private val _databaseComplete = SingleLiveEvent<Boolean>()

    private val _isLoading = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> get() = _isLoading

    init {
        viewModelScope.launch {
            _isLoading.value = true

            val types = pokemonRepository.findAllTypes()
            if (types.isEmpty()) {
                pokemonRepository.getPokemonsTypes()?.let { dto ->

                    pokemonRepository.saveTypes(dto.results.map {
                        TypeEntity(
                            image = it.thumbnailImage,
                            name = it.name.toUpperCase(Locale.getDefault())
                        )
                    })

                }
            }

            if (pokemonRepository.findAllPokemons().isEmpty()) {
                pokemonRepository.getPokemons()?.let { dto ->

                    dto.forEach {
                        val pokemonId = pokemonRepository.savePokemon(
                            PokemonEntity(
                                name = it.name,
                                image = it.thumbnailImage,
                            )
                        )

                        val list = pokemonRepository.findTypesByName(it.type.map { typeName ->
                            typeName.toUpperCase(Locale.getDefault())
                        })

                        pokemonRepository.savePokemonType(list.map { typeEntity ->
                            PokemonTypeEntity(
                                pokemonId = pokemonId,
                                typeId = typeEntity.typeId
                            )
                        })

                        _databaseComplete.value = true
                    }
                }
            } else {
                _databaseComplete.value = false
            }

            _isLoading.value = false
        }
    }

}