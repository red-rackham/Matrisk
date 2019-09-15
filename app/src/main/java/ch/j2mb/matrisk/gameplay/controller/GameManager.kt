package ch.j2mb.matrisk.gameplay.controller

import android.content.Context
import android.content.res.AssetManager
import ch.j2mb.matrisk.gameplay.helper.GameInitializer
import ch.j2mb.matrisk.gameplay.helper.JsonHandler
import ch.j2mb.matrisk.gameplay.model.Continent
import ch.j2mb.matrisk.gameplay.model.ContinentList
import ch.j2mb.matrisk.gameplay.model.Country
import ch.j2mb.matrisk.gameplay.model.Player
import com.google.gson.Gson
import java.io.InputStream
import java.io.InputStreamReader


/*This class is the Game manager who handles all the logic, process and communication with the players*/
class GameManager(
    var players: MutableList<Player>,
    val initialGameState: String,
    val context: Context
) {

    val jsonHandler = JsonHandler()
    var phase = "initialize game"
    var turn = ""
    var round: Int = 0
    var continentList = ContinentList()


    init {
        val gameInitializer = GameInitializer(players, initialGameState, context)
        continentList = gameInitializer.listOfContinents
        players = gameInitializer.players
        phase = "Reinforcment"
        turn = players[0].name
        //gamePlay()
    }


    private fun gamePlay() {
        while (players.size > 1) {
        }
    }

    fun getCountriesFromGson(jsonString: String): ContinentList? {
        var continentList: ContinentList? = null
        var json: String? = null
        try {
            val inputStream: InputStream = context.applicationContext.assets.open(jsonString)
            json = inputStream.bufferedReader().use { it.readText() }
        } catch (ex: Exception) {
            ex.printStackTrace()
            return continentList
        }
        val gson = Gson()
        continentList = gson.fromJson(json, ContinentList::class.java)
        return continentList
    }


}