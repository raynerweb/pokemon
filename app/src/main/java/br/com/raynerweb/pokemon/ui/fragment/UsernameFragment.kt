package br.com.raynerweb.pokemon.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.raynerweb.pokemon.R
import br.com.raynerweb.pokemon.databinding.FragmentUsernameBinding

class UsernameFragment : Fragment() {

    private lateinit var binding: FragmentUsernameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUsernameBinding.inflate(inflater, container, false)
        binding.fragment = this
        binding.lifecycleOwner = this
        return binding.root
    }

    fun next(view: View) {
        findNavController().navigate(R.id.action_usernameFragment_to_pokemonTypeFragment)
    }

}