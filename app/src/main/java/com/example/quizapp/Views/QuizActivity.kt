package com.example.quizapp.Views

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.Database.QuizDatabaseHandler
import com.example.quizapp.Model.QuestionModel
import com.example.quizapp.R
import kotlinx.android.synthetic.main.activity_quiz.*


class QuizActivity : AppCompatActivity() {

    private var questionModelList = mutableListOf<QuestionModel>()
    private val quizDatabaseHandler = QuizDatabaseHandler(this)
    private lateinit var questionModel: QuestionModel
    private var questionCount: Int = 0
    private var questionCounter: Int = 0
    private var scoreCount: Int = 0
    private var isAnswered: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        insertQuestionToDB()

        questionModelList.addAll(quizDatabaseHandler.getQuestionData())
        questionCount = questionModelList.size
        questionModelList.shuffle()

        moveToNextQuestion()

        btnConfirm.setOnClickListener {
            if (!isAnswered) {
                if (radio_button1.isChecked || radio_button2.isChecked || radio_button3.isChecked || radio_button4.isChecked) {
                    checkAnswerIsCorrectORNot()
                } else {
                    Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
                }
            } else {
                moveToNextQuestion()
            }
        }
    }


    fun insertQuestionToDB() {
        val questionModel = QuestionModel(
            "Android Web Browser Is Based On?",
            "Safari",
            "Firefox",
            "Open-source Webkit",
            "Chrome",
            3
        )
        quizDatabaseHandler.addQuestionToDB(questionModel)

        val questionModel1 = QuestionModel(
            "Which of the following virtual machine is used by the Android operating system?",

            "Dalvik virtual machine",
            "JVM",
            "Simple virtual machine",
            "None of the above",

            1
        )
        quizDatabaseHandler.addQuestionToDB(questionModel1)

        val questionModel2 = QuestionModel(
            " What is the use of content provider in Android?",

            "For storing the data in the database",
            "For sharing the data between applications",
            "For sending the data from an application to another application",
            "None of the above",
            3
        )
        quizDatabaseHandler.addQuestionToDB(questionModel2)

        val questionModel3 = QuestionModel(
            " APK stands for??",

            "Android Phone Kit",
            "Android Page Kit",
            "Android Poster Kit",
            "Android Package Kit",

            4
        )
        quizDatabaseHandler.addQuestionToDB(questionModel3)

        val questionModel4 = QuestionModel(
            "What does API stand for?",

            "Android Programming Interface",
            "Application Programming Interface",
            "Android Page Interface",
            "Application Page Interface",

            1
        )
        quizDatabaseHandler.addQuestionToDB(questionModel4)

        val questionModel5 = QuestionModel(
            " What is an activity in android?",

            "android class",
            "android package",
            "UI Presenter",
            "A single screen in an application with supporting java code",
            4
        )
        quizDatabaseHandler.addQuestionToDB(questionModel5)
    }


    private fun moveToNextQuestion() {

        if (questionCounter < questionCount) {
            questionModel = questionModelList[questionCounter]

            text_view_question.text = questionModel.question
            radio_button1.text = questionModel.option1
            radio_button2.text = questionModel.option2
            radio_button3.text = questionModel.option3
            radio_button4.text = questionModel.option4
            questionCounter++;
            isAnswered = false;

        } else {
            quizEnded()
        }
    }

    private fun quizEnded() {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("scoreResult", scoreCount)
        startActivity(intent)
    }

    private fun checkAnswerIsCorrectORNot() {
        isAnswered = true
        val rbSelected: RadioButton = findViewById(radio_group.checkedRadioButtonId)
        var ansPosition = radio_group.indexOfChild(rbSelected) + 1

        if (ansPosition == questionModel.answer) {
            scoreCount++;
            text_view_score.text = "Score: / $scoreCount";
        }
        showSolution()
    }

    private fun showSolution() {

        radio_button1.setTextColor(Color.RED)
        radio_button2.setTextColor(Color.RED)
        radio_button3.setTextColor(Color.RED)
        radio_button4.setTextColor(Color.RED)

        when (questionModel.answer) {
            1 -> {
                radio_button1.setTextColor(Color.BLACK)
                radio_button1.setBackgroundColor(Color.GREEN)
            }
            2 -> {
                radio_button2.setTextColor(Color.BLACK)
                radio_button2.setBackgroundColor(Color.GREEN)
            }
            3 -> {
                radio_button3.setTextColor(Color.BLACK)
                radio_button3.setBackgroundColor(Color.GREEN)
            }
            4 -> {
                radio_button4.setTextColor(Color.BLACK)
                radio_button4.setBackgroundColor(Color.GREEN)
            }
        }
        if (questionCounter < questionCount) {
            btnConfirm.text = "Next"
        } else {
            btnConfirm.text = "Finish"
        }
    }
}