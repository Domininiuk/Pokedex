package com.example.pokdex.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokdex.Models.PokemonListModel
import com.example.pokdex.Models.PokemonModel
import com.example.pokdex.R
import com.example.pokdex.Utility
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_recyclerview_display_all.view.*

class DisplayAllPokemonAdapter(list : PokemonListModel, private val onItemClicked : (position: Int) -> Unit) :
    RecyclerView.Adapter<DisplayAllPokemonAdapter.PokemonHolder>() {
    private val imageUrl =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"
    private val pokemonList = list
    private val results = pokemonList.results
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_recyclerview_display_all,
            parent, false
        )

        return PokemonHolder(view, onItemClicked)

    }

    override fun onBindViewHolder(holder: PokemonHolder, position: Int) {

        holder.bind(results[position], position.plus(1), imageUrl)

    }

    override fun getItemCount(): Int {
        return results.size
    }


    public class PokemonHolder(itemView: View, private val onItemClicked: (position: Int) -> Unit) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        fun bind(pokemonModel: PokemonModel, position: Int, url: String) {
            itemView.display_all_pokemon_name.text =
                Utility.capitalizeFirstCharacter(pokemonModel.name)
            Picasso.get().load(url + position + ".png").into(itemView.display_all_pokemon_image)
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