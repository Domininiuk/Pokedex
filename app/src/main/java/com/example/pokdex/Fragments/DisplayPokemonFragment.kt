package com.example.pokdex.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pokdex.Models.PokemonModel
import com.example.pokdex.R
import com.example.pokdex.ViewModel.DisplayPokemonViewModel
import com.squareup.picasso.Picasso
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_display_pokemon.*

class DisplayPokemonFragment : Fragment() {

    private val args : DisplayPokemonFragmentArgs by navArgs()
    private lateinit var pokemon : PokemonModel
    private lateinit var displayPokemonVM : DisplayPokemonViewModel
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
        pokemon = PokemonModel()
    }
    private fun getAndDisplayPokemon(id : Int)
    {
        animationView.playAnimation()
        pokemon = displayPokemonVM.getPokemon(id).value!!
        displayPokemonVM.pokemon.observe(viewLifecycleOwner, {
                newPokemon ->
            pokemon = newPokemon
            displayRandomPokemon()
        })

    }
    private fun displayRandomPokemon()
    {
        val url =  pokemon.getOfficialArtworkFrontDefault()

        if(url != "")
        {
            Picasso.get().load(url).into(display_pokemon_imageview)
            display_pokemon_name.text = pokemon.name

        }
        animationView.visibility=View.GONE
    }


}