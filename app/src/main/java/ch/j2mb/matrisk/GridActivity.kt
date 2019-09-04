package com.example.test

import android.os.Bundle
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity

class GridActivity : AppCompatActivity() {

    private lateinit var gridView: GridView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grid)


        gridView = findViewById(R.id.gridView)

        val personList = arrayListOf(
            Person("Gandalf", "the Grey", "Wizard"),
            Person("David", "Goliath", "Cyclops"),
            Person("Zealot", "of the God-Pharaoh", "Minotaur Archer")
        )

        val adapter = MyTextViewAdapter(this, personList)
        gridView.adapter = adapter



    }

}
