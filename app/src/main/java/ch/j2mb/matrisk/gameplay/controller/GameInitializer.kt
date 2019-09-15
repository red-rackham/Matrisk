package ch.j2mb.matrisk.gameplay.controller

import ch.j2mb.matrisk.gameplay.model.Continent
import ch.j2mb.matrisk.gameplay.model.Country
import ch.j2mb.matrisk.gameplay.model.Player
import kotlin.random.Random.Default.nextInt

class GameInitializer(val players: MutableList<Player>) {

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


    init {
        distributeCountries()

        //randomize turn order via the players List<>
        players.shuffle()
        GameManager(players, listOfContinents)

    }


    private fun assignCountry(continent: Int, country: Int, player: Int) {
        listOfContinents[continent].countries[country].player = players[player].name
    }

    //Distribute all countries available to players
    private fun distributeCountries() {
        var totalNrOfCountries = 0
        for (i in 0..listOfContinents.size)
            totalNrOfCountries += listOfContinents[i].countries.size

        val minCountryPerPlayer = totalNrOfCountries / players.size

        //When countries cannot be distributed evenly the leftovers will be distributed randomly to players
        var moduloCountry = totalNrOfCountries % players.size

        var distributionList = mutableListOf(players.size)

        //Distribution loop
        for (i in 0..listOfContinents.size) {
            for (j in 0..listOfContinents[i].countries.size) {
                var assigned = false
                while (!assigned) {
                    //Pick random player from List
                    var player = nextInt(0, players.size)
                    //Check if player has not reached max amount of assigned countries
                    if (distributionList[player] < minCountryPerPlayer + moduloCountry) {
                        assignCountry(i, j, player)
                        //If a country from the modulo was assigned, reduce moduloCounty by one
                        if (distributionList[player] >= minCountryPerPlayer) moduloCountry--
                        assigned = true
                    }
                }
            }
        }

    }


}
