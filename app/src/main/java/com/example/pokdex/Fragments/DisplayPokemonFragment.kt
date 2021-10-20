package com.example.pokdex.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Toast
import com.example.pokdex.Models.PokemonModel
import com.example.pokdex.R
import com.example.pokdex.ViewModel.DisplayPokemonViewModel
import com.squareup.picasso.Picasso
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokdex.Models.PokemonAbilityHolder
import com.example.pokdex.Utility
import com.google.common.base.Ascii.toUpperCase
import kotlinx.android.synthetic.main.fragment_display_pokemon.*
import kotlinx.android.synthetic.main.item_recyclerview_display_pokemon_ability.view.*


class DisplayPokemonFragment : Fragment() {

    private val args : DisplayPokemonFragmentArgs by navArgs()
    private lateinit var pokemon : PokemonModel
    private lateinit var displayPokemonVM : DisplayPokemonViewModel

    var   list : MutableList<PokemonModel> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_display_pokemon, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        initializeMemberVariables()
        getAndDisplayPokemon(args.id)

        super.onViewCreated(view, savedInstanceState)
    }
    // Initialize as many variables as possible
    private fun initializeMemberVariables()
    {
        displayPokemonVM = DisplayPokemonViewModel()
        pokemon = PokemonModel("")

    }
    private fun getAndDisplayPokemon(id : Int)
    {
        animationView.playAnimation()
        displayPokemonVM.getPokemon(id).observe(viewLifecycleOwner, {
                newPokemon ->
            pokemon = newPokemon
            list.add(pokemon)
            displayRandomPokemon()
        })
    }
    private fun displayRandomPokemon()
    {
        val url =  pokemon.getOfficialArtworkFrontDefault()

        if(url != "")
        {
            Picasso.get().load(url).into(display_pokemon_imageview)
            display_pokemon_name.text = Utility.capitalizeFirstCharacter(pokemon.name)
            display_pokemon_weight.text = "Weight: "+  pokemon.getWeightInKilograms() + " kg"
            display_pokemon_experience.text = "Base experience: " + pokemon.base_experience
            display_pokemon_height.text = "Height: " + pokemon.getHeightInCentimeters() + " cm"
            displayAbilitiesRecyclerView()
           // Toast.makeText()
        }
        animationView.visibility=View.GONE
    }

    private fun displayAbilitiesRecyclerView()
    {
        display_pokemon_ability_recyclerview.adapter = AbilitiesAdapter(pokemon.abilities)
        display_pokemon_ability_recyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }


    internal class AbilitiesAdapter(abilitiesList : List<PokemonAbilityHolder>) : RecyclerView.Adapter<AbilitiesAdapter.AbilityViewHolder>()
    {
       val  abilities : List<PokemonAbilityHolder> = abilitiesList
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



        internal class AbilityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
        {

        }
    }
}