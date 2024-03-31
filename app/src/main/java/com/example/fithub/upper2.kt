package com.example.fithub

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class upper2 : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_upper2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val done2_button = findViewById<Button>(R.id.done_u2)

        done2_button.setOnClickListener{
            val tracker = Tracker(this)
            val db = tracker.writableDatabase
            val disp = findViewById<TextView>(R.id.dayDisplay)

            val values = ContentValues().apply {put("status", "DONE")}

            val newRowId = db.insert("TrackData", null, values)
            val latestId = tracker.getLatestId()
            disp.text = "Day $latestId"

            db.close()

            val Intent = Intent(this,MainActivity::class.java)
            startActivity(Intent)
        }
    }
}