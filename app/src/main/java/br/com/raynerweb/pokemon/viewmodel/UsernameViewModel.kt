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

    private val _usernameSaved = SingleLiveEvent<Boolean>()
    val usernameSaved: LiveData<Boolean> get() = _usernameSaved

    private val _usernameError = SingleLiveEvent<Boolean>()
    val usernameError: LiveData<Boolean> get() = _usernameError

    fun saveUsername() {
        val name = username.value ?: ""
        if (name.isBlank()) {
            _usernameError.value = true
        } else {
            trainerRepository.setTrainer(username = name)
            _usernameSaved.value = true
        }
    }

}