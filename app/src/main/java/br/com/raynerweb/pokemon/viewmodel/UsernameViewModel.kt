package br.com.raynerweb.pokemon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.raynerweb.pokemon.ext.SingleLiveEvent
import br.com.raynerweb.pokemon.repository.TrainerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UsernameViewModel @Inject constructor(
    private val trainerRepository: TrainerRepository,
) : ViewModel() {

    val username = MutableLiveData<String>()

    private val _usernameSaved = SingleLiveEvent<Unit>()
    val usernameSaved: LiveData<Unit> get() = _usernameSaved

    private val _usernameError = SingleLiveEvent<Unit>()
    val usernameError: LiveData<Unit> get() = _usernameError

    fun saveUsername() {
        val name = username.value ?: ""
        if (name.isBlank()) {
            _usernameError.call()
        } else {
            trainerRepository.setTrainer(username = name)
            _usernameSaved.call()
        }
    }

}