package ch.j2mb.matrisk.gameplay.helper

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import ch.j2mb.matrisk.R
import ch.j2mb.matrisk.gameplay.model.Country
import kotlin.random.Random.Default.nextInt

class BattleGround(var counterparties: List<Country>, var attackingTroops: Int, context: Context) {

    lateinit var listener: GameActivityInterface
    var attackPopup: View


    init {
        if (context is GameActivityInterface) listener = context
        attackPopup = listener.showAttackPopup()
        updateTextViews()
    }

    //attacker = counterparties[0], defender = counterparties[1]

    var attackDices = 0
    var defendDices = 0

    fun battleRound() {

        //number of dices for attacker
        when {
            attackingTroops >= 3 -> attackDices = 3
            attackingTroops == 2 -> attackDices = 2
            attackingTroops == 1 -> attackDices = 1
        }

        //number of dices for defender
        when {
            counterparties[1].count >= 2 -> defendDices = 2
            counterparties[1].count == 1 -> defendDices = 1
        }

        //results of dice throws stored in lists
        val attackThrow = mutableListOf<Int>()
        val defendThrow = mutableListOf<Int>()
        for (i in 1..attackDices) attackThrow.add(nextInt(1, 7))
        for (j in 1..defendDices) defendThrow.add(nextInt(1, 7))

        //results dice throws will be compared in pairs (attacker:defender), order descending
        val pairs = if (attackDices < defendDices) attackDices else defendDices
        attackThrow.sortDescending()
        defendThrow.sortDescending()
        showDicePicture(attackThrow, defendThrow)

        for (i in 0 until pairs)
            if (attackThrow[i] > defendThrow[i]) {
                //if attack dice is higher than defending, defender looses one troop
                counterparties[1].count--
            } else {
                //defending is higher or equal than attacking, attacker looses one troop
                attackingTroops--
            }
        updateTextViews()

    }

    fun findWinner(): List<Country>? {
        //if attacker wins, the troops take over defending country
        when {
            (counterparties[1].count == 0) -> {
                counterparties[1].player = counterparties[0].player
                counterparties[1].count = attackingTroops
                //TODO: Toast
                return counterparties
            }
            (attackingTroops == 0) -> {
                //TODO: Toast
                return counterparties
            }
            else -> {
                //TODO:Toast --No winner
                return null
            }
        }
    }

    fun fastfight(): List<Country> {
        while (attackingTroops > 0 && counterparties[1].count > 0) {
            battleRound()
        }
        return findWinner()!!
    }

    fun fight(): List<Country>? {
        battleRound()
        return findWinner()
    }

    fun withdrawal() {

    }

    fun closePopup() {
        listener.closeAttackPopup()
    }

    fun getDicePicture(i: Int?) :Int{
        when (i) {
            1 -> return R.drawable.dice_1
            2 -> return R.drawable.dice_2
            3 -> return R.drawable.dice_3
            4 -> return R.drawable.dice_4
            5 -> return R.drawable.dice_5
            6 -> return R.drawable.dice_6
            else -> return  R.drawable.empty
        }
    }

    fun showDicePicture(attack: List<Int?>, defend: List<Int>) {
        attackPopup.findViewById<ImageView>(R.id.attackerDice1).setImageResource(getDicePicture(attack[0]))
        attackPopup.findViewById<ImageView>(R.id.attackerDice2).setImageResource(getDicePicture(attack[1]))
        attackPopup.findViewById<ImageView>(R.id.attackerDice3).setImageResource(getDicePicture(attack[2]))
        attackPopup.findViewById<ImageView>(R.id.defenderDice1).setImageResource(getDicePicture(defend[0]))
        attackPopup.findViewById<ImageView>(R.id.defenderDice2).setImageResource(getDicePicture(defend[1]))
    }

    fun updateTextViews() {
        attackPopup.findViewById<TextView>(R.id.attackingCountry).text = counterparties[0].name
        attackPopup.findViewById<TextView>(R.id.attackingCountry).text = counterparties[1].name
        attackPopup.findViewById<TextView>(R.id.attackingTroops).text = attackingTroops.toString()
        attackPopup.findViewById<TextView>(R.id.defendingTroops).text = counterparties[1].count.toString()
    }

}
