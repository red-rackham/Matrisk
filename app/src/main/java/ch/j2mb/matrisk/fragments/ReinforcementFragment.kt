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
import kotlinx.android.synthetic.*
import org.w3c.dom.Text


class ReinforcementFragment : Fragment() {

    private lateinit var listener: ReinforcementInterface
    var troopsAvailable: TextView? = null
    var targetCountry: TextView? = null
    var troopsSelected: TextView? = null




    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is ReinforcementInterface) listener = context
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

        troopsAvailable = fragmentView.findViewById(R.id.troopsAvailableReIn)
        targetCountry = fragmentView.findViewById(R.id.targetCountryReIn)
        troopsSelected = fragmentView.findViewById(R.id.nrOfTroopsSelectedReIn)


        //Set ClickListener on Testbutton
        fragmentView.findViewById<Button>(R.id.TestFragmentChangeButton1).setOnClickListener {
            listener.getAttackFragment()
        }

        fragmentView.findViewById<Button>(R.id.abortButton).setOnClickListener {
            listener.testStuff()
        }


        return fragmentView
        }

    fun setTroops(troops: Int) {



    }


    interface ReinforcementInterface {
        fun testStuff()
        fun getAttackFragment()
    }




}