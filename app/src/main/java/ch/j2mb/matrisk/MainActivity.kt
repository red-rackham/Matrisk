package ch.j2mb.matrisk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

/***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 3 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/

/**
 *<p> This project is a simpified implementation of a  <a href="https://en.wikipedia.org/wiki/Risk_(game)">
 *     Risk-like game</a> for Android.
 * <p>It is a study project from Jakob Bolliger and Jens Meydam who provided the AI-logic for the game.
 * <p>The AI part of this project is found found in packet
 * ch.j2mb.matrisk.ai_machine or as a stand-alone simulation here:
 * <a href="https://github.com/jmeydam/minimalrisk">https://github.com/jmeydam/minimalrisk</a></p>
 *
 * @author Jakob Bolliger (jakob at jpost.ch)
 * @date 27.09.2019
 */

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
