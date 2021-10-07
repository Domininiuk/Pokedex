package com.example.pokdex.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import com.example.pokdex.Models.PokemonModel
import com.example.pokdex.R
import com.example.pokdex.ViewModel.RandomPokemonViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_random_pokemon.*
import androidx.lifecycle.Observer
class RandomPokemonFragment : Fragment() {

    lateinit var pokemon : PokemonModel
    lateinit var randomPokemonVM : RandomPokemonViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_random_pokemon, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        initializeMemberVariables()
        getAndDisplayRandomPokemon()

        super.onViewCreated(view, savedInstanceState)
    }
    // Initialize as many variables as possible
    private fun initializeMemberVariables()
    {
        randomPokemonVM = RandomPokemonViewModel()
        pokemon = PokemonModel()
    }
    private fun getAndDisplayRandomPokemon()
    {
        animationView.playAnimation()
        pokemon = randomPokemonVM.getRandomPokemon().value!!
        randomPokemonVM.pokemon.observe(viewLifecycleOwner, Observer {
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
            Picasso.get().load(url).into(random_pokemon_imageview)
            random_pokemon_name.text = pokemon.name

        }
        animationView.visibility=View.GONE
    }


}