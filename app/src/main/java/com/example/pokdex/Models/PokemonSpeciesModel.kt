package com.example.pokdex.Models

data class PokemonSpeciesModel(var name : String, var evolution_chain : EvolutionChain)
{
    data class EvolutionChain(val url : String)
    {
        internal fun getChainId() : Int
        {
            var id : Int = 0
            // remove character until the id in the url
            //cast it to Int and return it?
            if(url != null && url.startsWith(""))
            {
                //startIndex is the number of characters in https://pokeapi.co/api/v2/evolution-chain/ -1
                var tempUrl = url.substring(42)
                var idString = tempUrl.filter { it.isDigit() }

                id = idString.toInt()
            }
            return id
        }
    }

}