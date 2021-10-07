package com.example.pokdex


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.findNavController

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
