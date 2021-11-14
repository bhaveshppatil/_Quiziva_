package com.example.quizapp.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.quizapp.Repository.QuizRepository

class QuizViewModelFactory(private val quizRepository: QuizRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return QuizViewModel(quizRepository) as T
    }

}