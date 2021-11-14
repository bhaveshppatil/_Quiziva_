package com.example.quizapp.Repository

import androidx.lifecycle.LiveData
import com.example.quizapp.Database.QuestionDAO
import com.example.quizapp.Model.AndroidQuestionModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuizRepository(val questionDAO: QuestionDAO) {

    fun getAllRoutines(): LiveData<List<AndroidQuestionModel>> {
        return questionDAO.getAllQuestion()
    }

    fun addQuestionTORoom(androidQuestionModel: AndroidQuestionModel){
        CoroutineScope(Dispatchers.IO).launch {
            questionDAO.addQuestion(androidQuestionModel)
        }
    }

    fun deleteAllQuestions(){
        CoroutineScope(Dispatchers.IO).launch {
            questionDAO.deleteAllQuestion()
        }
    }
}