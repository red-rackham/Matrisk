package ch.j2mb.matrisk

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import ch.j2mb.matrisk.fragments.*


class GameActivity : AppCompatActivity(), ReinforcementFragment.ReinforcementInterface {

    val fragmentManager: FragmentManager = supportFragmentManager

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

    override fun testToaster() {
        Toast.makeText(this@GameActivity, "Toaster clicked!!!!", Toast.LENGTH_LONG).show()
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
