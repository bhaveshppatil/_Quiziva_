package com.example.quizapp.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.quizapp.Model.AndroidQuestionModel


@Dao
interface QuestionDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addQuestion(androidQuestionModel: AndroidQuestionModel)

    @Query("select * from question_table")
    fun getAllQuestion(): LiveData<List<AndroidQuestionModel>>

    @Query("delete from question_table")
    fun deleteAllQuestion()
}