package com.example.pokdex.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.pokdex.Models.PokemonModel
import com.example.pokdex.R
import com.example.pokdex.ViewModel.DisplayPokemonViewModel
import com.squareup.picasso.Picasso
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokdex.Adapter.AbilitiesAdapter
import com.example.pokdex.Adapter.DisplayEvolutionsAdapter
import com.example.pokdex.Models.EvolutionModel
import com.example.pokdex.Utility
import com.synnapps.carouselview.ImageListener
import kotlinx.android.synthetic.main.fragment_display_pokemon.*


class DisplayPokemonFragment : Fragment() {

    private val args : DisplayPokemonFragmentArgs by navArgs()
    private lateinit var pokemon : PokemonModel
    private lateinit var displayPokemonVM : DisplayPokemonViewModel
    private lateinit var spritesUrls : List<String>

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
        //Display the pokemon chosen on the previous fragment
        getAndDisplayPokemon(args.id)
        getAndDisplayEvolutions(args.id)
        super.onViewCreated(view, savedInstanceState)
    }
    // Initialize as many variables as possible
    private fun initializeMemberVariables()
    {
        displayPokemonVM = DisplayPokemonViewModel()
        pokemon = PokemonModel("")

    }

    private fun getAndDisplayEvolutions(id : Int)
    {
        var c : EvolutionModel
        var listOfEvolutions  = listOf<String>()
        displayPokemonVM.getEvolutionChain(id).observe(viewLifecycleOwner, {
                evolutionChain ->
            c = evolutionChain
            listOfEvolutions = c.getListOfPokemonNames()
            display_pokemon_evolutions_recyclerview.adapter = DisplayEvolutionsAdapter(listOfEvolutions)
            display_pokemon_evolutions_recyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        })
    }

    private fun displayEvolutions()
    {


    }
    private fun getAndDisplayPokemon(id : Int)
    {
        animationView.playAnimation()
        displayPokemonVM.getPokemon(id).observe(viewLifecycleOwner, {
                newPokemon ->
            pokemon = newPokemon
            displayPokemon()


        })
    }
    private fun displayPokemon()
    {
        val url =  pokemon.getOfficialArtworkFrontDefault()


        if(url != "")
        {
            spritesUrls = pokemon.sprites?.getListOfUrls()!!
            loadCarousel()
            display_pokemon_name.text = Utility.capitalizeFirstCharacter(pokemon.name)
            display_pokemon_weight.text = "Weight: "+  pokemon.getWeightInKilograms() + " kg"
            display_pokemon_experience.text = "Base experience: " + pokemon.base_experience
            display_pokemon_height.text = "Height: " + pokemon.getHeightInCentimeters() + " cm"
            displayAbilitiesRecyclerView()
           // Toast.makeText()
        }
        animationView.visibility=View.GONE
    }

    fun loadCarousel()
    {
        carouselView.setImageListener(imageListener)
        carouselView.pageCount = spritesUrls.size
    }
    //Image listener for the carousel
    var imageListener: ImageListener = object : ImageListener {
        override fun setImageForPosition(position: Int, imageView: ImageView?) {
            if (imageView != null) {
                loadImageView(spritesUrls[position], imageView)
            }
        }
    }
    private fun loadImageView(url : String, imageView : ImageView)
    {
        Picasso.get().load(url).into(imageView)
    }

    private fun displayAbilitiesRecyclerView()
    {
        display_pokemon_ability_recyclerview.adapter = AbilitiesAdapter(pokemon.abilities)
        display_pokemon_ability_recyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }


}