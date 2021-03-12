package br.com.raynerweb.pokemon.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.raynerweb.pokemon.R
import br.com.raynerweb.pokemon.databinding.FragmentPokemonTypeBinding

class PokemonTypeFragment : Fragment() {

    private lateinit var binding: FragmentPokemonTypeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokemonTypeBinding.inflate(inflater, container, false)
        binding.fragment = this
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvHello.text = getString(R.string.hello_trainer, "Ráyner")

    }

    fun next(view: View) {
        findNavController().navigate(R.id.action_pokemonTypeFragment_to_homeActivity)
    }

}