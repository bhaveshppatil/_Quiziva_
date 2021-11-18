package com.example.quizapp.Views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.quizapp.Database.QuestionDAO
import com.example.quizapp.Database.QuizRoomDatabase
import com.example.quizapp.MainActivity
import com.example.quizapp.Model.AndroidQuestionModel
import com.example.quizapp.R
import com.example.quizapp.Repository.QuizRepository
import com.example.quizapp.ViewModel.QuizViewModel
import com.example.quizapp.ViewModel.QuizViewModelFactory
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    private lateinit var quizRoomDatabase: QuizRoomDatabase
    private lateinit var quizViewModel: QuizViewModel
    private lateinit var questionDAO: QuestionDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        questionDAO = QuizRoomDatabase.getQuestionObject(this).getQuestionDAO()
        val quizRepository = QuizRepository(questionDAO)
        val quizViewModelFactory = QuizViewModelFactory(quizRepository)
        quizViewModel =
            ViewModelProviders.of(this, quizViewModelFactory).get(QuizViewModel::class.java)

        quizViewModel.deleteAllQuestions()
        val intent = intent
        val score = intent.getIntExtra("correct", 0)
        val wrong = intent.getIntExtra("wrong", 0)

        tvCorrectAns.text = "Correct Answers - $score / $wrong"
        tvPointsEarned.text = "You Earned $score Points"

        btnDone.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}