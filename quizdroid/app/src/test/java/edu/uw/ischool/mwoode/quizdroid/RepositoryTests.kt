package edu.uw.ischool.mwoode.quizdroid

import edu.uw.ischool.mwoode.quizdroid.repository.*
import org.junit.Test
import org.junit.Assert.*

class TopicRepositoryTest {

    @Test
    fun createTopic_AddsTopicToRepository() {
        val topicRepository = InMemoryTopicRepository()
        val topic = Topic("Sample Topic", "Short description", emptyList())

        topicRepository.createTopic(topic)

        val retrievedTopic = topicRepository.getTopicByTitle("Sample Topic")
        assertNotNull(retrievedTopic)
        assertEquals("Sample Topic", retrievedTopic?.title)
    }

    @Test
    fun createTopic_ReturnsValidId() {
        val topicRepository = InMemoryTopicRepository()
        val topic = Topic("Sample Topic", "Long description", emptyList())

        val hello = topicRepository.createTopic(topic)
        assertTrue(hello.title === "Sample Topic")
    }

    @Test
    fun getTopicByTitle_ReturnsNullForNonExistentTopic() {
        val topicRepository = InMemoryTopicRepository()
        val retrievedTopic = topicRepository.getTopicByTitle("Non-existent Topic")
        assertNull(retrievedTopic)
    }

    // Add more simple tests for other methods as needed

}
