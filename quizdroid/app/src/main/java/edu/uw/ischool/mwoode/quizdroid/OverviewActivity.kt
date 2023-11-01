package edu.uw.ischool.mwoode.quizdroid
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*

class OverviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview)

        val category = intent.getStringExtra("category")
        val categories = loadCategories(this, "overview.json");
        val beginButton = findViewById<Button>(R.id.beginButton);

        beginButton.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java);
            intent.putExtra("category", category);
            intent.putExtra("numQuestion", "1");
            intent.putExtra("numWrong", "0");
            startActivity(intent);
        }

        Log.i("INFO", categories.getJSONObject("math").getString("title"));

        val titleDisplay = findViewById<TextView>(R.id.quizTitle);
        val descriptionDisplay = findViewById<TextView>(R.id.quizDescription);
        val quizLength = findViewById<TextView>(R.id.quizLength);

        titleDisplay.text = categories.getJSONObject(category).getString("title")
        descriptionDisplay.text = categories.getJSONObject(category).getString("description")
        quizLength.text = "This quiz has ${categories.getJSONObject(category).getString("totalQs")} questions";
    }
}

