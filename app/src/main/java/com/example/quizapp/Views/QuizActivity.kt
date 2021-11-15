package com.example.quizapp.Views

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.RadioButton
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

    private var questionTotal: Int = 0

    //    private lateinit var countDownTimer: CountDownTimer
//    var timeValue = 130
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

        val insertQuestions = InsertAndroidQuestions()
        for (i in 0..insertQuestions.insertQuestionToDB().size) {
            quizViewModel.addQuestionData(insertQuestions.insertQuestionToDB()[i])
        }

        quizViewModel.getAndroidQuestions().observe(this, Observer {
            questionModelList.clear()
            questionModelList.addAll(it)
            questionCount = questionModelList.size
            questionModelList.shuffle()
            moveToNextQuestion()
        })

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

    /* private fun disableButtons() {
         radio_button1.isEnabled = false
         radio_button2.isEnabled = false
         radio_button3.isEnabled = false
         radio_button4.isEnabled = false

     }*/

    private fun moveToNextQuestion() {
        var intent = Intent()
        intent = getIntent()
        val topicName: String? = intent.getStringExtra("TopicName")

        questionTotal++

        tvTotalQuestions.text = "$questionTotal / ${questionModelList.size}"

        radio_group.clearCheck()
        radio_button1.setTextColor(Color.BLACK)
        radio_button2.setTextColor(Color.BLACK)
        radio_button3.setTextColor(Color.BLACK)
        radio_button4.setTextColor(Color.BLACK)

        radio_button1.setBackgroundColor(Color.WHITE)
        radio_button2.setBackgroundColor(Color.WHITE)
        radio_button3.setBackgroundColor(Color.WHITE)
        radio_button4.setBackgroundColor(Color.WHITE)
        if (topicName?.equals("Android") == true) {
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
        } else {
            layoutMain.visibility = View.GONE
            tvNoQuizFound.visibility = View.VISIBLE
            tvNoQuizFound.text = "$topicName data not found, attend another quiz"
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
            rbSelected.setBackgroundResource(R.drawable.correct_ans_bg)
            playSound.seAudioforAnswers(MUSIC_FLAG)
            rbSelected.startAnimation(correctAnimation)
            scoreCount++;
        } else {
            rbSelected.startAnimation(wrongAnimation)
            rbSelected.setBackgroundResource(R.drawable.wrong_ans_bg)

            MUSIC_FLAG = 2
            playSound.seAudioforAnswers(MUSIC_FLAG)

            showSolution()
        }
    }

    private fun showSolution() {

        when (androidQuestionModel.answer) {
            1 -> {
                radio_button1.setBackgroundResource(R.drawable.correct_ans_bg)
            }
            2 -> {
                radio_button2.setBackgroundResource(R.drawable.correct_ans_bg)
            }
            3 -> {
                radio_button3.setBackgroundResource(R.drawable.correct_ans_bg)
            }
            4 -> {
                radio_button4.setBackgroundResource(R.drawable.correct_ans_bg)
            }
        }
        if (questionCounter < questionCount) {
            btnConfirm.text = "Next"
        } else {
            btnConfirm.text = "Finish"
        }
    }
}