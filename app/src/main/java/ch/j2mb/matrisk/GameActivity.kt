package ch.j2mb.matrisk


import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.PopupWindow
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import ch.j2mb.matrisk.fragments.*
import ch.j2mb.matrisk.gameplay.helper.BattleGround
import ch.j2mb.matrisk.gameplay.helper.GameInitializer
import ch.j2mb.matrisk.gameplay.helper.JsonHandler
import ch.j2mb.matrisk.gameplay.helper.GameActivityInterface
import ch.j2mb.matrisk.gameplay.helper.ai_machine.BiDirectionalLinkList
import ch.j2mb.matrisk.gameplay.helper.ai_machine.MinimalRisk
import ch.j2mb.matrisk.gameplay.model.ContinentList
import ch.j2mb.matrisk.gameplay.model.Country
import ch.j2mb.matrisk.gameplay.model.Player
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class GameActivity : AppCompatActivity(), GameActivityInterface {

    private val fragmentManager: FragmentManager = supportFragmentManager
    private lateinit var playerList: MutableList<Player>

    private var phase = "initialize game"
    //pointer to player in players<> that is now on the move
    private var moveOfPlayer = 0
    private var round: Int = 0
    private var continentList = ContinentList()
    private var biDirectionalLinkList = BiDirectionalLinkList()

    lateinit var reinforcementFragment: ReinforcementFragment
    lateinit var attackFragment: AttackFragment
    lateinit var relocationFragment: RelocationFragment
    lateinit var botFragment: BotFragment

    lateinit var battleGround: BattleGround
    lateinit var attackPopupWindow: PopupWindow

    private var attackFragID: Int = 0
    private var relocationFragID: Int = 0
    private var botFragmentID: Int = 0

    private var continentBonus = 3
    private var troopsPerCountryDivisor = 2

    lateinit var a11: Button
    lateinit var a12: Button
    lateinit var a13: Button
    lateinit var a21: Button
    lateinit var a22: Button
    lateinit var a23: Button
    lateinit var a31: Button
    lateinit var a32: Button
    lateinit var a33: Button

    lateinit var b11: Button
    lateinit var b12: Button
    lateinit var b13: Button
    lateinit var b21: Button
    lateinit var b22: Button
    lateinit var b23: Button
    lateinit var b31: Button
    lateinit var b32: Button
    lateinit var b33: Button

    lateinit var c11: Button
    lateinit var c12: Button
    lateinit var c13: Button
    lateinit var c21: Button
    lateinit var c22: Button
    lateinit var c23: Button
    lateinit var c31: Button
    lateinit var c32: Button
    lateinit var c33: Button

    lateinit var d11: Button
    lateinit var d12: Button
    lateinit var d13: Button
    lateinit var d21: Button
    lateinit var d22: Button
    lateinit var d31: Button
    lateinit var d23: Button
    lateinit var d32: Button
    lateinit var d33: Button

    lateinit var continentA: List<Button>
    lateinit var continentB: List<Button>
    lateinit var continentC: List<Button>
    lateinit var continentD: List<Button>

    lateinit var continentButtonList: List<List<Button>>


    private val initialGameState: String = "start_state.json"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        playerList = getPlayers()

        a11 = findViewById(R.id.a11)
        a12 = findViewById(R.id.a12)
        a13 = findViewById(R.id.a13)
        a21 = findViewById(R.id.a21)
        a22 = findViewById(R.id.a22)
        a23 = findViewById(R.id.a23)
        a31 = findViewById(R.id.a31)
        a32 = findViewById(R.id.a32)
        a33 = findViewById(R.id.a33)

        b11 = findViewById(R.id.b11)
        b12 = findViewById(R.id.b12)
        b13 = findViewById(R.id.b13)
        b21 = findViewById(R.id.b21)
        b22 = findViewById(R.id.b22)
        b23 = findViewById(R.id.b23)
        b31 = findViewById(R.id.b31)
        b32 = findViewById(R.id.b32)
        b33 = findViewById(R.id.b33)

        c11 = findViewById(R.id.c11)
        c12 = findViewById(R.id.c12)
        c13 = findViewById(R.id.c13)
        c21 = findViewById(R.id.c21)
        c22 = findViewById(R.id.c22)
        c23 = findViewById(R.id.c23)
        c31 = findViewById(R.id.c31)
        c32 = findViewById(R.id.c32)
        c33 = findViewById(R.id.c33)

        d11 = findViewById(R.id.d11)
        d12 = findViewById(R.id.d12)
        d13 = findViewById(R.id.d13)
        d21 = findViewById(R.id.d21)
        d22 = findViewById(R.id.d22)
        d23 = findViewById(R.id.d23)
        d31 = findViewById(R.id.d31)
        d32 = findViewById(R.id.d32)
        d33 = findViewById(R.id.d33)

        continentA = listOf(a11, a12, a13, a21, a22, a23, a31, a32, a33)
        continentB = listOf(b11, b12, b13, b21, b22, b23, b31, b32, b33)
        continentC = listOf(c11, c12, c13, c21, c22, c23, c31, c32, c33)
        continentD = listOf(d11, d12, d13, d21, d22, d23, d31, d32, d33)

        continentButtonList = listOf(continentA, continentB, continentC, continentD)

        for (i in continentButtonList.indices) {
            for (j in continentButtonList[i].indices) {
                continentButtonList[i][j].setOnClickListener {
                    buttonClicked(continentButtonList[i][j])

                    Log.d("UI", "touched: ${getButtonId(continentButtonList[i][j])}")
                }
            }
        }

        val gameInitializer = GameInitializer(playerList, initialGameState, true, this)
        continentList = gameInitializer.listOfContinents
        playerList = gameInitializer.players
        biDirectionalLinkList = gameInitializer.biDirectionalLinkList

        phase = "reinforcement"
        getReinforcementFragment()
        updateButtons()
    }

    override fun updateButtons() {
        for ((i, continent) in continentList.continents.withIndex())
            for ((j, country) in continent.countries.withIndex()) {
                when (country.player) {
                    playerList[0].name -> changeButtonToBlue(continentButtonList[i][j])
                    playerList[1].name -> changeButtonToRed(continentButtonList[i][j])
                    //TODO: for more players
                }
                continentButtonList[i][j].text = country.count.toString()
            }
    }


    /*
    *Function to get Players, can be extended if more players allowed
     */

    /**
     *
     */
    private fun getPlayers(): MutableList<Player> {
        val playerA = Player("A", "blue", false, null)
        val playerB = Player("B", "red", true, 1)
        return mutableListOf(playerA, playerB)
    }

    private fun buttonClicked(button: Button) {
        val buttonID = getButtonId(button)
        //Check if selected country is under control of player
        var ownButton: Boolean = false
        for (continent in continentList.continents)
            for (country in continent.countries)
                if (country.name == getButtonId(button) && country.player == playerList[moveOfPlayer].name)
                    ownButton = true

        //only do something if it is the turn of the human player
        if (!playerList[moveOfPlayer].bot) {

            when (phase) {
                "reinforcement" -> buttonClickedReinforcementPhase(button, ownButton, buttonID)
                "attack" -> buttonClickedAttackPhase(button, ownButton, buttonID)
                "relocation" -> buttonClickedRelocationPhase(button, ownButton, buttonID)
            }
        } else {
            toastIt("wait for your turn")
        }
    }

    private fun buttonClickedReinforcementPhase(
        button: Button,
        ownButton: Boolean,
        buttonID: String
    ) {
        if (ownButton) {
            updateButtons()
            changeButtonToWhite(button)
            reinforcementFragment.updateCountrySelected(buttonID)

        } else {
            toastIt("not your country!")
        }
    }

    private fun buttonClickedAttackPhase(button: Button, ownButton: Boolean, buttonID: String) {
        when (ownButton) {
            true -> {
                attackFragment.updateSourceCountry(buttonID)
                attackFragment.updateTargetCountry(NO_SELECTION)
                updateButtons()
                changeButtonToWhite(button)
            }
            false -> {
                when {
                    (attackFragment.sourceCountry == NO_SELECTION) -> toastIt("choose first attacking country")
                    (attackCheck(attackFragment.sourceCountry, buttonID)) -> {
                        updateButtons()
                        val attackButton = getButtonById(attackFragment.sourceCountry)
                        attackButton?.let { changeButtonToWhite(attackButton) }
                        changeButtonToBlack(button)
                        attackFragment.updateTargetCountry(buttonID)
                    }
                    else -> toastIt("selection not valid, country must be a neighbour")
                }
            }
        }
    }

    private fun buttonClickedRelocationPhase(button: Button, ownButton: Boolean, buttonID: String) {
        when (ownButton) {
            true -> {
                val sourceCountryId = relocationFragment.sourceCountry
                updateButtons()
                when (relocationFragment.updateCountry(buttonID)) {
                    "source" -> changeButtonToWhite(button)
                    "target" -> {
                        if (relocationCheck(sourceCountryId, buttonID)) {
                            changeButtonToWhite(getButtonById(sourceCountryId))
                            changeButtonToBlack(button)
                        } else {
                            toastIt("move from ${sourceCountryId} to ${buttonID} is not allowed. Countries must be connected!")
                        }
                    }
                }
            }
        }
    }

    override fun setReinforcement(countrySelected: String, troops: Int) {

        //get button
        for (i in continentButtonList.indices)
            for (j in continentButtonList[i].indices)
                if (getButtonId(continentButtonList[i][j]) == countrySelected)

                //update button in seperate coroutine
                    GlobalScope.launch {
                        for (k in 0 until troops) {
                            increaseTroops(continentButtonList[i][j])
                            continentList.continents[i].countries[j].count++
                            this@GameActivity.runOnUiThread(Runnable {
                                updateButtons()
                            })
                            delay(70)
                        }
                    }
    }

    private fun relocationCheck(source: String, target: String): Boolean {

        Log.d("relocationCheck:", "source:${source}\t target:${target}")

        var relocationValidity = false
        //get list of possible targets and check if target country is in that list
        val countryGraph =
            JsonHandler.getCountryGraphJson(continentList, biDirectionalLinkList.bidirectionalLinks)
        val possibleTargetsJson =
            MinimalRisk.possibleDestinations(countryGraph, playerList[moveOfPlayer].name, source)

        Log.d("relocationCheck:", "MinmalRisk:${possibleTargetsJson}")

        val countryList = JsonHandler.getCountryListFromJson(possibleTargetsJson)

        for (country in countryList.countries) {

            Log.d("attackCheck:", "country in list checked: ${country}")

            if (country.name == target) {
                relocationValidity = true
                Log.d("attackCheck:", "country found:${country}")
            }
        }

        return relocationValidity
    }

    private fun attackCheck(source: String, target: String): Boolean {

        return true

        /**
        Log.d("attackCheck:","source:${source}\t target:${target}")

        var attackValidity = false
        //get list of possible targets and check if target country is in that list
        val countryGraph = JsonHandler.getCountryGraphJson(continentList, biDirectionalLinkList.bidirectionalLinks)
        val possibleTargetsJson = MinimalRisk.possibleTargetCountries(countryGraph, playerList[moveOfPlayer].name, source)

        Log.d("attackCheck:","MinmalRisk:${possibleTargetsJson}")

        val countryList = JsonHandler.getCountryListFromJson(possibleTargetsJson)

        for(country in countryList.countries) {

        Log.d("attackCheck:", "country in list checked: ${country}")

        if (country.name == target) {
        attackValidity = true
        Log.d("attackCheck:","country found:${country}")
        }
        }

        return attackValidity
         **/
    }


    override fun attack(source: String, target: String, troopsAttacking: Int, troopsLeft: Int) {
        val counterparties = listOf(getCountryById(source)!!, getCountryById(target)!!)
        battleGround = BattleGround(counterparties, troopsAttacking, troopsLeft, this)
    }

    override fun move(source: String, target: String, troops: Int) {
        val sourceCountry = getCountryById(source)
        val targetCountry = getCountryById(target)

        GlobalScope
        sourceCountry!!.count -= troops
        targetCountry!!.count += troops
        updateCountries(listOf(sourceCountry, targetCountry))
        updateButtons()

        nextPlayer()
    }

    fun botPhase() {
        val player = playerList[moveOfPlayer].name
        var newBoard: String
        var newContinentList: ContinentList


        val reinforcementTroops = getTroopsForReinforcement(player)
        newBoard =
            MinimalRisk.allocationOfExtraTroops(getJsonForBot(), player, reinforcementTroops)
        Log.d("botPhase, reinforcement", "${newBoard}")
        newContinentList = JsonHandler.getContinentListFromJson(newBoard)
        //Show Bot-Log
        for ((i, continent) in continentList.continents.withIndex())
            for ((j, country) in continent.countries.withIndex())
                if (country.count < newContinentList.continents[i].countries[j].count) {
                    val newTroops =
                        newContinentList.continents[i].countries[j].count - country.count

                    GlobalScope.launch {

                        //Only the original thread that created a view hierarchy can touch its views.
                        this@GameActivity.runOnUiThread(Runnable {
                            botFragment.addBotAction("${player} placed ${newTroops} on ${country.name}")
                        })

                        delay(300)
                    }
                }

        continentList = newContinentList
        //delay(500)
        updateButtons()


        //Attack phase of bot
        newBoard = MinimalRisk.attack(getJsonForBot(), player)
        Log.d("botPhase, attack", "${newBoard}")

        continentList = JsonHandler.getContinentListFromJson(newBoard)

        GlobalScope.launch {

            for (continent in continentList.continents)
                for (country in continent.countries)
                    if (country.modified) {
                        //Only the original thread that created a view hierarchy can touch its views.
                        this@GameActivity.runOnUiThread(Runnable {
                            botFragment.addBotAction("${player} conquered ${country.name}")
                        })
                        delay(300)
                    }

            delay(500)
            updateButtons()

        }

        //Relocation phase of bot
        newBoard = MinimalRisk.moveTroops(getJsonForBot(), player)
        Log.d("botPhase, relocation", "${newBoard}")
        newContinentList = JsonHandler.getContinentListFromJson(newBoard)
        var fromCountry = ""
        var toCountry = ""
        var troopsMoved = 0

        GlobalScope.launch {
            for ((i, continent) in continentList.continents.withIndex())
                for ((j, country) in continent.countries.withIndex())
                    if (country.modified) {
                        val troops =
                            newContinentList.continents[i].countries[j].count - country.count
                        if (troops < 0) {
                            fromCountry = country.name
                        } else {
                            toCountry = country.name
                            troopsMoved = troops
                        }
                    }
            this@GameActivity.runOnUiThread(Runnable {
                botFragment.addBotAction("${player} moved ${troopsMoved} troops from ${fromCountry} to ${toCountry}")
            })
            delay(500)
            updateButtons()
        }

    }


    override fun nextPlayer() {

        if (moveOfPlayer == playerList.size - 1) moveOfPlayer = 0 else moveOfPlayer++

        if (playerList[moveOfPlayer].bot) {
            changePhase("bot")
            getBotFragment()

            GlobalScope.launch {
                while (botFragment.view == null) {
                    Log.d("nextPlayer()", "waiting for botFragmentView")
                    delay(10)
                }
                this@GameActivity.runOnUiThread(Runnable { botPhase() })
                Log.d("nextPlayer", "botFragmentView was added")
            }
        } else {
            changePhase("reinforcement")
            getReinforcementFragment()
        }
    }


    fun getJsonForBot(): String {
        val countryGraphObject = JsonHandler.gsonTemplateConverter(
            continentList,
            biDirectionalLinkList.bidirectionalLinks
        )
        return JsonHandler.getJsonFromCountryGraph(countryGraphObject)
    }

    fun getTroopsForReinforcement(player: String): Int {
        var troops = 0
        for (continent in continentList.continents) {
            var countCountriesInContinent = 0
            var ownCountries = 0

            for (country in continent.countries) {
                countCountriesInContinent++
                if (country.player == player) ownCountries++
            }
            //Continent-Bonus: check if all countries on continent are under of control of player
            if (ownCountries == countCountriesInContinent) ownCountries += continentBonus
            troops += ownCountries / troopsPerCountryDivisor
        }
        return troops
    }

    override fun setTroopsForReinforcement() {
        val troops = getTroopsForReinforcement(playerList[moveOfPlayer].name)
        reinforcementFragment.updateTroopsAvailable(troops)
    }

    override fun toastIt(bread: String) {
        Toast.makeText(this@GameActivity, bread, Toast.LENGTH_LONG).show()
    }

    override fun getReinforcementFragment() {
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        reinforcementFragment = ReinforcementFragment().newInstance()

        if (fragmentManager.fragments.size == 0) {
            transaction.add(R.id.fragment_container, reinforcementFragment)
        } else {
            transaction.replace(R.id.fragment_container, reinforcementFragment)
        }
        transaction.commit()
    }

    override fun getAttackFragment() {
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        attackFragment = AttackFragment().newInstance()
        transaction.replace(R.id.fragment_container, attackFragment)
        transaction.commit()
        attackFragID = attackFragment.id
    }

    override fun getRelocationFragment() {
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        relocationFragment = RelocationFragment().newInstance()
        transaction.replace(R.id.fragment_container, relocationFragment)
        transaction.commit()
        relocationFragID = relocationFragment.id
    }

    override fun getBotFragment() {
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        botFragment = BotFragment().newInstance()
        transaction.replace(R.id.fragment_container, botFragment)
        transaction.commit()
        botFragmentID = botFragment.id
    }


    override fun changePhase(phase: String) {
        this.phase = phase
    }

    override fun showAttackPopup(): View {

        val view: View = findViewById(android.R.id.content)
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popUpView = inflater.inflate(R.layout.attack_popup, null)

        popUpView.findViewById<Button>(R.id.attackButtonPop).setOnClickListener {
            val counterparties = battleGround.fight()
            if (counterparties != null) {
                updateCountries(counterparties)


                GlobalScope.launch {
                    delay(2000)
                    this@GameActivity.runOnUiThread(Runnable {
                        closeAttackPopup()
                        updateButtons()
                    })
                }


            }
            //TODO:SHOW WINNER IN TOAST OR POPUP
        }



        popUpView.findViewById<Button>(R.id.fastAttackButtonPop).setOnClickListener {
            val counterparties = battleGround.fastfight()
            if (counterparties != null) {
                updateCountries(counterparties)

                GlobalScope.launch {
                    delay(1000)
                    this@GameActivity.runOnUiThread(Runnable {
                        closeAttackPopup()
                        updateButtons()
                    })

                }

                //TODO:SHOW WINNER IN TOAST OR POPUP
            }

        }

        popUpView.findViewById<Button>(R.id.withdrawalButtonPop).setOnClickListener {
            battleGround.withdrawal()
            updateButtons()
        }

        val width = 800
        val height = 800
        val focusable = true
        attackPopupWindow = PopupWindow(popUpView, width, height, focusable)

        attackPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
        return popUpView
    }

    override fun closeAttackPopup() {
        attackPopupWindow.dismiss()
    }

    fun updateCountries(pairOfCountries: List<Country?>) {
        if (pairOfCountries != null)
            for (i in 0..1) {
                val country = getCountryById(pairOfCountries[i]!!.name)
                country?.player = pairOfCountries[i]!!.player
                country?.count = pairOfCountries[i]!!.count
                //country?.modified = true
            }
    }

    private fun getButtonId(button: Button): String {
        //This is somehow ugly but no better solution was found how to get the ID
        var buttonID = button.toString()
        buttonID = buttonID.substring(buttonID.length - 4, buttonID.length - 1).toUpperCase()
        return buttonID
    }

    override fun getButtonById(buttonId: String): Button? {
        for (i in continentButtonList.indices)
            for (j in continentButtonList[i].indices)
                if (getButtonId(continentButtonList[i][j]) == buttonId) return continentButtonList[i][j]
        return null
    }

    override fun getCountryById(countryId: String): Country? {
        for (continent in continentList.continents)
            for (country in continent.countries)
                if (country.name == countryId) return country
        return null
    }

    fun changeButtonToBlue(button: Button?) {
        if (button != null) {
            button.setBackgroundResource(R.drawable.country_blue)
            button.setTextColor(Color.BLUE)
        }
    }

    fun changeButtonToRed(button: Button?) {
        if (button != null) {
            button.setBackgroundResource(R.drawable.country_red)
            button.setTextColor(Color.RED)
        }
    }

    fun changeButtonToWhite(button: Button?) {
        if (button != null) {
            button.setBackgroundResource(R.drawable.country_white)
            button.setTextColor(Color.WHITE)
        }
    }

    fun changeButtonToBlack(button: Button?) {
        if (button != null) {
            button.setBackgroundResource(R.drawable.country_black)
            button.setTextColor(Color.BLACK)
        }
    }

    fun increaseTroops(button: Button?) {
        if (button != null) {
            var troops: Int = button.text.toString().toInt()
            troops++
            button.text = troops.toString()
        }
    }


}
