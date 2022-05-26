package com.example.pokdex.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.fragment.findNavController
import coil.compose.rememberAsyncImagePainter
import com.example.pokdex.Utility
import com.example.pokdex.compose.PokedexTheme
import com.example.pokdex.databinding.FragmentDisplayAllPokemonBinding
import com.example.pokdex.models.PokemonListModel
import com.example.pokdex.models.PokemonModel
import com.example.pokdex.viewmodels.DisplayAllPokemonViewModel


class DisplayAllPokemonFragment : Fragment() {
    lateinit var displayAllPokemonVM: DisplayAllPokemonViewModel
    lateinit var pokemonList: PokemonListModel

    private var _binding: FragmentDisplayAllPokemonBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initializeBinding()

        binding.composeView.apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner)
            )

            displayAllPokemonVM = DisplayAllPokemonViewModel()
            displayAllPokemonVM.pokemonList.observe(viewLifecycleOwner) { newList ->
                //   pokemonList = newList
                setContent { PokemonList(pokemonList = newList) }
            }
        }


        // initializeVariables()
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun initializeBinding() {
        _binding = FragmentDisplayAllPokemonBinding.inflate(layoutInflater)
    }
}
@Composable
fun PokemonList(pokemonList : PokemonListModel)
{
    remember{pokemonList}
    val navController = rememberNavController()
    PokedexTheme() {
        Scaffold{
            Pokemon(pokemonList, navController, it)
        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Pokemon(pokemonList: PokemonListModel, navController: NavController, padding : PaddingValues)
{



 LazyVerticalGrid( columns = GridCells.Fixed(2) , contentPadding = PaddingValues(horizontal = 16.dp,
 vertical = 8.dp)) {

     items(items = pokemonList.results,
     itemContent = {
         PokemonListItem(pokemon = it, navController)
     })
 }
}

@Composable
fun PokemonListItem(pokemon : PokemonModel, navController: NavController){
    Card(modifier = Modifier
        .padding(horizontal = 8.dp, vertical = 8.dp)
        .wrapContentWidth()
        ,
    elevation = 2.dp,
    shape = RoundedCornerShape(corner = CornerSize(16.dp)))
    {
        Row(modifier = Modifier
            .clickable {
                navController.navigate(
                    DisplayAllPokemonFragmentDirections.actionDisplayAllPokemonFragmentToDisplayPokemonFragment2(
                        pokemon.id
                    )
                )
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
