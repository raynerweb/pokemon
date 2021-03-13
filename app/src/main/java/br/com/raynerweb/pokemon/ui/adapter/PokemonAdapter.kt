package br.com.raynerweb.pokemon.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.raynerweb.pokemon.databinding.ViewPokemonNamesBinding
import br.com.raynerweb.pokemon.domain.Pokemon
import coil.load

class PokemonAdapter(val pokemons: List<Pokemon>) :
    RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = ViewPokemonNamesBinding.inflate(LayoutInflater.from(parent.context))
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(pokemons[position])
    }

    override fun getItemCount() = pokemons.size

    inner class PokemonViewHolder(val binding: ViewPokemonNamesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pokemon: Pokemon) {
            binding.pokemon = pokemon
            binding.ivType.load(pokemon.image)
            binding.executePendingBindings()
        }
    }

}