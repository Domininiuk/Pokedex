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

    class EvolutionHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
    {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EvolutionHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recyclerview_display_pokemon_evolutions, parent, false)
        return EvolutionHolder(view)
    }

    override fun onBindViewHolder(holder: EvolutionHolder, position: Int) {
        holder.itemView.display_evolution_name.text = Utility.capitalizeFirstCharacter(names[position])
    }

    override fun getItemCount(): Int {
        return names.size
    }
}