package br.com.raynerweb.pokemon.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.raynerweb.pokemon.databinding.ViewPokemonTypesBinding
import br.com.raynerweb.pokemon.domain.PokemonType
import coil.load

class PokemonTypeAdapter(
    val pokemonTypes: List<PokemonType>
) :
    RecyclerView.Adapter<PokemonTypeAdapter.PokemonTypeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonTypeViewHolder {
        val binding = ViewPokemonTypesBinding.inflate(LayoutInflater.from(parent.context))
        return PokemonTypeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonTypeViewHolder, position: Int) {
        holder.bind(pokemonTypes[position])
    }

    override fun getItemCount() = pokemonTypes.size

    inner class PokemonTypeViewHolder(val binding: ViewPokemonTypesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pokemonType: PokemonType) {
            binding.pokemonType = pokemonType
            binding.ivType.load(pokemonType.image)
            binding.executePendingBindings()
        }
    }
}