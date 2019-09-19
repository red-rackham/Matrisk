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
import ch.j2mb.matrisk.gameplay.model.ContinentList
import ch.j2mb.matrisk.gameplay.model.Country
import ch.j2mb.matrisk.gameplay.model.Player
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class GameActivity : AppCompatActivity(), GameActivityInterface {

    val fragmentManager: FragmentManager = supportFragmentManager
    private lateinit var playerList: MutableList<Player>

    var phase = "initialize game"
    //pointer to player in players<> that is now on the move
    var moveOfPlayer = 0
    var round: Int = 0
    var continentList = ContinentList()

    lateinit var reinforcementFragment: ReinforcementFragment
    lateinit var attackFragment: AttackFragment
    lateinit var relocationFragment: RelocationFragment
    lateinit var battleGround: BattleGround
    lateinit var attackPopupWindow: PopupWindow

    var reinforcementFragID: Int = 0
    var attackFragID: Int = 0
    var relocationFragID: Int = 0

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

    lateinit var continents: List<List<Button>>


    val initialGameState: String = "start_state.json"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        getReinforcementFragment()

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

        continents = listOf(continentA, continentB, continentC, continentD)

        for (i in continents.indices) {
            for (j in continents[i].indices) {
                continents[i][j].setOnClickListener {
                    buttonClicked(continents[i][j])

                    Log.d("onCreate", "setOnClickListener: ${getButtonId(continents[i][j])}")
                }
            }
        }

        val gameInitializer = GameInitializer(playerList, initialGameState, true, this)
        continentList = gameInitializer.listOfContinents
        playerList = gameInitializer.players
        phase = "reinforcement"
        updateButtons()
    }

    override fun updateButtons() {
        for ((i, continent) in continentList.continents.withIndex())
            for ((j, country) in continent.countries.withIndex()) {
                when (country.player) {
                    playerList[0].name -> changeButtonToBlue(continents[i][j])
                    playerList[1].name -> changeButtonToRed(continents[i][j])
                    //TODO: for more players
                }
                continents[i][j].text = country.count.toString()
            }
    }


    /*
    *Function to get Players, can be extended if more players allowed
     */
    fun getPlayers(): MutableList<Player> {
        val playerA = Player("A", "blue", false, null)
        val playerB = Player("B", "red", false, 1)
        return mutableListOf(playerA, playerB)
    }

    override fun setReinforcement(countrySelected: String, troops: Int) {
        for (i in continents.indices) {
            for (j in continents[i].indices) {
                if (getButtonId(continents[i][j]) == countrySelected) {
                    for (k in 0 until troops) increaseTroops(continents[i][j])
                    var count = continentList.continents[i].countries[j].count + troops
                    continentList.continents[i].countries[j].count = count
                }
            }
        }
    }


    fun buttonClicked(button: Button) {

        //Check if selected country is under control of player
        var ownButton: Boolean = false
        for (continent in continentList.continents)
            for (country in continent.countries)
                if (country.name == getButtonId(button) && country.player == playerList[moveOfPlayer].name)
                    ownButton = true

        //only do something if it is the turn of the human player
        if (!playerList[moveOfPlayer].bot) {

            val buttonID = getButtonId(button)
            toastIt(buttonID)

            when (phase) {
                "reinforcement" -> {
                    if (ownButton) {
                        updateButtons()
                        changeButtonToWhite(button)
                        reinforcementFragment.updateCountrySelected(buttonID)
                    }
                }

                "attack" -> {
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
                                (attackCheck(
                                    attackFragment.sourceCountry,
                                    buttonID
                                )) -> {
                                    updateButtons()
                                    val attackButton = getButtonById(attackFragment.sourceCountry)
                                    attackButton?.let { changeButtonToWhite(attackButton) }
                                    changeButtonToBlack(button)
                                    attackFragment.updateTargetCountry(buttonID)
                                }
                                else -> toastIt("selection not valid")
                            }
                        }
                    }
                }
                "relocation" -> {
                }
            }
        }
    }

    fun attackCheck(source: String, target: String): Boolean {
        //TODO: Implement Check
        return true
    }

    fun getButtonId(button: Button): String {
        //This is somehow ugly but no better solution was found how to get the ID
        var buttonID = button.toString()
        buttonID = buttonID.substring(buttonID.length - 4, buttonID.length - 1).toUpperCase()
        return buttonID
    }

    override fun getButtonById(buttonId: String): Button? {
        for (i in continents.indices)
            for (j in continents[i].indices)
                if (getButtonId(continents[i][j]) == buttonId) return continents[i][j]
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

    fun decreaseTroops(button: Button?) {
        if (button != null) {
            var troops: Int = button.text.toString().toInt()
            troops--
            button.text = troops.toString()
        }
    }


    override fun attack(source: String, target: String, troops: Int) {
        val counterparties = listOf(getCountryById(source)!!, getCountryById(target)!!)
        battleGround = BattleGround(counterparties, troops, this)
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

    override fun changePhase(phase: String) {
        this.phase = phase
    }

    override fun showAttackPopup() : View {
        val view: View = findViewById(R.id.content)
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popUpView = inflater.inflate(R.layout.attack_popup, null)

        findViewById<Button>(R.id.attackButtonPop).setOnClickListener {
                updateAfterFight(battleGround.fight())
        }

        findViewById<Button>(R.id.fastAttackButtonPop).setOnClickListener {
            updateAfterFight(battleGround.fastfight())
        }

        findViewById<Button>(R.id.withdrawalButtonPop).setOnClickListener {
            battleGround.withdrawal()
        }

        val width = 600
        val height = 800
        val focusable = false
        attackPopupWindow = PopupWindow(popUpView, width, height, focusable)

        attackPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
        return popUpView
    }

    override fun closeAttackPopup() {
        attackPopupWindow.dismiss()
    }

    fun updateAfterFight(counterparties: List<Country>?) {
        if (counterparties != null)
            for (i in 0..1) {
                val country = getCountryById(counterparties[i].name)
                country?.player = counterparties[i].player
                country?.count = counterparties[i].count
                //country?.modified = true
            }
    }


    /**
     * TestStuff
     */

    fun jsontest() {
        val jsonHandler = JsonHandler()
        var continentList: ContinentList?
        continentList = jsonHandler.getCountriesFromGson("start_state.json", this)
        if (continentList == null) {
            Log.e("geht nicht", "alles null")
        } else {
            for (continent in continentList.continents) {
                for (country in continent.countries) {
                    println(country.name)
                }
            }
        }
    }


}
