package com.example.pokdex.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokdex.Models.PokemonAbilityHolder
import com.example.pokdex.R
import com.example.pokdex.Utility
import kotlinx.android.synthetic.main.item_recyclerview_display_pokemon_ability.view.*

 class AbilitiesAdapter(abilitiesList : List<PokemonAbilityHolder>) : RecyclerView.Adapter<AbilitiesAdapter.AbilityViewHolder>()
{
    private val  abilities : List<PokemonAbilityHolder> = abilitiesList
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AbilitiesAdapter.AbilityViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_recyclerview_display_pokemon_ability, parent, false)

        return AbilityViewHolder(view)
    }

    override fun onBindViewHolder(holder: AbilitiesAdapter.AbilityViewHolder, position: Int)
    {
        holder.itemView.pokemon_ability.text = Utility.capitalizeFirstCharacter(abilities[position].ability.name)
    }

    override fun getItemCount(): Int {
        return abilities.size
    }



    public class AbilityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {

    }
}