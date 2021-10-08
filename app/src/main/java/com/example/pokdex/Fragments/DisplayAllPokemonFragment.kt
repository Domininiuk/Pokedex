package com.example.pokdex.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokdex.Models.AllPokemonModel
import com.example.pokdex.Models.PokemonModel
import com.example.pokdex.R
import com.example.pokdex.ViewModel.DisplayAllPokemonViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_display_all_pokemon.*
import kotlinx.android.synthetic.main.item_recyclerview_display_all.view.*


class DisplayAllPokemonFragment : Fragment()
{

   private lateinit var displayAllPokemonVM : DisplayAllPokemonViewModel
   private lateinit var allPokemon : AllPokemonModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getContext(): Context? {
        return super.getContext()
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
        displayAllPokemonVM = DisplayAllPokemonViewModel()
        allPokemon = AllPokemonModel()

    }

    private fun displayRecyclerView()
    {
        allPokemon = displayAllPokemonVM.getAllPokemon().value!!
        displayAllPokemonVM.allPokemonModel.observe(viewLifecycleOwner, { newAllPokemon ->
            if(newAllPokemon.results.size > 1) {
                allPokemon = newAllPokemon
                display_all_recyclerview.adapter = DisplayAllPokemonAdapter(allPokemon)
                display_all_recyclerview.layoutManager = LinearLayoutManager(context)
                //  display_all_recyclerview.setrecy
            }
    })
}
    //Get an adapter of ALL pokemon
    //And then in the PokemonHolder download the rest of the data to display the pokemon on the screen?
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
        holder.itemView.display_all_pokemon_name.text = pokemonModel.name

        //ERROR: PATH MUST NOT BE MPTY
        //ADD A GETFRONTARTWORKURL METHOD TO THE VIEWMODEL?
        //Save the ur0l in the list? and redownload iamges every time an item shows up?
        Picasso.get().load(pokemonModel.getOfficialArtworkFrontDefault()).
        into(holder.itemView.display_all_pokemon_image)

    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

}

class PokemonHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
{
    lateinit var pokemon : PokemonModel

    fun downloadPokemonData(id : Int)
    {

    }
}