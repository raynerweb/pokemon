package br.com.raynerweb.pokemon.ui.fragment

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.raynerweb.pokemon.R
import br.com.raynerweb.pokemon.databinding.FragmentHomeBinding
import br.com.raynerweb.pokemon.repository.local.entity.SortSelect
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

        setHasOptionsMenu(true)

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(br.com.raynerweb.pokemon.R.menu.menu_home, menu)
        val searchItem: MenuItem = menu.findItem(br.com.raynerweb.pokemon.R.id.action_search)
        val searchManager =
            requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = searchItem.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))

        searchView.apply {
            maxWidth = Integer.MAX_VALUE
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    viewModel.filter(newText)
                    return true
                }
            })
        }

    }

    fun sortByName(view: View) {
        viewModel.sort()
    }

    private fun subscribe() {

        viewModel.pokemonTypesState.observe(viewLifecycleOwner, {
            binding.rvTypes.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.rvTypes.adapter = PokemonTypeAdapter(it) { pokemonType ->
                viewModel.changePokemonType(pokemonType = pokemonType)
            }
        })

        viewModel.sortSelectState.observe(viewLifecycleOwner, {
            if (it == SortSelect.ASC) {
                binding.btnSort.setImageResource(R.drawable.ic_baseline_arrow_downward_24)
            } else {
                binding.btnSort.setImageResource(R.drawable.ic_baseline_arrow_upward_24)
            }
        })

        viewModel.pokemonsState.observe(viewLifecycleOwner, {
            binding.rvPokemons.layoutManager = LinearLayoutManager(requireContext())
            binding.rvPokemons.adapter = PokemonAdapter(it)
        })

        viewModel.errorState.observe(viewLifecycleOwner, {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        })

    }

}