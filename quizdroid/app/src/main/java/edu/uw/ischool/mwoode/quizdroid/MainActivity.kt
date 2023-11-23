package edu.uw.ischool.mwoode.quizdroid

import MyBackgroundThread
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import edu.uw.ischool.mwoode.quizdroid.repository.InMemoryTopicRepository

class MainActivity : AppCompatActivity() {

    private val URL = "http://tednewardsandbox.site44.com/questions.json"
    private var PERMISSION_REQUEST_CODE = 123
    private val myBackgroundThread = MyBackgroundThread(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkAndRequestPermissions()

        // Save the URL to SharedPreferences
        saveUrlToPreferences(URL)

        val topicRepository = QuizApp.getInstance().topicRepository

        myBackgroundThread.start()

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

    private fun checkAndRequestPermissions() {
        val storagePermission = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        if (storagePermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun saveUrlToPreferences(url: String) {
        // Get SharedPreferences instance
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("quizdroid", Context.MODE_PRIVATE)

        // Get SharedPreferences editor
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        // Put the URL in SharedPreferences
        editor.putString("Q_URL", url)

        // Apply changes
        editor.apply()
    }

    override fun onDestroy() {
        super.onDestroy()

        // Stop the background thread when the activity is destroyed
        myBackgroundThread.stopThread()
    }
}