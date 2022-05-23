package com.example.pokdex.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokdex.models.PokemonAbilityHolder
import com.example.pokdex.R
import com.example.pokdex.Utility
import com.example.pokdex.databinding.ItemRecyclerviewDisplayPokemonAbilityBinding

class AbilitiesAdapter(abilitiesList : List<PokemonAbilityHolder>) : RecyclerView.Adapter<AbilitiesAdapter.AbilityViewHolder>()
{
    private val  abilities : List<PokemonAbilityHolder> = abilitiesList
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AbilityViewHolder {



        val binding = ItemRecyclerviewDisplayPokemonAbilityBinding.inflate(LayoutInflater.from(parent.context))
        return AbilityViewHolder(binding.root, binding)

    }

    override fun onBindViewHolder(holder: AbilityViewHolder, position: Int)
    {
        holder.bind(Utility.capitalizeFirstCharacter(abilities[position].ability.name))
    }

    override fun getItemCount(): Int {
        return abilities.size
    }



    class AbilityViewHolder(itemView: View, private val binding: ItemRecyclerviewDisplayPokemonAbilityBinding) : RecyclerView.ViewHolder(itemView)
    {
        fun bind(abilityName : String)
        {
            binding.pokemonAbility.text = abilityName
          //  binding.
        }
    }
}