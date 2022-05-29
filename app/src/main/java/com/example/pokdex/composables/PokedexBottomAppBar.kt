package com.example.pokdex.composables

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import com.example.pokdex.R
import com.example.pokdex.compose.graySurface

@Composable
fun PokedexBottomAppBar(onRandomPokemon: () ->Unit)
{
    var listSelected by remember{ mutableStateOf(false)}
    var randomPokemonSelected by remember{ mutableStateOf(false)}


    BottomNavigation(backgroundColor = graySurface)
    { BottomNavigationItem(selected = listSelected, onClick = { listSelected = !listSelected}, label = { Text("Pokemon List") },icon =
    { Icon(imageVector = Icons.Filled.List, contentDescription = null) })
        BottomNavigationItem(selected = randomPokemonSelected, onClick = {
            randomPokemonSelected = !randomPokemonSelected
            onRandomPokemon()
                                                                         }, label = { Text("Random Pokemon") },icon =
        { Icon(painter = painterResource(id = R.drawable.shuffle_icon), contentDescription = null) })
    }
}