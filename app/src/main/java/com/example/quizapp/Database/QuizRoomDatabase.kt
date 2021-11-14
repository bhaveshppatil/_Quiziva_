package com.example.quizapp.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.quizapp.Model.AndroidQuestionModel

@Database(entities = [AndroidQuestionModel::class], version = 1)
abstract class QuizRoomDatabase : RoomDatabase() {
    abstract fun getQuestionDAO(): QuestionDAO

    companion object {
        private var INSTANCE: QuizRoomDatabase? = null

        fun getQuestionObject(context: Context): QuizRoomDatabase {
            if (INSTANCE != null) {
                return INSTANCE!!
            } else {
                val builder = Room.databaseBuilder(
                    context.applicationContext, QuizRoomDatabase::class.java, "Quiz_Database"
                )
                builder.fallbackToDestructiveMigration()
                INSTANCE = builder.build()
            }
            return INSTANCE!!
        }
    }
}