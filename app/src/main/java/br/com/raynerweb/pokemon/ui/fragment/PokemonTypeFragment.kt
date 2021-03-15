package br.com.raynerweb.pokemon.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.raynerweb.pokemon.R
import br.com.raynerweb.pokemon.databinding.FragmentPokemonTypeBinding
import br.com.raynerweb.pokemon.domain.PokemonType
import br.com.raynerweb.pokemon.ui.adapter.PokemonTypeArrayAdapter
import br.com.raynerweb.pokemon.viewmodel.PokemonTypeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonTypeFragment : Fragment() {

    private lateinit var binding: FragmentPokemonTypeBinding
    private val viewModel: PokemonTypeViewModel by viewModels()
    private lateinit var typeAdapter: PokemonTypeArrayAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokemonTypeBinding.inflate(inflater, container, false)
        binding.fragment = this
        binding.vm = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.init()

        viewModel.username.observe(viewLifecycleOwner, { username ->
            binding.tvHello.text = getString(R.string.hello_trainer, username)
        })

        viewModel.pokemonTypesState.observe(viewLifecycleOwner, {
            setupTypesAdapter(it)
        })

        viewModel.pokemonTypeSaved.observe(viewLifecycleOwner, {
            findNavController().navigate(R.id.action_pokemonTypeFragment_to_homeActivity)
        })

    }

    private fun setupTypesAdapter(types: List<PokemonType>) {
        typeAdapter = PokemonTypeArrayAdapter(
            requireContext(),
            R.layout.view_spinner_pokemon_types,
            types
        )
        binding.spPokemonType.adapter = typeAdapter
    }

    fun next(view: View) {
        viewModel.saveSelectedPokemonType(binding.spPokemonType.selectedItem as PokemonType)
    }

}