package edu.uw.ischool.mwoode.quizdroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import java.lang.Integer.parseInt

class AnswerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer)
        val category = intent.getStringExtra("category");
        var numQuestion = intent.getStringExtra("numQuestion");
        val wasWrong = intent.getStringExtra("wasWrong");
        var numWrong = intent.getStringExtra("numWrong");
        val theirAnswer = intent.getStringExtra("theirAnswer");
        val expectedAnswer = intent.getStringExtra("correctAnswer");
        val isLastAnswer = intent.getStringExtra("isLastAnswer");

        val resultTitleDisplay = findViewById<TextView>(R.id.answerResult);
        val correctAnswer = findViewById<TextView>(R.id.expectedAnswer);
        val yourAnswer = findViewById<TextView>(R.id.yourAnswer);
        val totalWrong = findViewById<TextView>(R.id.totalWrong);

        val nextQuestionButton = findViewById<Button>(R.id.nextQuestion);
        val prevQuestionButton = findViewById<Button>(R.id.prevQuestion);

        Log.i("INFO", wasWrong.toString())
        resultTitleDisplay.text = if (wasWrong == "true") { "That's incorrect" } else { "That's right!" }
        yourAnswer.text = "Your answer: $theirAnswer";
        correctAnswer.text = "Correct answer: $expectedAnswer";
        totalWrong.text = "${parseInt(numQuestion) - parseInt(numWrong)}/$numQuestion correct";
        nextQuestionButton.text = if (isLastAnswer != "true") { "Next Question" } else { "Finish" }

        // next question button functionality
        nextQuestionButton.setOnClickListener{
            numQuestion = (parseInt(numQuestion) + 1).toString();

            if (isLastAnswer != "true") {
                // go to next question
                val intent = Intent(this, QuizActivity::class.java);
                intent.putExtra("category", category);
                intent.putExtra("numQuestion", numQuestion);
                intent.putExtra("numWrong", numWrong);
                startActivity(intent);
            } else {
                // finish
                val intent = Intent(this, MainActivity::class.java);
                startActivity(intent);
            }
        }

        // prev button functionality
        prevQuestionButton.setOnClickListener {
            numWrong = if (wasWrong == "true") { (parseInt(numWrong) - 1).toString() } else { numWrong };
            val intent = Intent(this, QuizActivity::class.java);
            intent.putExtra("category", category);
            intent.putExtra("numQuestion", numQuestion);
            intent.putExtra("numWrong", numWrong);
            startActivity(intent);
        }
    }
}