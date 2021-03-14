package br.com.raynerweb.pokemon.viewmodel

import androidx.annotation.CallSuper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.raynerweb.pokemon.repository.TrainerRepository
import br.com.raynerweb.pokemon.test.CoroutineTestRule
import com.nhaarman.mockitokotlin2.doNothing
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.ArgumentMatchers.eq
import org.mockito.InjectMocks
import org.mockito.MockitoAnnotations

class UsernameViewModelTest {

    private val repository = mock<TrainerRepository>()

    @InjectMocks
    lateinit var viewModel: UsernameViewModel

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    @CallSuper
    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `Should notify usernameError`() {
        doNothing().`when`(repository).setTrainer(anyString())
        viewModel.saveUsername()
        val observer = spy<Observer<Boolean>>()
        viewModel.usernameError.observeForever(observer)
        verify(observer).onChanged(eq(true))
    }

    @Test
    fun `Should notify usernameSaved`() {
        doNothing().`when`(repository).setTrainer(anyString())
        viewModel.username.value = "TESTE"
        viewModel.saveUsername()
        val observer = spy<Observer<Boolean>>()
        viewModel.usernameSaved.observeForever(observer)
        verify(observer).onChanged(eq(true))
    }


}