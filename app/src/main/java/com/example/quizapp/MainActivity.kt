package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizapp.Adapter.ClickListener
import com.example.quizapp.Adapter.QuizTopicAdapter
import com.example.quizapp.Database.QuestionDAO
import com.example.quizapp.Database.QuizRoomDatabase
import com.example.quizapp.Model.QuizTopicModel
import com.example.quizapp.Repository.QuizRepository
import com.example.quizapp.ViewModel.QuizViewModel
import com.example.quizapp.ViewModel.QuizViewModelFactory
import com.example.quizapp.Views.QuizActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ClickListener {

    private lateinit var quizRoomDatabase: QuizRoomDatabase
    private lateinit var quizViewModel: QuizViewModel
    private lateinit var questionDAO: QuestionDAO

    private val quizTopicModelList = mutableListOf<QuizTopicModel>()
    lateinit var quizTopicAdapter: QuizTopicAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        questionDAO = QuizRoomDatabase.getQuestionObject(this).getQuestionDAO()
        val quizRepository = QuizRepository(questionDAO)
        val quizViewModelFactory = QuizViewModelFactory(quizRepository)
        quizViewModel =
            ViewModelProviders.of(this, quizViewModelFactory).get(QuizViewModel::class.java)

        quizViewModel.deleteAllQuestions()

        quizTopicData()
        setRecyclerView()
    }

    fun quizTopicData() {

        val quizTopicModel = QuizTopicModel(R.drawable.icons8_android_os_48, "Android")
        quizTopicModelList.add(quizTopicModel)
        val quizTopicModel1 = QuizTopicModel(R.drawable.icons8_java_48, "Java")
        quizTopicModelList.add(quizTopicModel1)
        val quizTopicModel2 = QuizTopicModel(R.drawable.icons8_kotlin_48, "Kotlin")
        quizTopicModelList.add(quizTopicModel2)
        val quizTopicModel3 = QuizTopicModel(R.drawable.icons8_networking_manager_48, "Networking")
        quizTopicModelList.add(quizTopicModel3)
        val quizTopicModel4 = QuizTopicModel(R.drawable.icons8_cyber_security_50, "Security")
        quizTopicModelList.add(quizTopicModel4)
        val quizTopicModel5 = QuizTopicModel(R.drawable.icons8_database_47, "Database")
        quizTopicModelList.add(quizTopicModel5)
        val quizTopicModel6 = QuizTopicModel(R.drawable.icons8_programming_48, "Algorithms")
        quizTopicModelList.add(quizTopicModel6)
        val quizTopicModel7 = QuizTopicModel(R.drawable.icons8_javascript_48, "Javascript")
        quizTopicModelList.add(quizTopicModel7)
        val quizTopicModel8 = QuizTopicModel(R.drawable.icons8_python_48, "Python")
        quizTopicModelList.add(quizTopicModel8)
        val quizTopicModel9 = QuizTopicModel(R.drawable.icons8_c___48, "C++")
        quizTopicModelList.add(quizTopicModel9)
        val quizTopicModel10 = QuizTopicModel(R.drawable.icons8_hacking_48, "Hacking")
        quizTopicModelList.add(quizTopicModel10)
    }

    fun setRecyclerView() {
        quizTopicAdapter = QuizTopicAdapter(this, quizTopicModelList, this)
        val layoutManager = GridLayoutManager(this,3, GridLayoutManager.VERTICAL, false)
        recycler_view_quiz_topic.layoutManager = layoutManager
        recycler_view_quiz_topic.adapter = quizTopicAdapter

    }

    override fun onStartQuiz(quizTopicModel: QuizTopicModel, position: Int) {
        val intent = Intent(this, QuizActivity::class.java)
        intent.putExtra("TopicName", quizTopicModel.topicName)
        startActivity(intent)
    }
}