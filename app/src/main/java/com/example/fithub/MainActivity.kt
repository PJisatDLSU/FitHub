package com.example.fithub

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    // Method to get status for each day based on the ID
    private fun getStatusForDay(dayId: Int): String {
        // Implement your logic to get status for each day based on the ID
        // For now, let's assume all days have the status "SKIP" for demonstration
        return "SKIP"
    }

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val u1_button = findViewById<Button>(R.id.u1)
        val u2_button = findViewById<Button>(R.id.u2)
        val u3_button = findViewById<Button>(R.id.u3)
        val l1_button = findViewById<Button>(R.id.low1)
        val l2_button = findViewById<Button>(R.id.low2)

        u1_button.setOnClickListener {
            val Intent1 = Intent(this, upper1::class.java)
            startActivity(Intent1)
        }
        u2_button.setOnClickListener {
            val Intent2 = Intent(this, upper2::class.java)
            startActivity(Intent2)
        }
        u3_button.setOnClickListener {
            val Intent5 = Intent(this, upper3::class.java)
            startActivity(Intent5)
        }
        l1_button.setOnClickListener {
            val Intent3 = Intent(this, lower1::class.java)
            startActivity(Intent3)
        }
        l2_button.setOnClickListener {
            val Intent4 = Intent(this, lower2::class.java)
            startActivity(Intent4)
        }

        //SQL buttons
        val skipButton = findViewById<Button>(R.id.skipBTN)
        val restButton = findViewById<Button>(R.id.restBTN)
        val refreshButton = findViewById<Button>(R.id.refBTN)
        val disp = findViewById<TextView>(R.id.dayDisplay)

        val tracker = Tracker(this)
        val db_color = tracker.writableDatabase
        val latestId = tracker.getLatestId()
        disp.text = "Day $latestId"

        skipButton.setOnClickListener {
            val tracker = Tracker(this)
            val db = tracker.writableDatabase

            val values = ContentValues().apply {
                put("status", "SKIP")
            }

            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.apply {
                setTitle("Confirm Skip")
                setMessage("Are you sure you want to skip the day?")
                setPositiveButton("Yes") { _, _ ->
                    val tracker = Tracker(this@MainActivity)
                    val db = tracker.writableDatabase

                    // Insert the "REST" status into the database
                    val newRowId = db.insert("TrackData", null, values)

                    // Get the latest ID from the database
                    val latestId = tracker.getLatestId()

                    // Display the latest ID in the TextView
                    disp.text = "Day $latestId"

                    db.close()

                }
                show()
            }
        }
        restButton.setOnClickListener {
            val tracker = Tracker(this)
            val db = tracker.writableDatabase

            val values = ContentValues().apply {
                put("status", "REST")
            }

            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.apply {
                setTitle("Confirm Rest")
                setMessage("Are you sure you want to rest the day?")
                setPositiveButton("Yes") { _, _ ->
                    val newRowId = db.insert("TrackData", null, values)
                    val latestId = tracker.getLatestId()
                    disp.text = "Day $latestId"

                    db.close()
                }
                show()
            }
        }
        refreshButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        //Assign ID to day number
        val days = arrayOf(
            R.id.day1, R.id.day2, R.id.day3, R.id.day4, R.id.day5, R.id.day6, R.id.day7,
            R.id.day8, R.id.day9, R.id.day10, R.id.day11, R.id.day12, R.id.day13, R.id.day14,
            R.id.day15, R.id.day16, R.id.day17, R.id.day18, R.id.day19, R.id.day20, R.id.day21,
            R.id.day22, R.id.day23, R.id.day24, R.id.day25, R.id.day26, R.id.day27, R.id.day28,
            R.id.day29, R.id.day30
        )
        for (i in 1..30) {
            val dayId = days[i - 1]
            val dayView = findViewById<View>(dayId)
            val status = tracker.getStatusForDay(i)

            when (status) {
                "SKIP" -> dayView.setBackgroundResource(R.drawable.red_circle)
                "REST" -> dayView.setBackgroundResource(R.drawable.yellow_circle)
                "DONE" -> dayView.setBackgroundResource(R.drawable.green_circle)
                else -> {
                    // Do nothing or set default background
                }
            }
        }
    }
}

