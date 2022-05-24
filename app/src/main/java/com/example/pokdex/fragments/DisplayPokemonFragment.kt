package com.example.pokdex.fragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokdex.adapters.AbilitiesAdapter
import com.example.pokdex.adapters.DisplayEvolutionsAdapter
import com.example.pokdex.Utility
import com.example.pokdex.viewmodels.DisplayPokemonViewModel
import com.example.pokdex.databinding.FragmentDisplayPokemonBinding
import com.squareup.picasso.Picasso
import com.synnapps.carouselview.ImageListener


class DisplayPokemonFragment : Fragment() {

    private val args : DisplayPokemonFragmentArgs by navArgs()
    private lateinit var displayPokemonVM : DisplayPokemonViewModel
    private lateinit var spritesUrls : List<String>
    private var _binding : FragmentDisplayPokemonBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        initializeBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        initializeMemberVariables()
        //Display the pokemon chosen on the previous fragment
        getAndDisplayPokemon(args.id)

        super.onViewCreated(view, savedInstanceState)
    }

    // Initialize as many variables as possible
    private fun initializeMemberVariables()
    {
        displayPokemonVM = DisplayPokemonViewModel()
    }

    private fun initializeBinding()
    {
        _binding = FragmentDisplayPokemonBinding.inflate(layoutInflater)
    }
    private fun getAndDisplayPokemon(id : Int)
    {
        binding.animationView.playAnimation()
        displayPokemonVM.getPokemon(id).observe(viewLifecycleOwner) { newPokemon ->
            // Delay because I want the cool animation to be visible :)
            Handler().postDelayed({
                displayPokemon()
                getPokemonSpecies(id)
            }, 500)
        }
    }
    private fun displayPokemon()
    {
        val url = displayPokemonVM.pokemon.value?.getOfficialArtworkFrontDefault()

        if(url != "")
        {
            spritesUrls = displayPokemonVM.pokemon.value?.sprites?.getListOfUrls()!!
            loadCarousel()
            binding.displayPokemonName.text = Utility.firstToUpper(displayPokemonVM.pokemon.value?.name.toString())
            binding.displayPokemonWeight.text = "Weight: "+  displayPokemonVM.pokemon.value?.getWeightInKilograms() + " kg"
            binding.displayPokemonExperience.text = "Base experience: " + displayPokemonVM.pokemon.value?.base_experience
            binding.displayPokemonHeight.text = "Height: " + displayPokemonVM.pokemon.value?.getHeightInCentimeters() + " cm"
            var types = displayPokemonVM.pokemon.value?.types

            if(types!!.size == 2)
            {
                binding.displayPokemonType.text = "Types: " + Utility.firstToUpper(displayPokemonVM.pokemon.value?.types!!.get(0).type.name) + ", " +
                        Utility.firstToUpper(displayPokemonVM.pokemon.value?.types!!.get(1).type.name)
            }
            else if(types!!.size == 1)
            {
                binding.displayPokemonType.text = "Type: " + Utility.firstToUpper(displayPokemonVM.pokemon.value?.types!!.get(0).type.name)

            }
            displayAbilitiesRecyclerView()

        }
        binding.animationView.visibility = View.GONE

    }

    private fun loadCarousel()
    {
        binding.carouselView.setImageListener(imageListener)
        binding.carouselView.pageCount = spritesUrls.size
    }
    private fun displayAbilitiesRecyclerView()
    {
        binding.displayPokemonAbilityRecyclerview.adapter = AbilitiesAdapter(displayPokemonVM.pokemon.value?.abilities!!)
        binding.displayPokemonAbilityRecyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }


    private fun getPokemonSpecies(id : Int)
    {
        displayPokemonVM.getPokemonSpecies(id).observe(viewLifecycleOwner) { pokemonSpecies ->
            getAndDisplayEvolutions(pokemonSpecies.evolution_chain.getChainId())
        }

    }
    private fun getAndDisplayEvolutions(id : Int)
    {
        displayPokemonVM.getEvolutionChain(id).observe(viewLifecycleOwner) { evolutionChain ->
            // Send requests for the pokemon data?
            displayEvolutions(evolutionChain.getListOfPokemonNames())
        }
    }

    private fun displayEvolutions(listOfEvolutions: List<String>)
    {
        binding.displayPokemonEvolutionsRecyclerview.adapter = DisplayEvolutionsAdapter(listOfEvolutions,
            displayPokemonVM.pokemon.value!!
        ) {
            position -> onEvolutionsItemClick(position)
        }
        binding.displayPokemonEvolutionsRecyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    }

    //When clicking on a pokemon, go their specific pokemon fragment
    private fun onEvolutionsItemClick(position : Int)
    {
        val id : Int = position + 1
       // val action =  DisplayAllPokemonFragmentDirections.actionDisplayAllPokemonFragmentToDisplayPokemonFragment(id)
       // findNavController().navigate(action)
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

}