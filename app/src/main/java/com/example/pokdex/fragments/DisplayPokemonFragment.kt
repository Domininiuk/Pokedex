package com.example.pokdex.fragments

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.compose.rememberAsyncImagePainter
import com.example.pokdex.adapters.AbilitiesAdapter
import com.example.pokdex.adapters.DisplayEvolutionsAdapter
import com.example.pokdex.Utility
import com.example.pokdex.compose.PokedexTheme
import com.example.pokdex.viewmodels.DisplayPokemonViewModel
import com.example.pokdex.databinding.FragmentDisplayPokemonBinding
import com.example.pokdex.models.PokemonModel
import com.example.pokdex.viewmodels.DisplayAllPokemonViewModel
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
        displayPokemonVM = DisplayPokemonViewModel()

       // initializeMemberVariables()
        //Display the pokemon chosen on the previous fragment
       // getAndDisplayPokemon(args.id)

        binding.composeView.apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner)
            )

            displayPokemonVM.getPokemon(args.id).observe(viewLifecycleOwner) { newPokemon ->
                setContent { DisplayPokemonScreen(pokemon = newPokemon) }
            }
        }
/*
        binding.composeView.apply { setViewCompositionStrategy(
            ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner))
        displayPokemonVM = DisplayPokemonViewModel()


            displayPokemonVM.getPokemon(id).observe(viewLifecycleOwner) { pokemon ->
                //   pokemonList = newList
                Log.d("DisplayPokemon", "SetContent")
                setContent { DisplayPokemonScreen(pokemon) }
            }



        }

 */



        return binding.root
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
            Log.d("DisplayPokemon", "SetContent")
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


@Composable
fun DisplayPokemonScreen(pokemon: PokemonModel)
{
    val scrollState = rememberScrollState()


    PokedexTheme() {
        val scrollState = rememberScrollState()
        Column(modifier = Modifier.fillMaxSize())
        {
            BoxWithConstraints{
                Surface{
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)) {
                        PokemonHeader(pokemon = pokemon, containerHeight = this@BoxWithConstraints.maxHeight)
                    }
                }
            }


        }



    }
}


@Composable
fun PokemonHeader(
    pokemon: PokemonModel, containerHeight : Dp)
{

    // This will be changed to a carouselview
    var url : String = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/" +pokemon.id +".png"

    Image(
        painter = rememberAsyncImagePainter(url),
        contentDescription = null,
        modifier = Modifier
            .heightIn(max = containerHeight / 2)
            .fillMaxWidth(),
        contentScale = ContentScale.Crop
    )
    PokemonContent(pokemon = pokemon)
}
@Composable
fun PokemonContent(pokemon: PokemonModel)
{
    Title(pokemon = pokemon)
    if(pokemon.types.size == 2)
    {
        val firstType = Utility.firstToUpper(pokemon.types[0].type.name)
        val secondType = Utility.firstToUpper(pokemon.types[1].type.name)

        PokemonPropety(label = "Types:", value =
        "$firstType, $secondType"
        )
    }
    else {
        val firstType = Utility.firstToUpper(pokemon.types[0].type.name)

        PokemonPropety(label = "Types:", value =
        firstType
        )
    }
    PokemonPropety(label = "Base experience gained:", value = pokemon.base_experience.toString())
    PokemonPropety(label = "Height", value = pokemon.height.toString())
    PokemonPropety(label = "Weight", value =pokemon.weight.toString())

}
@Composable
fun Title(pokemon: PokemonModel)
{
    Column(modifier = Modifier.padding(all = 16.dp)) {
        Text(text = Utility.firstToUpper(pokemon.name),
        style = MaterialTheme.typography.h5,
        fontWeight = FontWeight.Bold)
        
    }
}

@Composable
fun PokemonPropety(label: String, value: String)
{
    Column(modifier = Modifier.padding(top = 8.dp, bottom = 4.dp, start =16.dp, end = 16.dp)) {
        Divider(modifier = Modifier.padding(bottom = 4.dp))
        Text(text = label, modifier = Modifier.height(24.dp),
        style = MaterialTheme.typography.caption)
        Text(text = value,
        modifier = Modifier.height(24.dp),
        style = MaterialTheme.typography.body1,
        overflow = TextOverflow.Visible)

    }
}
@Composable
fun Pokemon(pokemon: PokemonModel)
{
    val scrollState = rememberScrollState()
    
    Column() {
        Text(text = pokemon.name)
    }

}