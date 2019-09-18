package ch.j2mb.matrisk.gameplay.helper

import ch.j2mb.matrisk.gameplay.model.Country
import kotlin.random.Random.Default.nextInt

class BattleGround(counterparties: List<Country>, var attackingTroops:Int) {

    var attackDices = 0
    var defendDices = 0
    val attackCountry:Country = counterparties[0]
    val defendCountry:Country = counterparties[1]

    fun fastfight() {

        //TODO: Make new thread, speak to popup

        while(attackingTroops > 0 && defendCountry.count > 0) {

            when {
                attackingTroops >= 3 -> attackDices = 3
                attackingTroops == 2 -> attackDices = 2
                attackingTroops == 1 -> attackDices = 1
            }

            when  {
                defendCountry.count >= 2 -> defendDices = 2
                defendCountry.count == 1 -> defendDices = 1
            }

            val attackThrow = mutableListOf<Int>()
            val defendThrow = mutableListOf<Int>()

            for (i in 1..attackDices) attackThrow.add(nextInt(1, 7))
            for (j in 1..defendDices) defendThrow.add(nextInt(1, 7))

            attackThrow.sortDescending()
            defendThrow.sortDescending()

            val compare = if(attackDices < defendDices) attackDices else defendDices

            for(i in 0 until compare)
                if(attackThrow[i] > defendThrow[i]) {
                    defendCountry.count--
                } else {
                    attackingTroops--
                }
        }

        if(defendCountry.count == 0) {
            defendCountry.player = attackCountry.player
            defendCountry.count = attackingTroops
            //TODO: Toast
        }



    }

}