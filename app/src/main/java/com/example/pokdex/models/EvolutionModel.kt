package com.example.pokdex.models


//Add a recursive function to iterate through the chain and add all pokemons to a list??


//Either display all possible evolutions, or just add one Evolves to: window
data class EvolutionModel(val id : Int,  val chain : EvolutionChainLinkModel)
{

    fun getListOfPokemonNames() : List<EvolutionSpeciesModel>
    {
        var returnList  = mutableListOf<EvolutionSpeciesModel>()
        if(chain.species.name != null)
        {
            returnList.add(chain.species)
        }
//While evolves to isnt null
        //Add species name to the list
        var chain = chain.evolves_to

        while(chain.isNotEmpty())
        {
            returnList.add(chain[0].species)
            chain = chain[0].evolves_to

        }
        return returnList
    }
}
data class EvolutionChainLinkModel( val species : EvolutionSpeciesModel, val evolves_to : List<EvolutionChainLinkModel>)

data class EvolutionSpeciesModel(val name : String , val id : Int, val url : String)
{
   fun getIdFromUrl() : Int
   {
       return url.subSequence(41, url.length - 1).toString().filter { c: Char -> c != '/' }.toString().toInt()
   }
}
