package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizapp.Adapter.ClickListener
import com.example.quizapp.Adapter.QuizTopicAdapter
import com.example.quizapp.Model.QuizTopicModel
import com.example.quizapp.Views.QuizActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ClickListener {

    private val quizTopicModelList = mutableListOf<QuizTopicModel>()
    lateinit var quizTopicAdapter: QuizTopicAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        quizTopicData()
        setRecyclerView()
    }

    fun quizTopicData() {

        val quizTopicModel = QuizTopicModel(R.drawable.icons8_android_os_50, "Android")
        quizTopicModelList.add(quizTopicModel)
        val quizTopicModel1 = QuizTopicModel(R.drawable.icons8_java_64, "Java")
        quizTopicModelList.add(quizTopicModel1)
        val quizTopicModel2 = QuizTopicModel(R.drawable.icons8_kotlin_48, "Kotlin")
        quizTopicModelList.add(quizTopicModel2)
        val quizTopicModel3 = QuizTopicModel(R.drawable.icons8_networking_64, "Networking")
        quizTopicModelList.add(quizTopicModel3)
        val quizTopicModel4 =
            QuizTopicModel(R.drawable.icons8_cyber_security_50, "Computer Security")
        quizTopicModelList.add(quizTopicModel4)
        val quizTopicModel5 = QuizTopicModel(R.drawable.icons8_database_64, "Database")
        quizTopicModelList.add(quizTopicModel5)
        val quizTopicModel6 = QuizTopicModel(R.drawable.icons8_algorithm_64, "Algorithms")
        quizTopicModelList.add(quizTopicModel6)
    }

    fun setRecyclerView() {
        quizTopicAdapter = QuizTopicAdapter(this, quizTopicModelList, this)
        val layoutManager = LinearLayoutManager(this)
        recycler_view_quiz_topic.layoutManager = layoutManager
        recycler_view_quiz_topic.adapter = quizTopicAdapter

    }

    override fun onStartQuiz(quizTopicModel: QuizTopicModel, position: Int) {

        val intent = Intent(this, QuizActivity::class.java)
        startActivity(intent)
    }
}