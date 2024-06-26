package com.example.fithub

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class lower1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lower1)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val done3_button = findViewById<Button>(R.id.done_l1)

        done3_button.setOnClickListener{

            val tracker = Tracker(this)
            val db = tracker.writableDatabase
            val disp = findViewById<TextView>(R.id.dayDisplay)

            val values = ContentValues().apply {put("status", "DONE")}

            val newRowId = db.insert("TrackData", null, values)
            val latestId = tracker.getLatestId()
            disp.text = "Day $latestId"

            db.close()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
