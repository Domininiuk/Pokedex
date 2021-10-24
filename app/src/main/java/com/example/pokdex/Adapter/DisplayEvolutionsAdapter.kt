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


class DisplayEvolutionsAdapter(private var names : List<String>, private val onItemClicked : (position: Int) -> Unit) : RecyclerView.Adapter<DisplayEvolutionsAdapter.EvolutionHolder>()
{

    class EvolutionHolder(itemView : View, private val onItemClicked: (position: Int) -> Unit) : RecyclerView.ViewHolder(itemView), View.OnClickListener
    {

        /*
        init {
            itemView.setOnClickListener(this)
        }
        
         */
        override fun onClick(p0: View?) {
            val position = absoluteAdapterPosition
            onItemClicked(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EvolutionHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recyclerview_display_pokemon_evolutions, parent, false)
        return EvolutionHolder(view, onItemClicked)
    }

    override fun onBindViewHolder(holder: EvolutionHolder, position: Int) {
        holder.itemView.display_evolution_name.text = Utility.capitalizeFirstCharacter(names[position])
    }

    override fun getItemCount(): Int {
        return names.size
    }
}