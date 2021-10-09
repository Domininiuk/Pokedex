package com.example.pokdex.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokdex.Models.AllPokemonModel
import com.example.pokdex.Models.PokemonModel
import com.example.pokdex.PokemonRepository
import com.example.pokdex.R
import com.example.pokdex.ViewModel.DisplayAllPokemonViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_display_all_pokemon.*
import kotlinx.android.synthetic.main.item_recyclerview_display_all.view.*


class DisplayAllPokemonFragment : Fragment()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        initializeVariables()
        displayRecyclerView()

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_display_all_pokemon, container, false)
    }

    //Initialize member variables
    private fun initializeVariables()
    {
    }

    private fun displayRecyclerView() {
    }


}

class DisplayAllPokemonAdapter(allpokemon : AllPokemonModel) :
    RecyclerView.Adapter<PokemonHolder>()

{
    //List of the displayed Pokemon
    private var allPokemon = allpokemon
    var pokemonList = allPokemon.results

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recyclerview_display_all,
        parent, false)

        return PokemonHolder(view)

    }

    override fun onBindViewHolder(holder: PokemonHolder, position: Int) {
        val pokemonModel = pokemonList[position]
        holder.bind(pokemonModel)



    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

}

class PokemonHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
{
    fun bind(id : PokemonModel)
    {
        itemView.display_all_pokemon_name.text = id.name
    }
}