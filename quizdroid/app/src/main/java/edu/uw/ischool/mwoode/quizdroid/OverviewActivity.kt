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

        val topicRepository = QuizApp.getInstance().topicRepository
        val category = intent.getStringExtra("category")
        // val categories = loadCategories(this, "overview.json");
        val beginButton = findViewById<Button>(R.id.beginButton);

        // Log.i("INFO", categories.getJSONObject("math").getString("title"));

        val titleDisplay = findViewById<TextView>(R.id.quizTitle);
        val descriptionDisplay = findViewById<TextView>(R.id.quizDescription);
        val quizLength = findViewById<TextView>(R.id.quizLength);

        val topic = category?.let { topicRepository.getTopicByTitle(it) }

        if (topic != null) {
            titleDisplay.text = topic.title
            descriptionDisplay.text = topic.longDescription
            quizLength.text = "This quiz has ${topic.questions.size} questions";

            beginButton.setOnClickListener {
                val intent = Intent(this, QuizActivity::class.java);
                intent.putExtra("category", category);
                intent.putExtra("numQuestion", "1");
                intent.putExtra("numWrong", "0");
                startActivity(intent);
            }
        }
    }
}

