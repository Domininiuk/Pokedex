package com.example.pokdex.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokdex.Models.AllPokemonModel
import com.example.pokdex.Models.PokemonModel
import com.example.pokdex.PokemonRepository

class DisplayAllPokemonViewModel : ViewModel() {
    var allPokemonModel : MutableLiveData<AllPokemonModel> = MutableLiveData(AllPokemonModel(
        PokemonModel()
    ))


    //If the number of pokemon in AllPokemonModel is not at least 2, send a request to the repository
    fun getAllPokemon() : MutableLiveData<AllPokemonModel>
    {
        if(allPokemonModel.value!!.results.size <= 1)
        {
            allPokemonModel = PokemonRepository.getAllPokemon() as MutableLiveData<AllPokemonModel>
        }



       return allPokemonModel
    }

}