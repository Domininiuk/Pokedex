package com.example.pokdex.fragments

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.pokdex.Utility
import com.example.pokdex.compose.PokedexTheme
import com.example.pokdex.compose.graySurface
import com.example.pokdex.models.*
import com.example.pokdex.viewmodels.PokemonDetailsViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState


@Composable
fun PokemonDetailsScreen(
    //pokemon: PokemonModel,
   // evolution: List<EvolutionSpeciesModel>,
  //  navController: NavController,
  //  abilities: List<PokemonAbilityModel>,
    viewModel: PokemonDetailsViewModel = hiltViewModel(), pokemonId: Int
)
{
    var viewModelState = remember {
        mutableStateOf(viewModel)
    }
   var pokemon1=  viewModelState.component1().getPokemon(pokemonId).observeAsState()
    var pokemonSpecies = viewModel.getPokemonSpecies(pokemonId).observeAsState()
    var evolutionChain = pokemonSpecies.value?.let {
        viewModel.getEvolutionChain(it.evolution_chain.getChainId()).observeAsState()
    }








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
                        pokemon1.value?.let { PokemonHeader(pokemon = it, containerHeight = this@BoxWithConstraints.maxHeight) }


                             // This crashes soemtimes
                        evolutionChain?.let {
                            if (pokemon1.value != null && evolutionChain.value != null) {
                                PokemonContent(
                                    pokemon = pokemon1.value!!,
                                    evolutionChain.value!!.getListOfPokemonNames()
                                )
                            }
                        }

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
                    .heightIn(max = containerHeight / 2, min = containerHeight/ 2)
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
fun PokemonContent(pokemon: PokemonModel, evolutions: List<EvolutionSpeciesModel>,
                  // abilities: List<PokemonAbilityModel>
)
{
    Title(pokemon = pokemon)

    PokemonTypes(pokemonTypes = pokemon.types)
    PokemonProperty(label = "Base experience gained:", value = pokemon.base_experience.toString() + " exp")
    PokemonProperty(label = "Height", value = pokemon.getHeightInCentimeters().toString() + " cm")
    PokemonProperty(label = "Weight", value =pokemon.getWeightInKilograms().toString() +" kg")
    //PokemonAbilityList(abilities = abilities)
    PokemonEvolutionChain(evolutions, pokemon.id)

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
fun PokemonEvolutionChain(evolution: List<EvolutionSpeciesModel>, pokemonId :Int)
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
              PokemonEvolution(evolution = it, pokemonId)
            }

    }
    }
}

@Composable
fun PokemonEvolution(evolution: EvolutionSpeciesModel, pokemonId: Int)
{
    Card(modifier = Modifier
        .padding(horizontal = 8.dp, vertical = 8.dp)
        .wrapContentWidth()
        .clickable {

            /*
            navController.navigate(
                DisplayPokemonFragmentDirections.actionDisplayPokemonFragmentSelf(
                    evolution.getIdFromUrl()
                )
            )


             */

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