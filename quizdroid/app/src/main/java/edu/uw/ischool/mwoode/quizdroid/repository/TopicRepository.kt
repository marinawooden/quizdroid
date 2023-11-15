package edu.uw.ischool.mwoode.quizdroid.data
import android.content.Context
import org.json.JSONArray


data class Quiz(
    val question: String,
    val answers: List<String>,
    val correctAnswer: Int
)

data class Topic(
    val title: String,
    val longDescription: String,
    val questions: List<Quiz>
)

interface TopicRepository {
    fun getAllTopics(): List<Topic>
    fun getTopicByTitle(title: String): Topic?
    fun createTopic(topic: Topic): Topic

}

class InMemoryTopicRepository(private val context: Context) : TopicRepository {
    private val topics: MutableList<Topic> = mutableListOf()

    init {
        val jsonString = context.assets.open("questions.json")
            .bufferedReader()
            .use { it.readText() }

        val jsonArray = JSONArray(jsonString)

        for (i in 0 until jsonArray.length()) {
            val topicObject = jsonArray.getJSONObject(i)
            val title = topicObject.getString("title")
            val description = topicObject.getString("desc")
            val questionsArray = topicObject.getJSONArray("questions")

            val quizzes = mutableListOf<Quiz>()

            for (j in 0 until questionsArray.length()) {
                val questionObject = questionsArray.getJSONObject(j)
                val questionText = questionObject.getString("text")
                val correctAnswerIndex = questionObject.getString("answer").toInt()
                val answersArray = questionObject.getJSONArray("answers")

                val answers = mutableListOf<String>()
                for (k in 0 until answersArray.length()) {
                    answers.add(answersArray.getString(k))
                }

                quizzes.add(Quiz(questionText, answers, correctAnswerIndex))
            }

            val topic = Topic(title, description, quizzes)
            createTopic(topic)
        }
    }

    override fun getAllTopics(): List<Topic> {
        return topics
    }

    override fun getTopicByTitle(title: String): Topic? {
        return topics.find { it.title == title }
    }

    override fun createTopic(topic: Topic): Topic {
        topics.add(topic)
        return topic
    }
}
