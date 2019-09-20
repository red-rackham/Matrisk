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
import ch.j2mb.matrisk.gameplay.helper.GameActivityInterface


class AttackFragment : Fragment() {

    private lateinit var listener: GameActivityInterface

    var troopsSelected = 0
    var troopsLeft = 0
    var sourceCountry = NO_SELECTION
    var targetCountry = NO_SELECTION

    private lateinit var sourceCountryText: TextView
    private lateinit var targetCountryText: TextView
    private lateinit var troopsSelectedText: TextView
    private lateinit var troopsLeftText: TextView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is GameActivityInterface) listener = context
    }

    fun newInstance(): AttackFragment {
        return AttackFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val fragmentView = inflater.inflate(R.layout.attack_fragment, container, false)


        sourceCountryText = fragmentView.findViewById(R.id.sourceCountryAttack)
        targetCountryText = fragmentView.findViewById(R.id.targetCountryAttack)
        troopsSelectedText = fragmentView.findViewById(R.id.nrOfTroopsSelectedAttack)
        troopsLeftText = fragmentView.findViewById(R.id.troopsAvailableAttack)

        fragmentView.findViewById<Button>(R.id.attackButton).setOnClickListener {
            attack()
        }
        fragmentView.findViewById<Button>(R.id.plusAttackButton).setOnClickListener {
            addTroops()
        }
        fragmentView.findViewById<Button>(R.id.minusAttackButton).setOnClickListener {
            minTroops()
        }

        fragmentView.findViewById<Button>(R.id.skipAttackButton).setOnClickListener {
            listener.getRelocationFragment()
            listener.updateButtons()
            listener.changePhase("relocation")
            //TODO: PopUp "do you really want to skip / goto next phase
        }
        updateTextViews()
        return fragmentView
    }

    private fun attack() {
        when {
            sourceCountry == NO_SELECTION -> listener.toastIt("select country")
            targetCountry == NO_SELECTION -> listener.toastIt("select target")
            troopsSelected < 1 -> listener.toastIt("select troops")
            else -> listener.attack(sourceCountry, targetCountry, troopsSelected, troopsLeft)
        }
    }

    private fun addTroops(){
        when {
            //troopsinCountry < 2 -> listener.toastIt("no troops for attack available in selected country")
            troopsLeft < 2 -> listener.toastIt("no troops left for attack")
            else -> {
                troopsLeft--
                troopsSelected++
                updateTextViews()
            }
        }
    }

    private fun minTroops(){
        when {
            troopsSelected < 1 -> listener.toastIt("no troops selected")
            else -> {
                troopsLeft++
                troopsSelected--
                updateTextViews()
            }
        }
    }

    private fun updateTextViews(){
        troopsSelectedText.text = troopsSelected.toString()
        troopsLeftText.text = troopsLeft.toString()
        sourceCountryText.text = sourceCountry
        targetCountryText.text = targetCountry
    }

    fun updateSourceCountry(countryId: String){
        sourceCountry = countryId
        troopsLeft = listener.getCountryById(countryId)?.count ?: 0
        troopsSelected = 0
        updateTextViews()
    }

    fun updateTargetCountry(country: String) {
        targetCountry = country
        updateTextViews()
    }
}