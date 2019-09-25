package ch.j2mb.matrisk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gameActivityIntent = Intent(this, GameActivity::class.java)
        val aboutIntent = Intent(this, AboutActivity::class.java)

        val onePlayerButton = findViewById<Button>(R.id.singlePlayerButton)
        val aboutButton = findViewById<Button>(R.id.aboutButton)

        onePlayerButton.setOnClickListener {
            startActivity(gameActivityIntent)
        }

        aboutButton.setOnClickListener {
            startActivity(aboutIntent)
        }
    }
}
