package ch.j2mb.matrisk.gameplay.helper

import android.content.Context
import android.util.Log
import ch.j2mb.matrisk.gameplay.helper.ai_machine.BiDirectionalLinkList
import ch.j2mb.matrisk.gameplay.model.ContinentList
import ch.j2mb.matrisk.gameplay.model.Player
import kotlin.random.Random.Default.nextInt

class GameInitializer(val players: MutableList<Player>, private val gameState: String, val newGame:Boolean, val context: Context) {

    /*
    Legacy

    val listOfAs = listOf(
        Country("A11", null, 1, false),
        Country("A12", null, 1, false),
        Country("A13", null, 1, false),
        Country("A21", null, 1, false),
        Country("A22", null, 1, false),
        Country("A23", null, 1, false),
        Country("A31", null, 1, false),
        Country("A32", null, 1, false),
        Country("A33", null, 1, false)
    )

    val listOfBs = listOf(
        Country("B11", null, 1, false),
        Country("B12", null, 1, false),
        Country("B13", null, 1, false),
        Country("B21", null, 1, false),
        Country("B22", null, 1, false),
        Country("B23", null, 1, false),
        Country("B31", null, 1, false),
        Country("B32", null, 1, false),
        Country("B33", null, 1, false)
    )

    val listOfCs = listOf(
        Country("C11", null, 1, false),
        Country("C12", null, 1, false),
        Country("C13", null, 1, false),
        Country("C21", null, 1, false),
        Country("C22", null, 1, false),
        Country("C23", null, 1, false),
        Country("C31", null, 1, false),
        Country("C32", null, 1, false),
        Country("C33", null, 1, false)
    )

    val listOfDs = listOf(
        Country("D11", null, 1, false),
        Country("D12", null, 1, false),
        Country("D13", null, 1, false),
        Country("D21", null, 1, false),
        Country("D22", null, 1, false),
        Country("D23", null, 1, false),
        Country("D31", null, 1, false),
        Country("D32", null, 1, false),
        Country("D33", null, 1, false)
    )

    val continentA = Continent("A", listOfAs)
    val continentB = Continent("B", listOfBs)
    val listOfContinents = listOf(continentA, continentB)
     */

    lateinit var listOfContinents: ContinentList
    lateinit var biDirectionalLinkList : BiDirectionalLinkList

    init {

        //Get initial set of countries/continentButtonList
        val jsonFile = JsonHandler.getJsonFromFile(gameState, context)
        if(jsonFile != null) {
            listOfContinents = JsonHandler.getContinentListFromJson(jsonFile)
            biDirectionalLinkList = JsonHandler.getBidirectionalLinkfromJson(jsonFile)
        } else {
            Log.e("Initalizer", "JSON file not found")
        }

        if(newGame) {
            distributeCountries()
            //randomize turn order with the players List --> For testing disabled!!
            //players.shuffle()
        }

    }


    private fun assignCountry(continent: Int, country: Int, player: Int) {
        listOfContinents.continents[continent].countries[country].player = players[player].name
    }

    //Distribute all countries available to players
    private fun distributeCountries() {
        var totalNrOfCountries = 0
        for (continent in listOfContinents.continents)
            totalNrOfCountries += continent.countries.size

        val minCountryPerPlayer = totalNrOfCountries / players.size

        //When countries cannot be distributed evenly the leftovers will be distributed randomly to players
        var moduloCountry = totalNrOfCountries % players.size


        var distributionList = mutableListOf<Int>()
        for(i in 0 until players.size)
            distributionList.add(i, 0)

        //Distribution loop
        for (continent in listOfContinents.continents.indices) {
            for (country in listOfContinents.continents[continent].countries.indices) {
                var assigned = false
                while (!assigned) {
                    //Pick random player from List
                    var player = nextInt(0, players.size)
                    //Check if player has not reached max amount of assigned countries
                    if (distributionList[player] < minCountryPerPlayer + moduloCountry) {
                        assignCountry(continent, country, player)
                        distributionList[player]++
                        //If a country from the modulo was assigned, reduce moduloCounty by one
                        if (distributionList[player] > minCountryPerPlayer)  moduloCountry--
                        assigned = true
                    }
                }
            }
        }

    }


}
