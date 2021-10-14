package com.example.quizapp.Adapter

import com.example.quizapp.Model.QuizTopicModel

interface ClickListener {

    fun onStartQuiz(quizTopicModel: QuizTopicModel, position: Int)
}