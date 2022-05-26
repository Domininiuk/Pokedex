package com.example.pokdex


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.pokdex.compose.PokedexTheme
import com.example.pokdex.fragments.PokemonList
import com.example.pokdex.models.PokemonListModel
import com.example.pokdex.models.PokemonModel
import com.example.pokdex.viewmodels.DisplayAllPokemonViewModel


/*
class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}


*/

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var viewModel = DisplayAllPokemonViewModel()

        viewModel.pokemonList.observe(this){
            setContent {
                PokedexTheme {
                   // PokemonList(it)
                    MainScreen(it)
                }
            }
        }

    }
}


@Composable
fun MainScreen(pokemonList: PokemonListModel)
{
    NavHost(navController = rememberNavController(), startDestination = "PokemonList"){
        composable("PokemonList") { PokemonList(pokemonList = pokemonList)}
    }
}

