package com.example.pokdex.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokdex.models.PokemonListModel
import com.example.pokdex.models.PokemonModel
import com.example.pokdex.Utility
import com.example.pokdex.databinding.ItemRecyclerviewDisplayAllBinding
import com.squareup.picasso.Picasso

class DisplayAllPokemonAdapter(list : PokemonListModel, private val onItemClicked : (position: Int) -> Unit) :
    RecyclerView.Adapter<DisplayAllPokemonAdapter.PokemonHolder>() {
    private val imageUrl =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"
    private val pokemonList = list
    private val results = pokemonList.results
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonHolder {

        val binding = ItemRecyclerviewDisplayAllBinding.inflate(LayoutInflater.from(parent.context))

        return PokemonHolder(binding.root, onItemClicked, binding)

    }

    override fun onBindViewHolder(holder: PokemonHolder, position: Int) {

        holder.bind(results[position], position.plus(1), imageUrl)

    }

    override fun getItemCount(): Int {
        return results.size
    }


    class PokemonHolder(
        itemView: View,
        private val onItemClicked: (position: Int) -> Unit,
        val binding: ItemRecyclerviewDisplayAllBinding
    ) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        fun bind(pokemonModel: PokemonModel, position: Int, url: String) {
            binding.displayAllPokemonName.text =
                Utility.firstToUpper(pokemonModel.name)
            Picasso.get().load(url + position + ".png").into(binding.displayAllPokemonImage)
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = absoluteAdapterPosition
            onItemClicked(position)
        }


    }
}