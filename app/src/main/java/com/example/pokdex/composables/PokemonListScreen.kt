package com.example.pokdex.composables


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.pokdex.R
import com.example.pokdex.Utility
import com.example.pokdex.compose.PokedexTheme
import com.example.pokdex.compose.PokemonColors
import com.example.pokdex.models.PokemonModel
import com.example.pokdex.viewmodels.PokemonListViewModel


@Composable
fun PokemonListScreen(viewModel: PokemonListViewModel = hiltViewModel(), navigateToPokemon: (id: Int)-> Unit)
{
    val viewModelState = remember{ mutableStateOf(viewModel)}

    var pokemonListState = viewModelState.component1().pokemonList.observeAsState()


    PokedexTheme {
        pokemonListState.value?.let {  Scaffold(topBar = {
            PokemonListTopAppBar {

                viewModelState.component1().sortBy = it
            }
        }){
            PokemonList(
                viewModelState.component1().sortPokemon(pokemonListState.value!!), it, navigateToPokemon)
        } }

    }
}
@Composable
fun PokemonListTopAppBar(sortBy: (String) ->Unit)
{
    // Sort by

    var showSortMenu by remember{ mutableStateOf(false)}
    TopAppBar(title = {
        Row() {
            Text("Pokedex")
        }
                      },
        actions = {
            IconButton(onClick = { showSortMenu = !showSortMenu}) {

                Icon(
                    painter = painterResource(id = R.drawable.sort_icon,),
                    contentDescription = null,
                )
                DropdownMenu(expanded = showSortMenu, onDismissRequest = {showSortMenu = false}) {
                    DropdownMenuItem(onClick = {
                        showSortMenu = !showSortMenu
                        sortBy("Alphabetically")
                    } ) {
                        
                        Text(text = "Alphabetically")
                    }
                    DropdownMenuItem(onClick = { /*TODO*/ }) {
                        Text(text = "Generation")
                        
                    }
                }
            }},
        backgroundColor = PokemonColors.graySurface)

}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PokemonList(
    pokemonList: List<PokemonModel>,
    padding: PaddingValues,
    navigateToPokemon: (id: Int) -> Unit
)
{



 LazyVerticalGrid( columns = GridCells.Fixed(2) , contentPadding = PaddingValues(horizontal = 16.dp,
 vertical = 8.dp)) {

     items(items = pokemonList
     ) {
         PokemonListItem(pokemon = it, navigateToPokemon)
     }
 }
}

@Composable
fun PokemonListItem(pokemon: PokemonModel, navigateToPokemon: (id: Int) -> Unit){
    Card(modifier = Modifier
        .padding(horizontal = 8.dp, vertical = 8.dp)
        .wrapContentWidth()
        ,
    elevation = 2.dp,
    shape = RoundedCornerShape(corner = CornerSize(16.dp)))
    {
        Row(modifier = Modifier
            .clickable {
                navigateToPokemon(pokemon.id)
            }
            .wrapContentWidth(),
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentWidth(),
                        verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PokemonImage(pokemonModel = pokemon)
                Text(modifier = Modifier.wrapContentWidth(), text = Utility.firstToUpper(pokemon.name), style = typography.h6)
            }
        }
    }
}

@Composable
fun PokemonImage(pokemonModel: PokemonModel)
{
    var url : String = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/" +pokemonModel.id +".png"

    Image(
        painter = rememberAsyncImagePainter(url),
        contentDescription = null,
        modifier = Modifier.size(128.dp)
    )
}
