package edu.uw.ischool.mwoode.quizdroid.repository

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

class InMemoryTopicRepository : TopicRepository {
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
}
