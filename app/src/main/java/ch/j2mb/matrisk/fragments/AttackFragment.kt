package ch.j2mb.matrisk.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ch.j2mb.matrisk.R


class AttackFragment : Fragment() {

    fun newInstance(): AttackFragment {
        return AttackFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.attack_fragment, container, false)
    }


}