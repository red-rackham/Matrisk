package ch.j2mb.matrisk.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import ch.j2mb.matrisk.R

const val NO_SELECTION: String = "<please select>"

class ReinforcementFragment : Fragment() {

    private lateinit var listener: ReinforcementInterface


    var troopsAvailable = 0
    var troopsSelected = 0
    var countrySelected = NO_SELECTION
    private lateinit var troopsAvailableText: TextView
    private lateinit var targetCountryText: TextView
    private lateinit var troopsSelectedText: TextView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ReinforcementInterface) listener = context
    }


    fun newInstance(): ReinforcementFragment {
        return ReinforcementFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val fragmentView = inflater.inflate(R.layout.reinforcement_fragment, container, false)

        troopsAvailableText = fragmentView.findViewById(R.id.troopsAvailableReIn)
        targetCountryText = fragmentView.findViewById(R.id.targetCountryReIn)
        troopsSelectedText = fragmentView.findViewById(R.id.nrOfTroopsSelectedReIn)

        //TODO: Remove testfunction
        //Set ClickListener on Testbutton
        fragmentView.findViewById<Button>(R.id.TestFragmentChangeButton1).setOnClickListener {
            listener.getAttackFragment()
        }

        fragmentView.findViewById<Button>(R.id.dispatchButton).setOnClickListener {
            dispatch()
        }

        fragmentView.findViewById<Button>(R.id.plusReInButton).setOnClickListener {
            addTroops()
        }

        fragmentView.findViewById<Button>(R.id.minusReInButton).setOnClickListener {
            minTroops()
        }

        fragmentView.findViewById<Button>(R.id.abortButton).setOnClickListener {
            cancel()
        }
        return fragmentView
    }

    private fun setTroops(country: String, troops: Int) {
        //TODO:
        listener.

    }

    private fun addTroops() {
        if (troopsAvailable > 0) {
            this.troopsAvailableText.text = (--troopsAvailable).toString()
            this.troopsSelectedText.text = (++troopsSelected).toString()
        }
    }

    private fun minTroops() {
        if (troopsSelected > 0) {
            troopsSelectedText.text = (--troopsSelected).toString()
            troopsAvailableText.text = (++troopsAvailable).toString()
        }
    }

    private fun cancel() {
        troopsAvailable += troopsSelected
        troopsSelected = 0
        troopsAvailableText.text = troopsAvailable.toString()
        troopsSelectedText.text = troopsSelected.toString()
        targetCountryText.text = NO_SELECTION
    }

    //TODO
    private fun dispatch() {
        if (countrySelected == NO_SELECTION) {
            //TODO: make toaster
        } else if (troopsSelected == 0) {
            //TODO: make toaster
        } else {
            setTroops(countrySelected, troopsSelected)
            troopsSelected = 0
        }

    }


    interface ReinforcementInterface {
        fun testStuff()
        fun getAttackFragment()
    }


}