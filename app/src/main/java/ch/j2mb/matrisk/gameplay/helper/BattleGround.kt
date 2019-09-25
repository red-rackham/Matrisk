package ch.j2mb.matrisk.gameplay.helper

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import ch.j2mb.matrisk.R
import ch.j2mb.matrisk.gameplay.model.Country
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random.Default.nextInt

class BattleGround(
    private var counterparties: List<Country>,
    private var attackingTroops: Int,
    private val troopsLeft: Int,
    val context: Context
) {
    //attacker is counterparties[0], defender is counterparties[1]
    var attackDices: Int
    var defendDices: Int

    var attackPopup: View
    val activity: Activity
    lateinit var listener: GameActivityInterface

    init {
        if (context is GameActivityInterface) listener = context
        attackPopup = listener.showAttackPopup()
        activity = context as Activity
        attackDices = 0
        defendDices = 0
        updateTextViews()
    }

    /**
     * Battle beween attacker and defender is simulated. Results are shown in the view (attack popup)
     *
     */
    private fun battleRound() {

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
        val attackThrow = mutableListOf(0, 0, 0)
        val defendThrow = mutableListOf(0, 0)
        for (i in 0 until attackDices) attackThrow.add(i, nextInt(1, 7))
        for (j in 0 until defendDices) defendThrow.add(j, nextInt(1, 7))

        //results dice throws will be compared in pairs (attacker:defender), order descending
        val pairs = if (attackDices < defendDices) attackDices else defendDices
        attackThrow.sortDescending()
        defendThrow.sortDescending()

        //animation
        clearDicePictures()
        showDicePicture(attackThrow, defendThrow)

        for (i in 0 until pairs)
            if (attackThrow[i] > defendThrow[i]) {
                //if attack dice is higher than defending, defender looses one troop
                counterparties[1].count--
            } else {
                //defending is higher or equal than attacking, attacker looses one troop
                attackingTroops--
            }

        GlobalScope.launch {
            val activity = context as Activity
            delay(1000)
            activity.runOnUiThread { updateTextViews() }
        }
    }

    /**
     * Check if there is a winner in a battle
     *
     * @return counterparties if someone wins as List<Country>{attacker-country, defender-country}
     */
    private fun findWinner(): List<Country>? {
        //if attacker wins, the troops take over defending country
        when {
            //attacker wins: attacker invades defending country with #attackingTroops
            (counterparties[1].count == 0) -> {
                counterparties[1].player = counterparties[0].player
                counterparties[1].count = attackingTroops
                counterparties[0].count = troopsLeft
                listener.toastIt("you win!")
                return counterparties
            }
            //defender wins, attacker only has #troopsLeft on attacking country
            (attackingTroops == 0) -> {
                listener.toastIt("you lose!")
                counterparties[0].count = troopsLeft
                return counterparties
            }
            else -> {
                return null
            }
        }
    }

    /**
     * Initiate a fast battleRound where the attacker attacks with all troops and no withdrawals are possible
     */
    fun fastfight(): List<Country> {
        while (attackingTroops > 0 && counterparties[1].count > 0) {
            battleRound()
        }
        return findWinner()!!
    }

    //battle, each round the attacker can withdrawal or attack again
    fun fight(): List<Country>? {
        battleRound()
        return findWinner()
    }

    /**
     * abort attack and withdraw troops
     */
    fun withdrawal() {
        closePopup()
    }

    /**
     * Close the popup with delay of 1 sec
     *
     */
    private fun closePopup() {
        GlobalScope.launch {
            delay(1000)
            listener.closeAttackPopup()
        }
    }

    /**
     * Update picture ID for the dice based on dice count
     *
     * @param i Count of dice
     * @return Int with ID of picture
     */
    private fun getDicePicture(i: Int?): Int {
        return when (i) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            6 -> R.drawable.dice_6
            else -> R.drawable.empty
        }
    }

    /**
     * Hide pictures
     *
     */
    private fun clearDicePictures() {
        attackPopup.findViewById<ImageView>(R.id.attackerDice1)
            .setImageResource(getDicePicture(null))
        attackPopup.findViewById<ImageView>(R.id.attackerDice2)
            .setImageResource(getDicePicture(null))
        attackPopup.findViewById<ImageView>(R.id.attackerDice3)
            .setImageResource(getDicePicture(null))
        attackPopup.findViewById<ImageView>(R.id.defenderDice1)
            .setImageResource(getDicePicture(null))
        attackPopup.findViewById<ImageView>(R.id.defenderDice2)
            .setImageResource(getDicePicture(null))
    }

    /**
     * Update pictures in view of dices based
     *
     * @param attack List with dice throw of attacker in current battleRound
     * @param defend List with dice throw of defender in current battleRound
     */
    private fun showDicePicture(attack: List<Int?>, defend: List<Int>) {

        GlobalScope.launch {

            val activity = context as Activity

            activity.runOnUiThread(Runnable {
                attackPopup.findViewById<ImageView>(R.id.attackerDice1)
                    .setImageResource(getDicePicture(attack[0]))
            })
            delay(300)

            activity.runOnUiThread(Runnable {
                attackPopup.findViewById<ImageView>(R.id.attackerDice2)
                    .setImageResource(getDicePicture(attack[1]))
            })
            delay(300)

            activity.runOnUiThread(Runnable {
                attackPopup.findViewById<ImageView>(R.id.attackerDice3)
                    .setImageResource(getDicePicture(attack[2]))
            })
            delay(300)

            activity.runOnUiThread(Runnable {
                attackPopup.findViewById<ImageView>(R.id.defenderDice1)
                    .setImageResource(getDicePicture(defend[0]))
            })
            delay(300)

            activity.runOnUiThread(Runnable {
                attackPopup.findViewById<ImageView>(R.id.defenderDice2)
                    .setImageResource(getDicePicture(defend[1]))
            })
            delay(300)
        }
    }

    /**
     * Update text view in popup
     *
     */
    private fun updateTextViews() {
        attackPopup.findViewById<TextView>(R.id.attackingCountry).text = counterparties[0].name
        attackPopup.findViewById<TextView>(R.id.defendingCountry).text = counterparties[1].name
        attackPopup.findViewById<TextView>(R.id.attackingTroops).text = attackingTroops.toString()
        attackPopup.findViewById<TextView>(R.id.defendingTroops).text =
            counterparties[1].count.toString()
    }

}
