package com.example.pokdex.Models


//Add a recursive function to iterate through the chain and add all pokemons to a list??


//Either display all possible evolutions, or just add one Evolves to: window
data class EvolutionModel(val id : Int,  val chain : EvolutionChainLinkModel)
{

    fun getListOfPokemonNames() : List<String>
    {
        var returnList  = mutableListOf<String>()
        if(chain.species.name != null)
        {
            returnList.add(chain.species.name)
        }
//While evolves to isnt null
        //Add species name to the list
        var chain = chain.evolves_to

        while(chain.isNotEmpty())
        {
            returnList.add(chain[0].species.name)
            chain = chain[0].evolves_to

        }
        return returnList
    }
}
data class EvolutionChainLinkModel( val species : EvolutionSpeciesModel, val evolves_to : List<EvolutionChainLinkModel>)

data class EvolutionSpeciesModel(val name : String , val url : String)
