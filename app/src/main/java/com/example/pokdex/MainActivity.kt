package com.example.pokdex


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity()
{
  //  private val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
  //  private val navController = navHostFragment?.findNavController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       // goToRandomPokemonButton = findViewById(R.id.go_to_random_pokemon_button)


       val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
       val navController = navHostFragment!!.findNavController()

        setupActionBarWithNavController(navController)


        if (supportActionBar != null) {
            supportActionBar!!.setDisplayShowHomeEnabled(true);
            supportActionBar!!.setLogo(R.mipmap.ic_launcher);
            supportActionBar!!.setDisplayUseLogoEnabled(true);

        }

    }


    override fun onBackPressed() {
        supportFragmentManager.popBackStack()
    }
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {


        16908332 -> {
            onBackPressed()

            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

}
