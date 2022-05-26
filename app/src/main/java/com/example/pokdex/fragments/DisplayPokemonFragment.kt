package com.example.pokdex.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.pokdex.Utility
import com.example.pokdex.compose.PokedexTheme
import com.example.pokdex.compose.graySurface
import com.example.pokdex.databinding.FragmentDisplayPokemonBinding
import com.example.pokdex.models.*
import com.example.pokdex.viewmodels.DisplayPokemonViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState


class DisplayPokemonFragment : Fragment() {

    private val args: DisplayPokemonFragmentArgs by navArgs()
    private lateinit var displayPokemonVM: DisplayPokemonViewModel
    private lateinit var spritesUrls: List<String>
    private var _binding: FragmentDisplayPokemonBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        initializeBinding()
        initializeViewModel()

        // initializeMemberVariables()
        //Display the pokemon chosen on the previous fragment
        // getAndDisplayPokemon(args.id)

        binding.composeView.apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner)
            )

            displayPokemonVM.getPokemon(args.id).observe(viewLifecycleOwner) { newPokemon ->
                displayPokemonVM.getPokemonSpecies(args.id)
                    .observe(viewLifecycleOwner) { pokemonSpecies ->
                        displayPokemonVM.getEvolutionChain(pokemonSpecies.evolution_chain.getChainId())
                            .observe(viewLifecycleOwner) { evolutionChain ->
                                 var abilities = mutableListOf<PokemonAbilityModel>()
                                 var ids = newPokemon.getListOfAbilityIds()
                                for(id in ids)
                                {
                                    displayPokemonVM.getPokemonAbility(id).observe(viewLifecycleOwner)
                                    {
                                        abilities.add(it)
                                        if(abilities.size == ids.size)
                                        {

                                            setContent {

                                                DisplayPokemonScreen(
                                                    pokemon = newPokemon,
                                                    evolutionChain.getListOfPokemonNames(),
                                                    navController = findNavController(),
                                                    abilities.sortedBy { ability -> ability.name }

                                                )
                                            }
                                        }
                                    }
                                }

                                }


                    }


            }
        }


        return binding.root
    }

    // Initialize as many variables as possible
    private fun initializeViewModel() {
        displayPokemonVM = DisplayPokemonViewModel()
    }

    private fun initializeBinding() {
        _binding = FragmentDisplayPokemonBinding.inflate(layoutInflater)
    }


}
@Composable
fun DisplayPokemonScreen(pokemon: PokemonModel, evolution: List<EvolutionSpeciesModel>, navController: NavController,
                         abilities: List<PokemonAbilityModel>)
{

    PokedexTheme() {
        val scrollState = rememberScrollState()
        TopAppBar(
            title = { Text(text = "AppBar") },
            navigationIcon = {
            }
        )
        Column(modifier = Modifier.fillMaxSize())
        {
            BoxWithConstraints{
                Surface{
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)) {
                        PokemonHeader(pokemon = pokemon, containerHeight = this@BoxWithConstraints.maxHeight)
                        PokemonContent(pokemon = pokemon, evolution = evolution, navController,abilities)

                    }
                }
            }


        }



    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun PokemonHeader(
    pokemon: PokemonModel, containerHeight : Dp)
{

    // Im pretty sure is a major performance sink because all the images are loaded at once

    var urls = pokemon.sprites!!.getListOfUrls()
    var state = rememberPagerState()
    Column(Modifier.fillMaxWidth())
    {
        HorizontalPager(count = urls.size, state = state) { page ->
            Card(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .fillMaxWidth()
                    .heightIn(max = containerHeight / 2)
                ,
                elevation = 2.dp,
                shape = RoundedCornerShape(corner = CornerSize(16.dp))
            )
            {

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(urls[page])
                        .decoderFactory(SvgDecoder.Factory())
                        .build(),
                    contentDescription = null
                )

            }


        }
        HorizontalPagerIndicator(
            pagerState = state,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
        )
    }




}
@Composable
fun PokemonContent(pokemon: PokemonModel, evolution: List<EvolutionSpeciesModel>, navController: NavController, abilities: List<PokemonAbilityModel>)
{
    Title(pokemon = pokemon)

    PokemonTypes(pokemonTypes = pokemon.types)
    PokemonProperty(label = "Base experience gained:", value = pokemon.base_experience.toString() + " exp")
    PokemonProperty(label = "Height", value = pokemon.getHeightInCentimeters().toString() + " cm")
    PokemonProperty(label = "Weight", value =pokemon.getWeightInKilograms().toString() +" kg")
    PokemonAbilityList(abilities = abilities)
    PokemonEvolutionChain(evolution, pokemon.id, navController)

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
fun PokemonTypes(pokemonTypes: List<PokemonTypeHolder>)
{
    Column(modifier = Modifier.padding(top = 8.dp, bottom = 4.dp, start =16.dp, end = 16.dp)) {
        Divider(modifier = Modifier.padding(bottom = 4.dp))
        Text(text = "Types:", modifier = Modifier.height(24.dp),
            style = MaterialTheme.typography.caption)

        Row() {
            PokemonType(type = Utility.firstToUpper(pokemonTypes[0].type.name), modifier = Modifier.padding(bottom = 4.dp, end = 10.dp), pokemonTypes[0].type.getColour())
            if(pokemonTypes.size == 2)
            {
                PokemonType(type = Utility.firstToUpper(pokemonTypes[1].type.name), modifier = Modifier.padding(start = 4.dp), backGroundColor = pokemonTypes[1].type.getColour())
            }
        }
    }
}
@Composable
fun PokemonType(type : String, modifier: Modifier = Modifier,
                backGroundColor: Color = MaterialTheme.colors.surface,)
{
    Card(
        elevation = 2.dp,
        backgroundColor = backGroundColor,
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
    ) {
        Text(
            text = type,
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .padding(vertical = 2.dp)
                .padding(horizontal = 8.dp)
        )
    }
}
@Composable
fun PokemonProperty(label: String, value: String)
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
fun PokemonAbilityList(abilities: List<PokemonAbilityModel>)
{

    Column(modifier = Modifier.padding(top = 8.dp, bottom = 4.dp, start =16.dp, end = 16.dp)) 
    {
        Divider(modifier = Modifier.padding(bottom = 4.dp))
        Text(text = "Abilities:", modifier = Modifier.height(24.dp),
            style = MaterialTheme.typography.caption)

        
        Row(
            Modifier
                .wrapContentSize()
                .horizontalScroll(rememberScrollState())){
            abilities.forEach(){
                    PokemonAbility(it, Modifier.padding(end = 10.dp))
            }
            
        }
            
    }
}

@Composable
fun PokemonAbility(pokemonAbility: PokemonAbilityModel, modifier: Modifier = Modifier)
{
    var isExpanded by remember{ mutableStateOf(false)}
    val  context = LocalContext.current
    Card(
        elevation = 50.dp,
        backgroundColor = graySurface,
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .widthIn(0.dp, 300.dp)
            .clickable { //Toast.makeText(context, pokemonAbility.effect_entries[1].effect, Toast.LENGTH_LONG).show()
                isExpanded = !isExpanded
            },
    ) {
        Column() {


            Text(
                modifier = Modifier
                    .padding(vertical = 2.dp)
                    .padding(horizontal = 8.dp),
                text = Utility.firstToUpper(pokemonAbility.name),
                style = MaterialTheme.typography.body1,
                overflow = TextOverflow.Visible

            )
            if (isExpanded) {
                Text(
                    modifier = Modifier
                        .padding(vertical = 2.dp)
                        .padding(horizontal = 8.dp),
                    text = pokemonAbility.getEnglishVersion().effect,
                    style = MaterialTheme.typography.body1,
                    overflow = TextOverflow.Visible

                )
            }
        }
        }




}


@Composable
fun PokemonEvolutionChain(evolution: List<EvolutionSpeciesModel>, pokemonId :Int, navController: NavController)
{

    Column(modifier = Modifier.padding(top = 8.dp, bottom = 4.dp, start =16.dp, end = 16.dp)) {
        Divider(modifier = Modifier.padding(bottom = 4.dp))
        Text(text = "Evolutions:", modifier = Modifier.height(24.dp),
            style = MaterialTheme.typography.caption)
        Row(
            Modifier
                .wrapContentSize()
                .horizontalScroll(rememberScrollState())){
            evolution.forEach(){
              PokemonEvolution(evolution = it, pokemonId, navController)
            }

    }
    }
}

@Composable
fun PokemonEvolution(evolution: EvolutionSpeciesModel, pokemonId: Int, navController: NavController)
{
    Card(modifier = Modifier
        .padding(horizontal = 8.dp, vertical = 8.dp)
        .wrapContentWidth()
        .clickable {
            /*
            var currentId = evolution.getIdFromUrl()
            if(currentId == pokemonId)
            {

            }
            else
            {
                navController.navigate(DisplayPokemonFragmentDirections.actionDisplayPokemonFragmentSelf(currentId))
            }

             */
            navController.navigate(
                DisplayPokemonFragmentDirections.actionDisplayPokemonFragmentSelf(
                    evolution.getIdFromUrl()
                )
            )

        },
        elevation = 2.dp,
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    )
    {
    Column(){
        val url : String = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"+
                evolution.getIdFromUrl()+".png"

        Image(
            painter = rememberAsyncImagePainter(url),
            contentDescription =
            null,
            modifier = Modifier.size(128.dp)
        )
        Text(modifier = Modifier
            .padding(end = 4.dp)
            .align(Alignment.CenterHorizontally), text = Utility.firstToUpper(evolution.name), style = MaterialTheme.typography.body1, overflow = TextOverflow.Visible)
        //Text(text = "" + "Lorem ipsum, testing how a description would look like")

    }}
}