package com.example.pokdex


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokdex.compose.PokedexTheme
import com.example.pokdex.fragments.PokemonDetailsScreen
import com.example.pokdex.fragments.PokemonListScreen
import com.example.pokdex.viewmodels.PokemonDetailsViewModel
import com.example.pokdex.viewmodels.PokemonListViewModel
import dagger.hilt.android.AndroidEntryPoint


/*
class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}


*/

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var pokemonListViewModel = PokemonListViewModel()
        var pokemonDetailsViewModel = PokemonDetailsViewModel()
        pokemonListViewModel.pokemonList.observe(this){
            setContent {
                PokedexTheme {
                    MainScreen()
                }
            }
        }

    }
}


@Composable
fun MainScreen()
{
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = "PokemonList")
    {
        composable("PokemonList")
        {
            PokemonListScreen(navigateToPokemon = {id ->
                navController.navigate("PokemonDetails/$id")
            })
        }
        composable("PokemonDetails/{pokemonId}")
        {
           var pokemonId = it.arguments?.getString("pokemonId")

            if(pokemonId != "0")
            {

            }
            PokemonDetailsScreen(pokemonId = pokemonId!!.toInt())
        }

    }
    
    
    //DisplayPokemonListScreen(viewModel = viewModel)
    
}

