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
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.pokdex.R
import com.example.pokdex.Utility
import com.example.pokdex.compose.PokedexTheme
import com.example.pokdex.models.PokemonListModel
import com.example.pokdex.models.PokemonModel
import com.example.pokdex.viewmodels.PokemonListViewModel


@Composable
fun PokemonListScreen(viewModel: PokemonListViewModel = hiltViewModel(), navigateToPokemon: (id: Int)-> Unit)
{
    val viewModelState = remember{ mutableStateOf(viewModel)}

    var pokemonList = viewModelState.component1().pokemonList.observeAsState()
    remember{ mutableStateOf(pokemonList)}
    PokedexTheme {
        pokemonList.value?.let {  Scaffold(topBar = {
            TopAppBar(title = {
                Text("Top App Bar")},
                backgroundColor = MaterialTheme.colors.primary,
            navigationIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.sort_icon,),
                    contentDescription = null
                )
            })
                                                    }){
            PokemonList(pokemonList.value!!, it, navigateToPokemon)
        } }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PokemonList(
    pokemonList: PokemonListModel,
    padding: PaddingValues,
    navigateToPokemon: (id: Int) -> Unit
)
{



 LazyVerticalGrid( columns = GridCells.Fixed(2) , contentPadding = PaddingValues(horizontal = 16.dp,
 vertical = 8.dp)) {

     items(items = pokemonList.results
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
