package com.example.quizapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.quizapp.Model.AndroidQuestionModel
import com.example.quizapp.Repository.QuizRepository

class QuizViewModel(val quizRepository: QuizRepository) : ViewModel() {

    fun addQuestionData(androidQuestionModel: AndroidQuestionModel){
        quizRepository.addQuestionTORoom(androidQuestionModel)
    }

    fun getAndroidQuestions(): LiveData<List<AndroidQuestionModel>> {
        return quizRepository.getAllRoutines()
    }

    fun deleteAllQuestions(){
        quizRepository.deleteAllQuestions()
    }

}