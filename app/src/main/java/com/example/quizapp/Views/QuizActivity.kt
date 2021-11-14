package com.example.quizapp.Views

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.quizapp.Database.QuestionDAO
import com.example.quizapp.Database.QuizRoomDatabase
import com.example.quizapp.Model.AndroidQuestionModel
import com.example.quizapp.MusicController.PlaySound
import com.example.quizapp.MusicController.TimerDialog
import com.example.quizapp.R
import com.example.quizapp.Repository.QuizRepository
import com.example.quizapp.ViewModel.QuizViewModel
import com.example.quizapp.ViewModel.QuizViewModelFactory
import kotlinx.android.synthetic.main.activity_quiz.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class QuizActivity : AppCompatActivity() {

    private var questionModelList = mutableListOf<AndroidQuestionModel>()
    private lateinit var quizRoomDatabase: QuizRoomDatabase
    private lateinit var quizViewModel: QuizViewModel
    private lateinit var androidQuestionModel: AndroidQuestionModel
    private lateinit var questionDAO: QuestionDAO

    private var questionCount: Int = 0
    private var questionCounter: Int = 0
    private var scoreCount: Int = 0
    private lateinit var correctAnimation: Animation
    private lateinit var wrongAnimation: Animation

    private lateinit var countDownTimer: CountDownTimer
    var timeValue = 130
    var MUSIC_FLAG = 0
    private lateinit var timerDialog: TimerDialog
    private lateinit var playSound: PlaySound

    private var isAnswered: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        supportActionBar?.hide()

        questionDAO = QuizRoomDatabase.getQuestionObject(this).getQuestionDAO()
        val quizRepository = QuizRepository(questionDAO)
        val quizViewModelFactory = QuizViewModelFactory(quizRepository)
        quizViewModel =
            ViewModelProviders.of(this, quizViewModelFactory).get(QuizViewModel::class.java)

        playSound = PlaySound(this)
        wrongAnimation = AnimationUtils.loadAnimation(this, R.anim.incorrect_animation)
        wrongAnimation.repeatCount = 3

        correctAnimation = AnimationUtils.loadAnimation(this, R.anim.right_ans_animation)
        correctAnimation.repeatCount = 3

        insertQuestionToDB()

        quizViewModel.getRoutines().observe(this, Observer {
            questionModelList.clear()
            questionModelList.addAll(it)
            questionCount = questionModelList.size
            questionModelList.shuffle()
            moveToNextQuestion()
        })


        val radioGroup = findViewById<View>(R.id.radio_group) as RadioGroup
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radio_button1 -> {
                    Toast.makeText(this, "Option First Selected", Toast.LENGTH_SHORT).show();
                }
                R.id.radio_button2 -> {
                    Toast.makeText(this, "Option Second Selected", Toast.LENGTH_SHORT).show();
                }
                R.id.radio_button3 -> {
                    Toast.makeText(this, "Option Third Selected", Toast.LENGTH_SHORT).show();
                }
                R.id.radio_button4 -> {
                    Toast.makeText(this, "Option Fourth Selected", Toast.LENGTH_SHORT).show();
                }
            }
        }
        /*     countDownTimer = object : CountDownTimer(32000, 1000) {
                  override fun onTick(millisUntilFinished: Long) {
                      tvTimer.text = timeValue.toString()

                      timeValue -= 1
                      if (timeValue == -1) {

                          disableButtons()

                          MUSIC_FLAG = 3
                          playSound.seAudioforAnswers(MUSIC_FLAG)
                          timerDialog.timerDialog()
                      }
                  }

                  override fun onFinish() {

                  }
              }.start()*/

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

    private fun disableButtons() {
        radio_button1.isEnabled = false
        radio_button2.isEnabled = false
        radio_button3.isEnabled = false
        radio_button4.isEnabled = false

    }


    fun insertQuestionToDB() {
        val questionModel = AndroidQuestionModel(
            "Android Web Browser Is Based On?",
            "Safari",
            "Firefox",
            "Open-source Webkit",
            "Chrome",
            3
        )

        quizViewModel.addQuestionData(questionModel)

        val questionModel1 = AndroidQuestionModel(
            "Which of the following virtual machine is used by the Android operating system?",

            "Dalvik virtual machine",
            "JVM",
            "Simple virtual machine",
            "None of the above",

            1
        )
        quizViewModel.addQuestionData(questionModel1)


        val questionModel2 = AndroidQuestionModel(
            " What is the use of content provider in Android?",

            "For storing the data in the database",
            "For sharing the data between applications",
            "For sending the data from an application to another application",
            "None of the above",
            3
        )
        quizViewModel.addQuestionData(questionModel2)


        val questionModel3 = AndroidQuestionModel(
            " APK stands for??",

            "Android Phone Kit",
            "Android Page Kit",
            "Android Poster Kit",
            "Android Package Kit",

            4
        )
        quizViewModel.addQuestionData(questionModel3)


        val questionModel4 = AndroidQuestionModel(
            "What does API stand for?",

            "Android Programming Interface",
            "Application Programming Interface",
            "Android Page Interface",
            "Application Page Interface",

            1
        )
        quizViewModel.addQuestionData(questionModel4)


        val questionModel5 = AndroidQuestionModel(
            " What is an activity in android?",

            "android class",
            "android package",
            "UI Presenter",
            "A single screen in an application with supporting java code",
            4
        )
        quizViewModel.addQuestionData(questionModel5)

    }


    private fun moveToNextQuestion() {

        radio_button1.setTextColor(Color.BLACK)
        radio_button2.setTextColor(Color.BLACK)
        radio_button3.setTextColor(Color.BLACK)
        radio_button4.setTextColor(Color.BLACK)

        if (questionCounter < questionCount) {
            androidQuestionModel = questionModelList[questionCounter]
            tvQuestion.text = androidQuestionModel.question
            radio_button1.text = androidQuestionModel.option1
            radio_button2.text = androidQuestionModel.option2
            radio_button3.text = androidQuestionModel.option3
            radio_button4.text = androidQuestionModel.option4
            questionCounter++;
            isAnswered = false;
            /* countDownTimer.cancel()
             countDownTimer.start()*/

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

        if (ansPosition == androidQuestionModel.answer) {
            MUSIC_FLAG = 1
            playSound.seAudioforAnswers(MUSIC_FLAG)
            rbSelected.setBackgroundColor(Color.GREEN)
            rbSelected.startAnimation(correctAnimation)
            scoreCount++;
        } else {
            rbSelected.startAnimation(wrongAnimation)
            MUSIC_FLAG = 2
            playSound.seAudioforAnswers(MUSIC_FLAG)
            showSolution()
        }
    }

    private fun showSolution() {

        radio_button1.setBackgroundColor(Color.RED)
        radio_button2.setBackgroundColor(Color.RED)
        radio_button3.setBackgroundColor(Color.RED)
        radio_button4.setBackgroundColor(Color.RED)

        when (androidQuestionModel.answer) {
            1 -> {
                radio_button1.setTextColor(Color.BLACK)
            }
            2 -> {
                radio_button2.setTextColor(Color.BLACK)
            }
            3 -> {
                radio_button3.setTextColor(Color.BLACK)
            }
            4 -> {
                radio_button4.setTextColor(Color.BLACK)
            }
        }
        if (questionCounter < questionCount) {
            btnConfirm.text = "Next"
        } else {
            btnConfirm.text = "Finish"
        }
    }
}