package ch.j2mb.matrisk

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.PopupWindow

class EndOfGameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multiplayer)

        val popItup = findViewById<Button>(R.id.popUpButton)

        popItup.setOnClickListener {
            showPopUp(findViewById(android.R.id.content))
        }
    }

    fun showPopUp(view: View) {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popUpView = inflater.inflate(R.layout.attack_popup, null)

        val width = 600
        val height = 800
        val focusable = true
        val popupWindow = PopupWindow(popUpView, width,height, focusable)

        popupWindow.showAtLocation(view, Gravity.CENTER, 0,0)
    }
}
