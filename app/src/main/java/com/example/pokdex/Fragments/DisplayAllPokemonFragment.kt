package com.example.pokdex.Fragments


import android.app.ActionBar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup



import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokdex.Adapter.DisplayAllPokemonAdapter
import com.example.pokdex.Models.PokemonListModel
import com.example.pokdex.Models.PokemonModel
import com.example.pokdex.R
import com.example.pokdex.Utility
import com.example.pokdex.ViewModel.DisplayAllPokemonViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_display_all_pokemon.*
import kotlinx.android.synthetic.main.item_recyclerview_display_all.view.*
import java.lang.Character.toUpperCase


class DisplayAllPokemonFragment : Fragment() {
    lateinit var displayAllPokemonVM: DisplayAllPokemonViewModel
    lateinit var pokemonList : PokemonListModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {

            initializeVariables()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_display_all_pokemon, container, false)
    }


    //Initialize member variables
    private fun initializeVariables() {
        displayAllPokemonVM = DisplayAllPokemonViewModel()
        displayAllPokemonVM.pokemonList.observe(viewLifecycleOwner, {newList ->
            pokemonList = newList
            displayRecyclerView()
        })
    }


    private fun displayRecyclerView()
    {
        display_all_recyclerview.adapter = DisplayAllPokemonAdapter(pokemonList) { position ->
            onListItemClick(
                position
            )
        }
        display_all_recyclerview.layoutManager = GridLayoutManager(context, 2)
    }

    //When clicking on a pokemon, go their specific pokemon fragment
    private fun onListItemClick(position : Int)
    {
        val id : Int = position + 1
        val action =  DisplayAllPokemonFragmentDirections.actionDisplayAllPokemonFragmentToDisplayPokemonFragment(id)
        findNavController().navigate(action)
    }

}

