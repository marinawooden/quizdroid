package edu.uw.ischool.mwoode.quizdroid

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import java.lang.Integer.parseInt

class QuizActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        var category = intent.getStringExtra("category");
        val numQuestion = intent.getStringExtra("numQuestion");
        var numWrong = intent.getStringExtra("numWrong");

        // val categories = loadCategories(this, "questions.json");
        val topicRepository = QuizApp.getInstance().topicRepository

        val questionTitle = findViewById<TextView>(R.id.questionTitle);
        val questionText = findViewById<TextView>(R.id.questionDescription);

        val currTopic = topicRepository.getTopicByTitle(category as String)

//        Log.i("CAT", currTopic?.questions.toString());
//        Log.i("CAT", numQuestion as String);
//        Log.i("CAT", currTopic?.questions?.get((numQuestion as Int) - 1).toString());

        val currQuestion = currTopic?.questions?.get(parseInt(numQuestion) - 1)
        Log.i("CAT", currQuestion.toString())


        questionTitle.text = "Question ${numQuestion}";
        questionText.text = currQuestion?.question;

        val radioGroup = findViewById<RadioGroup>(R.id.answerGroup)

        for (i in 0 until currQuestion?.answers?.size!!) {
            val answer = currQuestion?.answers?.get(i);
            val radioButton = RadioButton(this);
            radioButton.text = answer;
            radioButton.tag = i;

            // Add the radio button to your layout or view
            radioGroup.addView(radioButton);
        }

        // make next button interactive
        val nextQuestionButton = findViewById<Button>(R.id.answerButton);

        nextQuestionButton.setOnClickListener {
            val selectedRadioButtonId = radioGroup.checkedRadioButtonId
            // only works if radio button is selected
            if (selectedRadioButtonId != -1) {
                val selectedOption = findViewById<RadioButton>(selectedRadioButtonId);

                val isCorrect = currQuestion.correctAnswer == (selectedOption.tag as Int);
                val numTotalQuestions = currTopic.questions.size

                numWrong = if (isCorrect) { numWrong } else { numWrong + 1 };

                if (parseInt(numQuestion) <= numTotalQuestions) {
                    val intent = Intent(this, AnswerActivity::class.java);
                    intent.putExtra("category", category);
                    intent.putExtra("numQuestion", numQuestion);
                    intent.putExtra("wasWrong", (!isCorrect).toString());
                    intent.putExtra("numWrong", numWrong);
                    intent.putExtra("theirAnswer", currQuestion.answers[selectedOption.tag as Int]);
                    intent.putExtra("correctAnswer",
                        currQuestion.answers[currQuestion.correctAnswer]
                    );
                    intent.putExtra("isLastAnswer", (parseInt(numQuestion) == numTotalQuestions).toString());

                    startActivity(intent);
                }

            } else {
                Log.i("INFO", "Nothing selected");
            }
        }

    }
}
