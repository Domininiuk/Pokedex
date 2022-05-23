package com.example.pokdex.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokdex.models.PokemonModel
import com.example.pokdex.R
import com.example.pokdex.Utility
import com.example.pokdex.databinding.ItemRecyclerviewDisplayPokemonEvolutionsBinding


class DisplayEvolutionsAdapter(private var names : List<String>, private var currentlyChosenPokemon :PokemonModel, private val onItemClicked : (position: Int) -> Unit) : RecyclerView.Adapter<DisplayEvolutionsAdapter.EvolutionHolder>()
{

    class EvolutionHolder(
        itemView: View,
        private val onItemClicked: (position: Int) -> Unit,
        private val binding: ItemRecyclerviewDisplayPokemonEvolutionsBinding
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener
    {

        public fun bind(evolutionName : String)
        {
            binding.displayEvolutionName.text = evolutionName
        }

        override fun onClick(p0: View?) {
            val position = absoluteAdapterPosition
            onItemClicked(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EvolutionHolder
    {
        val binding = ItemRecyclerviewDisplayPokemonEvolutionsBinding.inflate(LayoutInflater.from(parent.context))

        return EvolutionHolder(binding.root, onItemClicked, binding)
    }

    override fun onBindViewHolder(holder: EvolutionHolder, position: Int) {
        //If the names[position] == chosenPokemon.name
        //Then imageId == chosenPokemon.id + position
        holder.bind(Utility.capitalizeFirstCharacter(names[position]))
    }

    override fun getItemCount(): Int {
        return names.size
    }
}