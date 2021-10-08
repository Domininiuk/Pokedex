package com.example.pokdex.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokdex.Models.PokemonModel
import com.example.pokdex.R
import com.example.pokdex.ViewModel.DisplayAllPokemonViewModel


class DisplayAllPokemonFragment : Fragment()
{
   private lateinit var displayAllPokemonVM : DisplayAllPokemonViewModel
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

        displayAllPokemonVM = DisplayAllPokemonViewModel()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_display_all_pokemon, container, false)
    }


    //Get an adapter of ALL pokemon
    //And then in the PokemonHolder download the rest of the data to display the pokemon on the screen?
}

class DisplayAllPokemonAdapter(list: List<PokemonModel>) :
    RecyclerView.Adapter<PokemonHolder>()

{
    //List of the displayed Pokemon
    var allPokemonList : List<PokemonModel> = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: PokemonHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return allPokemonList.size
    }

}

class PokemonHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
{

}