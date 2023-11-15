package edu.uw.ischool.mwoode.quizdroid

import android.app.Application
import edu.uw.ischool.mwoode.quizdroid.data.InMemoryTopicRepository
import edu.uw.ischool.mwoode.quizdroid.data.TopicRepository
import edu.uw.ischool.mwoode.quizdroid.repository.*

class QuizApp : Application() {
    // Declare the topicRepository as a property
    val topicRepository: TopicRepository by lazy { InMemoryTopicRepository(this) }

    // Singleton pattern
    companion object {
        private lateinit var instance: QuizApp

        fun getInstance(): QuizApp {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
