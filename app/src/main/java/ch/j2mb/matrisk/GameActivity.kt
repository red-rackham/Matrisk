package ch.j2mb.matrisk

import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import ch.j2mb.matrisk.fragments.*
import ch.j2mb.matrisk.gameplay.controller.GameManager
import ch.j2mb.matrisk.gameplay.helper.GameInitializer
import ch.j2mb.matrisk.gameplay.model.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Thread.sleep


class GameActivity : AppCompatActivity(), ReinforcementFragment.ReinforcementInterface {

    val fragmentManager: FragmentManager = supportFragmentManager
    var playerList = getPlayers()
    val gameManager = GameManager(playerList, "start_state.json", this)


    var a11: Button? = null
    var a12: Button? = null
    var a13: Button? = null
    var a21: Button? = null
    var a22: Button? = null
    var a23: Button? = null
    var a31: Button? = null
    var a32: Button? = null
    var a33: Button? = null

    var b11: Button? = null
    var b12: Button? = null
    var b13: Button? = null
    var b21: Button? = null
    var b22: Button? = null
    var b23: Button? = null
    var b31: Button? = null
    var b32: Button? = null
    var b33: Button? = null

    var c11: Button? = null
    var c12: Button? = null
    var c13: Button? = null
    var c21: Button? = null
    var c22: Button? = null
    var c23: Button? = null
    var c31: Button? = null
    var c32: Button? = null
    var c33: Button? = null

    var d11: Button? = null
    var d12: Button? = null
    var d13: Button? = null
    var d21: Button? = null
    var d22: Button? = null
    var d23: Button? = null
    var d31: Button? = null
    var d32: Button? = null
    var d33: Button? = null

    var continentA: List<Button?> = listOf()
    var continentB: List<Button?> = listOf()
    var continentC: List<Button?> = listOf()
    var continentD: List<Button?> = listOf()

    var continents: List<List<Button?>> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        getReinforcementFragment()
        testToaster()

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

        for (i in 0 until continents.size) {
            for (j in 0 until continents[i].size) {
                    continents[i][j]?.setOnClickListener {
                        buttonClicked(continents[i][j])
                    }
                }
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

    fun buttonClicked(button: Button?) {
        //TODO()
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


    fun testToaster() {
        Toast.makeText(this@GameActivity, "Toaster clicked!!!!", Toast.LENGTH_LONG).show()
    }



    private fun getReinforcementFragment() {
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        val reinforcementFragment: ReinforcementFragment = ReinforcementFragment().newInstance()

        if (fragmentManager.fragments.size == 0) {
            transaction.add(R.id.fragment_container, reinforcementFragment)
        } else {
            transaction.replace(R.id.fragment_container, reinforcementFragment)
        }
        transaction.commit()
    }

    override fun getAttackFragment() {
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        val attackFragment: AttackFragment = AttackFragment().newInstance()
        transaction.replace(R.id.fragment_container, attackFragment)
        transaction.commit()
    }

    private fun getRelocationFragment() {
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        val relocationFragment: RelocationFragment = RelocationFragment().newInstance()
        transaction.add(R.id.fragment_container, relocationFragment)
        transaction.commit()
    }


    /**
     * TestStuff
     */


    override fun testStuff() {

        GlobalScope.launch(context = Dispatchers.Main) {

            for (i in 0 until continents.size) {
                for (j in 0 until continents[i].size) {
                    changeButtonToBlack(continents[i][j])
                    increaseTroops(continents[i][j])
                    sleep(100)
                    changeButtonToWhite(continents[i][j])
                    increaseTroops(continents[i][j])
                    sleep(100)
                    changeButtonToRed(continents[i][j])
                    increaseTroops(continents[i][j])
                    sleep(100)
                    changeButtonToBlue(continents[i][j])
                    increaseTroops(continents[i][j])
                    sleep(100)
                }
            }
        }
    }

}
