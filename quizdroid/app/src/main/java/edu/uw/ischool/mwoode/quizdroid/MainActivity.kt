package edu.uw.ischool.mwoode.quizdroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)

        radioGroup.setOnCheckedChangeListener { elem, checkedId ->
            val radioButton = findViewById<RadioButton>(checkedId);
            val category = radioButton.tag as String;
            // Open new page
            val intent = Intent(this, OverviewActivity::class.java)
            intent.putExtra("category", category)
            startActivity(intent)
        }
    }
}