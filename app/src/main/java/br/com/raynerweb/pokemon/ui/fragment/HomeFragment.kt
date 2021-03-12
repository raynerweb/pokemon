package br.com.raynerweb.pokemon.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.raynerweb.pokemon.databinding.FragmentHomeBinding
import br.com.raynerweb.pokemon.ui.adapter.PokemonAdapter
import br.com.raynerweb.pokemon.ui.adapter.PokemonTypeAdapter
import br.com.raynerweb.pokemon.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.fragment = this
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribe()

        viewModel.pokemonTypes()
        viewModel.pokemons()
    }

    private fun subscribe() {

        viewModel.pokemonsState.observe(viewLifecycleOwner, {
            binding.rvPokemons.layoutManager = LinearLayoutManager(requireContext())
            binding.rvPokemons.adapter = PokemonAdapter(it)
        })

        viewModel.pokemonTypesState.observe(viewLifecycleOwner, {
            binding.rvTypes.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.rvTypes.adapter = PokemonTypeAdapter(it)
        })

        viewModel.errorState.observe(viewLifecycleOwner, {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        })

    }

}