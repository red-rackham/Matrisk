package ch.j2mb.matrisk.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import ch.j2mb.matrisk.R


class ReinforcementFragment : Fragment() {

    private lateinit var listener: ReinforcementInterface

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


        //Set ClickListener on Testbutton
        fragmentView.findViewById<Button>(R.id.TestFragmentChangeButton1).setOnClickListener {
            listener.getAttackFragment()
        }

        fragmentView.findViewById<Button>(R.id.abortButton).setOnClickListener {
            listener.testToaster()
        }


        return fragmentView
        }


    interface ReinforcementInterface {
        fun testToaster()
        fun getAttackFragment()
    }




}