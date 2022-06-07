package com.example.pokdex.composables

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
    viewModel: PokemonDetailsViewModel = hiltViewModel(), pokemonIdParam: Int, navigateToPokemon: (id: Int) -> Unit,
    onBackButtonPressed: () ->Unit)
{
    var pokemonId = pokemonIdParam
    var viewModelState = remember {
        mutableStateOf(viewModel)
    }
    var pokemon1=  viewModelState.component1().getPokemon(pokemonId).observeAsState()
    if(pokemonId > 10000)
    {
        pokemonId -= 9600
    }
    var pokemonSpecies = viewModel.getPokemonSpecies(pokemonId).observeAsState()
    var evolutionChain = pokemonSpecies.value?.let {
        viewModel.getEvolutionChain(it.evolution_chain.getChainId()).observeAsState()
    }


    var pokemonLoaded = remember { mutableStateOf(false, neverEqualPolicy()) }

                PokedexTheme {
        val scrollState = rememberScrollState()

                    Scaffold(topBar = { PokemonDetailsTopAppBar(onBackButtonPressed)

                    })
                    {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it),
                        )
                        {
                            BoxWithConstraints {
                                Surface {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .verticalScroll(scrollState)
                                    ) {
                                        if (!pokemonLoaded.value) {
                                            LottieLoadingAnimation()

                                        }
                                        if ((evolutionChain != null) && (pokemon1 != null)) {
                                            if ((pokemon1.value != null) && (evolutionChain.value != null)) {
                                                var ids =
                                                    remember { pokemon1.value!!.getListOfAbilityIds() }

                                                pokemonLoaded.value = true
                                                PokemonHeader(
                                                    pokemon = pokemon1.value!!,
                                                    containerHeight = this@BoxWithConstraints.maxHeight
                                                )
                                                PokemonContent(
                                                    pokemon = pokemon1.value!!,
                                                    evolutionChain.value!!.getListOfPokemonNames(),
                                                    navigateToPokemon,
                                                    ids, viewModelState
                                                )

                                            }
                                        }
                                    }
                                }
                            }


                        }
                    }




    }


}

@Composable
fun PokemonDetailsTopAppBar(onBackButtonPressed: () -> Unit)
{
    TopAppBar(title = {
        Row() {
            Text("Pokedex")
        }
    },
    navigationIcon = { Icon(modifier = Modifier.padding(10.dp).clickable { onBackButtonPressed() }, imageVector = Icons.Default.ArrowBack, contentDescription =null) })
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
                    .heightIn(max = containerHeight / 2, min = containerHeight / 2)
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
fun PokemonContent(
    pokemon: PokemonModel, evolutions: List<EvolutionSpeciesModel>, navigateToPokemon: (id: Int) -> Unit,
    abilitiesIds: List<Int>, viewModelState: MutableState<PokemonDetailsViewModel>
)
{
    Title(pokemon = pokemon)

    PokemonTypes(pokemonTypes = pokemon.types)
    PokemonProperty(label = "Base experience gained:", value = pokemon.base_experience.toString() + " exp")
    PokemonProperty(label = "Height", value = pokemon.getHeightInCentimeters().toString() + " cm")
    PokemonProperty(label = "Weight", value =pokemon.getWeightInKilograms().toString() +" kg")
    PokemonAbilityList(abilitiesIds = abilitiesIds, viewModelState)
    if(!pokemon.isMega())
    {
        PokemonEvolutionChain(evolutions, pokemon.id, navigateToPokemon)
    }

}
@Composable
fun Title(pokemon: PokemonModel)
{
    Column(modifier = Modifier.padding(all = 16.dp)) {
        Text(text = pokemon.getFormattedName(),
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

        Row {
            PokemonType(type = Utility.firstToUpper(pokemonTypes[0].type.name), modifier = Modifier.padding(bottom = 4.dp, end = 10.dp), pokemonTypes[0].type.getColour())
            if(pokemonTypes.size == 2)
            {
                PokemonType(type = Utility.firstToUpper(pokemonTypes[1].type.name), modifier = Modifier.padding(start = 4.dp), backGroundColor = pokemonTypes[1].type.getColour())
            }
        }
    }
}
@Composable
fun PokemonType(
    type: String, modifier: Modifier = Modifier,
    backGroundColor: Color = MaterialTheme.colors.surface,
)
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
fun PokemonAbilityList(abilitiesIds: List<Int>, viewModelState: MutableState<PokemonDetailsViewModel>)
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
            abilitiesIds.forEach {

                    PokemonAbility(
                        it,viewModelState , modifier =  Modifier.padding(end = 10.dp))
                }
            }
            
        }
            

}

@Composable
fun PokemonAbility(abilityId: Int, viewModelState: MutableState<PokemonDetailsViewModel>, modifier: Modifier = Modifier)
{
    var isExpanded by remember{ mutableStateOf(false)}

    var pokemonAbility = viewModelState.value.getPokemonAbility(abilityId).observeAsState()


    pokemonAbility.value?.let {
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
            Column {


                Text(
                    modifier = Modifier
                        .padding(vertical = 2.dp)
                        .padding(horizontal = 8.dp),
                    text = Utility.firstToUpper(pokemonAbility.value!!.name),
                    style = MaterialTheme.typography.body1,
                    overflow = TextOverflow.Visible

                )
                if (isExpanded) {
                    Text(
                        modifier = Modifier
                            .padding(vertical = 2.dp)
                            .padding(horizontal = 8.dp),
                        text = pokemonAbility.value!!.getEnglishVersion().effect,
                        style = MaterialTheme.typography.body1,
                        overflow = TextOverflow.Visible

                    )
                }
            }
        }



    }


}


@Composable
fun PokemonEvolutionChain(evolution: List<EvolutionSpeciesModel>, pokemonId :Int, navigateToPokemon: (id: Int) -> Unit)
{


    Column(modifier = Modifier.padding(top = 8.dp, bottom = 4.dp, start =16.dp, end = 16.dp)) {
        Divider(modifier = Modifier.padding(bottom = 4.dp))
        Text(text = "Evolutions:", modifier = Modifier.height(24.dp),
            style = MaterialTheme.typography.caption)
        Row(
            Modifier
                .wrapContentSize()
                .horizontalScroll(rememberScrollState())){
            evolution.forEach {
              PokemonEvolution(evolution = it, navigateToPokemon)
            }

    }
    }
}

@Composable
fun PokemonEvolution(
    evolution: EvolutionSpeciesModel,
    navigateToPokemon: (id: Int) -> Unit
)
{
    Card(modifier = Modifier
        .padding(horizontal = 8.dp, vertical = 8.dp)
        .wrapContentWidth()
        .clickable {

            navigateToPokemon(evolution.getIdFromUrl())

        },
        elevation = 2.dp,
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    )
    {
    Column {
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
            .align(Alignment.CenterHorizontally), text = Utility.formatName(evolution.name), style = MaterialTheme.typography.body1, overflow = TextOverflow.Visible)
        //Text(text = "" + "Lorem ipsum, testing how a description would look like")

    }}
}