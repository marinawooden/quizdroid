package edu.uw.ischool.mwoode.quizdroid.repository
import android.content.Context
import android.util.Log
import com.google.gson.stream.JsonReader
import java.io.InputStreamReader
import java.io.BufferedReader
import java.io.File
import java.io.FileReader


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
        // Initialize the repository with some hard-coded data
        val topic1 = Topic(
            "Math",
            "These are some math questions",
            listOf(
                Quiz("2 + 2", listOf("1", "2", "3", "4"), 3),
                Quiz("2 * 2", listOf("4", "5", "6", "7"), 0)
            )
        )

        val topic2 = Topic(
            "Physics",
            "Physics is cool!",
            listOf(
                Quiz("What is mc^2", listOf("y", "z", "e", "x"),2),
            )
        )

        val topic3 = Topic(
            "Marvel Superheroes",
            "Marvel is a franchise",
            listOf(
                Quiz("What's the best franchise?", listOf("Marvel", "Marvel", "Marvel", "DC"), 3)
            )
        )

        topics.addAll(listOf(topic1, topic2, topic3))
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

    private fun readJsonFile(context: Context, fileName: String): String {
        try {
            context.assets.open(fileName).use { inputStream ->
                val reader = JsonReader(InputStreamReader(inputStream))
                val jsonContent = StringBuilder()

                reader.beginObject()
                while (reader.hasNext()) {
                    val name = reader.nextName()
                    val value = reader.nextString()
                    jsonContent.append("$name: $value\n")
                }
                reader.endObject()

                return jsonContent.toString()
            }
        } catch (e: Exception) {
            Log.e("Error", "Error reading JSON file", e)
        }

        return ""
    }
}
/*

fun readJsonFile(context: Context, filePath: String): String {
    val file: File = File(filePath)
    val fileReader = FileReader(file)
    val bufferedReader = BufferedReader(fileReader)
    val stringBuilder = java.lang.StringBuilder()
    var line = bufferedReader.readLine()
    while (line != null) {
        stringBuilder.append(line).append("\n")
        line = bufferedReader.readLine()
    }
    bufferedReader.close()
// This responce will have Json Format String
// This responce will have Json Format String
    val response = stringBuilder.toString()

    return response
}

private fun readJson(jsonReader: JsonReader): String {
    val stringBuilder = StringBuilder()

    // Read the JSON content
    jsonReader.beginObject()
    while (jsonReader.hasNext()) {
        val name = jsonReader.nextName()
        val value = jsonReader.nextString()
        stringBuilder.append("$name: $value\n")
    }
    jsonReader.endObject()

    return stringBuilder.toString()
}*/
