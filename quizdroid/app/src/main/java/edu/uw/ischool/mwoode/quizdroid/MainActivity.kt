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

        // Access the topicRepository through the Singleton
        val topicRepository = QuizApp.getInstance().topicRepository

        // Now you can use the topicRepository to get topics or perform other operations
        val allTopics = topicRepository.getAllTopics()
        Log.i("INFO", allTopics.toString())

        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)

        for (category in allTopics) {
            val radioButton = RadioButton(this) // Create a new RadioButton
            radioButton.text = category.title
            radioButton.tag = category.title

            radioGroup.addView(radioButton) // A
        }

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