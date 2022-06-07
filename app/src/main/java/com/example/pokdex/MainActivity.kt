package com.example.pokdex


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pokdex.composables.PokedexBottomAppBar
import com.example.pokdex.compose.PokedexTheme
import com.example.pokdex.composables.PokemonDetailsScreen
import com.example.pokdex.composables.PokemonListScreen
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

            setContent {
                PokedexTheme(content = {
                    MainScreen()
                })

            }


    }
}


@Composable
fun MainScreen()
{
    val navController = rememberNavController()


        NavHost(navController = navController, startDestination = "PokemonList", Modifier.padding())
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
                PokemonDetailsScreen(pokemonIdParam = pokemonId!!.toInt(), navigateToPokemon = {id ->
                    navController.navigate("PokemonDetails/$id")
                }, onBackButtonPressed = {
                    navController.navigateUp()
                })
            }

        }

    
    //DisplayPokemonListScreen(viewModel = viewModel)
    
}

