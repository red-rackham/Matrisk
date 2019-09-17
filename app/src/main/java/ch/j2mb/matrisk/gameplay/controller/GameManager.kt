package ch.j2mb.matrisk.gameplay.controller

import android.content.Context
import android.content.res.AssetManager
import android.widget.Button
import ch.j2mb.matrisk.GameActivity
import ch.j2mb.matrisk.R
import ch.j2mb.matrisk.R.layout.reinforcement_fragment
import ch.j2mb.matrisk.fragments.ReinforcementFragment
import ch.j2mb.matrisk.gameplay.helper.GameInitializer
import ch.j2mb.matrisk.gameplay.helper.JsonHandler
import ch.j2mb.matrisk.gameplay.model.Continent
import ch.j2mb.matrisk.gameplay.model.ContinentList
import ch.j2mb.matrisk.gameplay.model.Country
import ch.j2mb.matrisk.gameplay.model.Player
import com.google.gson.Gson
import java.io.InputStream
import java.io.InputStreamReader

//Test


/*This class is the Game manager who handles all the logic, process and communication with the players*/
class GameManager(
    var players: MutableList<Player>,
    val initialGameState: String,
    val context: Context
) {

    var phase = "initialize game"
    //pointer to player in players<> that is now on the move
    var moveOfPlayer = 0
    var round: Int = 0
    var continentList = ContinentList()

    //TODO: Create Interface
    private lateinit var listener: GameActivity


    init {
        val gameInitializer = GameInitializer(players, initialGameState, true, context)
        continentList = gameInitializer.listOfContinents
        players = gameInitializer.players
        phase = "Reinforcment"


        if (context is GameActivity) listener = context
    }

    private fun gamePlay() {
        while (players.size > 1) {
        }
    }

    private fun setReinforcement(troops:Int) {
        listener.fragmentManager.findFragmentById(listener.reinforcementFragID)


    }

    fun buttonClicked(button: Button?) {
        //only do something if it is the turn of the human player
        if (players[moveOfPlayer].bot != null) {

            //TODO: Delete change color
            listener.changeButtonToBlack(button)

            var buttonID: String = button.toString()
            //This is somehow ugly but no better solution was found how to get the ID
            buttonID = buttonID.substring(buttonID.length - 4, buttonID.length - 1).toUpperCase()
            listener.toastIt(buttonID)

            when (phase) {
                "reinforcement" -> {

                }
                "attack" -> {}
                "relocation" -> {}
            }
        }


    }

}