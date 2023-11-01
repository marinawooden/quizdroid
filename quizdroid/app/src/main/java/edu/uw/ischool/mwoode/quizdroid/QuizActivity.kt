package edu.uw.ischool.mwoode.quizdroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import java.lang.Integer.parseInt

class QuizActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        val category = intent.getStringExtra("category");
        val numQuestion = intent.getStringExtra("numQuestion");
        var numWrong = intent.getStringExtra("numWrong");

        val categories = loadCategories(this, "questions.json");

        val questionTitle = findViewById<TextView>(R.id.questionTitle);
        val questionText = findViewById<TextView>(R.id.questionDescription);

        val currQuestion = categories
            .getJSONObject(category)
            .getJSONObject("questions")
            .getJSONObject(numQuestion);

        questionTitle.text = "Question ${numQuestion}";
        questionText.text = currQuestion.getString("question");

        val radioGroup = findViewById<RadioGroup>(R.id.answerGroup)

        for (i in 0 until currQuestion.getJSONArray("answers").length()) {
            val answer = currQuestion.getJSONArray("answers").getString(i);
            val radioButton = RadioButton(this);
            radioButton.text = answer;
            radioButton.tag = answer;
            // Add the radio button to your layout or view

            radioGroup.addView(radioButton);
        }

        // make next button interactive
        val nextQuestionButton = findViewById<Button>(R.id.answerButton);

        nextQuestionButton.setOnClickListener {
            val selectedRadioButtonId = radioGroup.checkedRadioButtonId
            Log.i("INFO", "BUTTON ID IS: " + selectedRadioButtonId );
            // only works if radio button is selected
            if (selectedRadioButtonId != -1) {
                val selectedOption = findViewById<RadioButton>(selectedRadioButtonId);

                Log.i("QUESTION", currQuestion.getString("correctAnswer"))
                val isCorrect = currQuestion.getString("correctAnswer") == selectedOption.tag as String;
                Log.i("QUESTION", isCorrect.toString());
                val numTotalQuestions = categories
                    .getJSONObject(category)
                    .getJSONObject("questions").length();

                numWrong =  if (isCorrect) { numWrong } else { numWrong + 1 };

                Log.i("INFO", numTotalQuestions.toString())

                if (parseInt(numQuestion) <= numTotalQuestions) {
                    val intent = Intent(this, AnswerActivity::class.java);
                    intent.putExtra("category", category);
                    intent.putExtra("numQuestion", numQuestion);
                    intent.putExtra("wasWrong", (!isCorrect).toString());
                    intent.putExtra("numWrong", numWrong);
                    intent.putExtra("theirAnswer", selectedOption.tag as String);
                    intent.putExtra("correctAnswer", currQuestion.getString("correctAnswer"));
                    intent.putExtra("isLastAnswer", (parseInt(numQuestion) == numTotalQuestions).toString());

                    startActivity(intent);
                }

            } else {
                Log.i("INFO", "Nothing selected");
            }
        }

    }
}
