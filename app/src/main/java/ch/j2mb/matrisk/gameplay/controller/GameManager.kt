package ch.j2mb.matrisk.gameplay.controller

import ch.j2mb.matrisk.gameplay.model.Continent
import ch.j2mb.matrisk.gameplay.model.Country
import ch.j2mb.matrisk.gameplay.model.Player


/*This class is the Game manager who handles all the logic, process and communication with the players*/
class GameManager(var players: MutableList<Player>, var continents: List<Continent>) {

    var round: Int = 0
    var gameOrder = mutableListOf<Player>()


    init {
        setGameSequence(players)
    }

    private fun gamePlay() {
        while (players.size > 1) {
        }
    }

    private fun setGameSequence(players: MutableList<Player>) {
        var gameOrder: MutableList<Player>

        var rand = 0

    }


}