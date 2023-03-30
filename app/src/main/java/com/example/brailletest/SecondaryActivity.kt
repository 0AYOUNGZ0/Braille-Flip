package com.example.brailletest

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

//new layout
class SecondaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_secondary)

        // Find views
        val editText = findViewById<EditText>(R.id.edit_text)
        val statusButton = findViewById<Button>(R.id.status_button)
        val settingsButton = findViewById<Button>(R.id.settings_button)
        val nextLineButton = findViewById<Button>(R.id.next_line_button)
        val copyAllButton = findViewById<Button>(R.id.copy_all_button)
        val clearAllButton = findViewById<Button>(R.id.clear_all_button)
        val readAllButton = findViewById<Button>(R.id.read_all_button)

        // Set click listeners
        statusButton.setOnClickListener {
            // Perform action when status button is clicked
        }

        settingsButton.setOnClickListener {
            // Perform action when settings button is clicked
        }

        nextLineButton.setOnClickListener {
            // Perform action when next line button is clicked
        }

        copyAllButton.setOnClickListener {
            // Perform action when copy all button is clicked
        }

        clearAllButton.setOnClickListener {
            // Perform action when clear all button is clicked
        }

        readAllButton.setOnClickListener {
            // Perform action when read all button is clicked
        }
    }
}