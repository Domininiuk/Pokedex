package com.example.pokdex.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokdex.Models.EvolutionModel
import com.example.pokdex.R

class DisplayEvolutionsAdapter(var evolutionChain : EvolutionModel) : RecyclerView.Adapter<DisplayEvolutionsAdapter.EvolutionHolder>()
{

    //Get a list of names?
    //And then download pictures the same way i do it in DisplayAllPokemonAdapter ( hardcode the url?)



    class EvolutionHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
    {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EvolutionHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recyclerview_display_pokemon_evolutions, parent, false)
        return EvolutionHolder(view)
    }

    override fun onBindViewHolder(holder: EvolutionHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}