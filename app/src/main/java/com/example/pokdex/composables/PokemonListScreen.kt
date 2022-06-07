package com.example.pokdex.composables


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.pokdex.R
import com.example.pokdex.compose.PokemonColors
import com.example.pokdex.models.PokemonModel
import com.example.pokdex.viewmodels.PokemonListViewModel


@Composable
fun PokemonListScreen(viewModel: PokemonListViewModel = hiltViewModel(), navigateToPokemon: (id: Int)-> Unit)
{
    val viewModelState = remember{ mutableStateOf(viewModel)}

    var pokemonListState = viewModelState.component1().pokemonList.observeAsState()


        pokemonListState.value?.let {
            var listState = mutableStateOf(pokemonListState.value, neverEqualPolicy())

            Scaffold(

                topBar = {
            PokemonListTopAppBar(searchForPokemon = {
                val tempList =  pokemonListState.value!!
                tempList.searchPokemon(it)

                listState.value = tempList
            }, sortBy = {

                viewModelState.component1().sortBy = it
               val tempList =  pokemonListState.value!!
                tempList.sortPokemon(it)

                listState.value = tempList

            }, cancelSearch = {
                val tempList =  pokemonListState.value!!
                tempList.restoreList()

                listState.value = tempList})
        }){
            PokemonList(
                listState.component1()!!.results, it, navigateToPokemon)
        } }


}
@OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun PokemonListTopAppBar(sortBy: (String) ->Unit, searchForPokemon: (String) -> Unit, cancelSearch:() -> Unit)
{
    // Sort by

    var showSortMenu by remember{ mutableStateOf(false)}
    var showSearchBar by remember { mutableStateOf(false)}
    TopAppBar(title = {
        Row() {
            Text("Pokedex")
        }
                      },
        actions = {

            var searchText by remember{mutableStateOf("")}
            if(showSearchBar)
            {
                SearchBar(searchText = searchText, placeholderText = "",
                    onSearchTextChanged = {
                        searchText = it
                        searchForPokemon(it)
                                          }
                ) {
                    searchText = ""
                    showSearchBar = !showSearchBar
                    cancelSearch()
                }

            }
            IconButton(onClick = {showSearchBar =  !showSearchBar}) {
                Icon(imageVector = Icons.Filled.Search,
                contentDescription = null)

            }
          //  SearchBar(searchText = "ff")

            IconButton(onClick = { showSortMenu = !showSortMenu}) {

                Icon(
                    painter = painterResource(id = R.drawable.sort_icon)
                    ,
                    contentDescription = null,
                )
                DropdownMenu(expanded = showSortMenu, onDismissRequest = {showSortMenu = false}) {
                    DropdownMenuItem(onClick = {
                        showSortMenu = !showSortMenu
                        sortBy("Generation")
                    } ) {
                        Text(text = "Generation")
                    }
                    DropdownMenuItem(onClick = {  showSortMenu = !showSortMenu
                        sortBy("Alphabetically")}) {
                        Text(text = "Alphabetically")

                    }
                }
            }},
        backgroundColor = PokemonColors.graySurface)

}


@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun SearchBar(
    searchText: String,
    placeholderText: String = "",
    onSearchTextChanged: (String) -> Unit = {},
    onClearClick: () -> Unit = {}
) {
    var showClearButton by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }



 

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp)
                .onFocusChanged { focusState ->
                    showClearButton = (focusState.isFocused)
                }
                .focusRequester(focusRequester),
            value = searchText,
            onValueChange = onSearchTextChanged,
            placeholder = {
                Text(text = placeholderText)
            },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent,
                cursorColor = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
            ),
            trailingIcon = {
                AnimatedVisibility(
                    visible = showClearButton,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    IconButton(onClick = { onClearClick() }) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = null
                        )
                    }

                }
            },
            maxLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
            }),
        )

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }


  
}
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
                Text(modifier = Modifier.wrapContentWidth(), text = pokemon.name, style = typography.h6)
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
