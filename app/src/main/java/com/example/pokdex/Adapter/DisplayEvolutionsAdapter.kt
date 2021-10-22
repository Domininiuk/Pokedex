package com.example.pokdex.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokdex.Models.EvolutionModel
import com.example.pokdex.R
import com.example.pokdex.Utility
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_recyclerview_display_all.view.*
import kotlinx.android.synthetic.main.item_recyclerview_display_pokemon_evolutions.view.*


class DisplayEvolutionsAdapter(var names : List<String>) : RecyclerView.Adapter<DisplayEvolutionsAdapter.EvolutionHolder>()
{

    //Get a list of names?
    //And then download pictures the same way i do it in DisplayAllPokemonAdapter ( hardcode the url?)
    var url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"


    class EvolutionHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
    {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EvolutionHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recyclerview_display_pokemon_evolutions, parent, false)
        return EvolutionHolder(view)
    }

    override fun onBindViewHolder(holder: EvolutionHolder, position: Int) {
        holder.itemView.display_evolution_name.text = names[position]
         //   Utility.capitalizeFirstCharacter(pokemonModel.name)

        //get a way to get id of first pokemon
        Picasso.get().load(url + position + ".png").into(holder.itemView.display_evolution_image)
    }

    override fun getItemCount(): Int {
        return names.size
    }
}