package ch.j2mb.matrisk.gameplay.controller

import ch.j2mb.matrisk.gameplay.model.Continent
import ch.j2mb.matrisk.gameplay.model.Country
import ch.j2mb.matrisk.gameplay.model.Player
import kotlin.random.Random.Default.nextInt

class GameInitializer(val players: MutableList<Player>) {


    val listOfAs = listOf(
        Country("A11", 1, 1),
        Country("A12", 1, 2),
        Country("A13", 1, 3),
        Country("A21", 2, 1),
        Country("A22", 2, 2),
        Country("A23", 2, 3),
        Country("A31", 3, 1),
        Country("A32", 3, 2),
        Country("A33", 3, 3)
    )

    val listOfBs = listOf(
        Country("B11", 6, 1),
        Country("B12", 6, 2),
        Country("B13", 6, 3),
        Country("B21", 7, 1),
        Country("B22", 7, 2),
        Country("B23", 7, 3),
        Country("B31", 8, 1),
        Country("B32", 8, 2),
        Country("B33", 8, 3)
    )

    val continentA = Continent("A", listOfAs)
    val continentB = Continent("B", listOfBs)

    val listOfContinents = listOf(continentA, continentB)

    init{

        var totalNrOfCountries = 0
        for(i in 0..listOfContinents.size)
            totalNrOfCountries += listOfContinents[i].countries.size

        val minCountryPerPlayer = totalNrOfCountries / players.size
        val moduloCountry = totalNrOfCountries % players.size

        var distributionList = mutableListOf(players.size)

        for(i in 0..listOfContinents.size) {
            for(j in 0..listOfContinents[i].countries.size) {
                var assigned = false
                while(!assigned) {
                    var player = nextInt(0, players.size)
                    if (distributionList[player] < minCountryPerPlayer + moduloCountry) {
                        assignCountry(i, j, player)
                        assigned = true
                    }
                }
            }
        }


    }
    private fun assignCountry(continent:Int, country:Int, player:Int) {
        listOfContinents[continent].countries[country].owner = players[player]
    }




}
