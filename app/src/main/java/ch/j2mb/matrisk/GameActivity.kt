package ch.j2mb.matrisk

import android.content.pm.ActivityInfo
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import ch.j2mb.matrisk.fragments.*
import kotlinx.android.synthetic.main.activity_main.view.*


class GameActivity : AppCompatActivity(), ReinforcementFragment.ReinforcementInterface {

    val fragmentManager: FragmentManager = supportFragmentManager

    val a11:Button = findViewById(R.id.a11)
    val a12:Button = findViewById(R.id.a12)
    val a13:Button = findViewById(R.id.a13)
    val a21:Button = findViewById(R.id.a21)
    val a22:Button = findViewById(R.id.a22)
    val a23:Button = findViewById(R.id.a23)
    val a31:Button = findViewById(R.id.a31)
    val a32:Button = findViewById(R.id.a32)
    val a33:Button = findViewById(R.id.a33)

    val b11:Button = findViewById(R.id.b11)
    val b12:Button = findViewById(R.id.b12)
    val b13:Button = findViewById(R.id.b13)
    val b21:Button = findViewById(R.id.b21)
    val b22:Button = findViewById(R.id.b22)
    val b23:Button = findViewById(R.id.b23)
    val b31:Button = findViewById(R.id.b31)
    val b32:Button = findViewById(R.id.b32)
    val b33:Button = findViewById(R.id.b33)

    val c11:Button = findViewById(R.id.c11)
    val c12:Button = findViewById(R.id.c12)
    val c13:Button = findViewById(R.id.c13)
    val c21:Button = findViewById(R.id.c21)
    val c22:Button = findViewById(R.id.c22)
    val c23:Button = findViewById(R.id.c23)
    val c31:Button = findViewById(R.id.c31)
    val c32:Button = findViewById(R.id.c32)
    val c33:Button = findViewById(R.id.c33)

    val d11:Button = findViewById(R.id.d11)
    val d12:Button = findViewById(R.id.d12)
    val d13:Button = findViewById(R.id.d13)
    val d21:Button = findViewById(R.id.d21)
    val d22:Button = findViewById(R.id.d22)
    val d23:Button = findViewById(R.id.d23)
    val d31:Button = findViewById(R.id.d31)
    val d32:Button = findViewById(R.id.d32)
    val d33:Button = findViewById(R.id.d33)

    val continentA = listOf(a11, a12, a13, a21, a22, a23, a31, a32, a33)
    val continentB = listOf(b11, b12, b13, b21, b22, b23, b31, b32, b33)
    val continentC = listOf(c11, c12, c13, c21, c22, c23, c31, c32, c33)
    val continentD = listOf(d11, d12, d13, d21, d22, d23, d31, d32, d33)

    val continents = listOf(continentA, continentB, continentC, continentD)





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE



        getReinforcementFragment()
        testToaster()

        /*

        findViewById<Button>(R.id.TestFragmentChangeButton1).setOnClickListener {
            getAttackFragment()
        }

        findViewById<Button>(R.id.TestFragmentChangeButton2).setOnClickListener {
            getRelocationFragment()
        }

        findViewById<Button>(R.id.TestFragmentChangeButton3).setOnClickListener {
            getReinforcementFragment()
        }

         */


    }

    fun changeButtonToBlue(button: Button) {
        button.setBackgroundResource(R.drawable.country_blue)
        button.setTextColor(Color.BLUE)
    }

    fun changeButtonToRed(button: Button) {
        button.setBackgroundResource(R.drawable.country_red)
        button.setTextColor(Color.RED)
    }

    fun changeButtonToWhite(button: Button) {
        button.setBackgroundResource(R.drawable.country_white)
        button.setTextColor(Color.WHITE)
    }

    fun changeButtonToBlack(button: Button) {
        button.setBackgroundResource(R.drawable.country_black)
        button.setTextColor(Color.BLACK)
    }

    fun increaseTroops(button: Button){
        var troops: Int = button.text.toString().toInt()
        troops++
        button.text = troops.toString()
    }

    fun decreaseTroops(button: Button){
        var troops: Int = button.text.toString().toInt()
        troops--
        button.text = troops.toString()
    }








    fun testToaster() {
        //Toast.makeText(this@GameActivity, "Toaster clicked!!!!", Toast.LENGTH_LONG).show()
    }

    override fun testStuff() {

        for(i in 0..continents)


    }


    private fun getReinforcementFragment() {
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        val reinforcementFragment: ReinforcementFragment = ReinforcementFragment().newInstance()

        if(fragmentManager.fragments.size == 0) {
            transaction.add(R.id.fragment_container, reinforcementFragment)
        } else {
            transaction.replace(R.id.fragment_container, reinforcementFragment)
        }
        transaction.commit()
    }

    override fun getAttackFragment() {
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        val attackFragment: AttackFragment  = AttackFragment().newInstance()
        transaction.replace(R.id.fragment_container, attackFragment)
        transaction.commit()
    }

    private fun getRelocationFragment() {
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        val relocationFragment: RelocationFragment  = RelocationFragment().newInstance()
        transaction.add(R.id.fragment_container, relocationFragment)
        transaction.commit()
    }
}
