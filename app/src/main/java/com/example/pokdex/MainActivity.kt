package com.example.pokdex


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.pokdex.Fragments.MainFragment
import com.example.pokdex.Fragments.MainFragmentDirections
import com.example.pokdex.Models.PokemonModel
import com.example.pokdex.ViewModel.RandomPokemonViewModel
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity()
{
  //  private val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
  //  private val navController = navHostFragment?.findNavController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       // goToRandomPokemonButton = findViewById(R.id.go_to_random_pokemon_button)


       val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
       val navController = navHostFragment?.findNavController()

    }


}
