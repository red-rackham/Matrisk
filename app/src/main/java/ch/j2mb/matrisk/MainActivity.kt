package ch.j2mb.matrisk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onePlayerIntent = Intent(this, SinglePlayerActivity::class.java)
        val multiPlayerIntent = Intent(this, MultiPlayerActivity::class.java)
        val profileIntent = Intent(this, ProfileActivity::class.java)
        val settingsIntent = Intent(this, SettingsActivity::class.java)
        val editorIntent = Intent(this, EditorActivity::class.java)
        val aboutIntent = Intent(this, AboutActivity::class.java)

        val onePlayerButton = findViewById<Button>(R.id.singlePlayerButton)
        val multiPlayerButton = findViewById<Button>(R.id.multiPlayerbutton)
        val profileButton = findViewById<Button>(R.id.profileButton)
        val settingsButton = findViewById<Button>(R.id.settingsButton)
        val editorButton = findViewById<Button>(R.id.editorButton)
        val aboutButton = findViewById<Button>(R.id.aboutButton)

        onePlayerButton.setOnClickListener {
            startActivity(onePlayerIntent)
        }
        multiPlayerButton.setOnClickListener {
            startActivity(multiPlayerIntent)
        }
        profileButton.setOnClickListener {
            startActivity(profileIntent)
        }
        settingsButton.setOnClickListener {
            startActivity(settingsIntent)
        }
        editorButton.setOnClickListener {
            startActivity(editorIntent)
        }
        aboutButton.setOnClickListener {
            startActivity(aboutIntent)
        }
    }
}
