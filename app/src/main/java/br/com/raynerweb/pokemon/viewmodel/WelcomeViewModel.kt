package br.com.raynerweb.pokemon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.raynerweb.pokemon.ext.SingleLiveEvent
import br.com.raynerweb.pokemon.repository.PokemonRepository
import br.com.raynerweb.pokemon.repository.TrainerRepository
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
    private val trainerRepository: TrainerRepository,
) : ViewModel() {

    val username = SingleLiveEvent<String>()

    private val _usernameSaved = SingleLiveEvent<Unit>()
    val usernameSaved: LiveData<Unit> get() = _usernameSaved

    private val _usernameError = SingleLiveEvent<Unit>()
    val usernameError: LiveData<Unit> get() = _usernameError

    private val _isLoading = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> get() = _isLoading

    fun saveUsername() {
        val name = username.value ?: ""
        if (name.isBlank()) {
            _usernameError.call()
        } else {
            trainerRepository.setTrainer(username = name)
            _usernameSaved.call()
        }
    }

    init {
        viewModelScope.launch {
            _isLoading.value = true

            if (pokemonRepository.findAllTypes().isEmpty()) {
                pokemonRepository.getPokemonsTypes()?.let { dto ->

                    pokemonRepository.saveTypes(dto.results.map {
                        TypeEntity(
                            image = it.thumbnailImage,
                            name = it.name.toUpperCase(Locale.getDefault())
                        )
                    })

                } ?: run {
                    //TODO tratar erro
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

                        pokemonRepository.findAllTypes().forEach { typeEntity ->
                            it.type.forEach { typeName ->

                            }
                        }

                        val list = pokemonRepository.findTypesByName(it.type.map { typeName ->
                            typeName.toUpperCase(Locale.getDefault())
                        })

                        pokemonRepository.savePokemonType(list.map { typeEntity ->
                            PokemonTypeEntity(
                                pokemonId = pokemonId,
                                typeId = typeEntity.typeId
                            )
                        })

                    }
                } ?: run {
                    //TODO tratar erro
                }
            }

            _isLoading.value = false
        }
    }

}