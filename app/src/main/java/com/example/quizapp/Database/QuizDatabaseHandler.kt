package com.example.quizapp.Database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.quizapp.Model.AndroidQuestionModel

class QuizDatabaseHandler(val context: Context) :
    SQLiteOpenHelper(context, "quiz_database", null, 1) {

    companion object {
        val TABLE_NAME = "QuizTable"
        val ID = "id"
        val QUESTION = "question"
        val OPTION1 = "option1"
        val OPTION2 = "option2"
        val OPTION3 = "option3"
        val OPTION4 = "option4"
        val ANSWER = "answer"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query =
            "CREATE TABLE $TABLE_NAME($ID INTEGER PRIMARY KEY," +
                    "$QUESTION TEXT, " +
                    "$OPTION1 TEXT, " +
                    "$OPTION2 TEXT, " +
                    "$OPTION3 TEXT, " +
                    "$OPTION4 TEXT, " +
                    "$ANSWER INTEGER)"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun getQuestionData(): MutableList<AndroidQuestionModel> {

        val questionModelList = mutableListOf<AndroidQuestionModel>()
        val db = readableDatabase
        val query = "select * from $TABLE_NAME"

        val cursor = db?.rawQuery(query, null)

        if (cursor != null && cursor.count > 0) {
            cursor.moveToFirst()
            do {
                val id = cursor.getInt(cursor.getColumnIndex(ID))
                val question = cursor.getString(cursor.getColumnIndex(QUESTION))
                val option1 = cursor.getString(cursor.getColumnIndex(OPTION1))
                val option2 = cursor.getString(cursor.getColumnIndex(OPTION2))
                val option3 = cursor.getString(cursor.getColumnIndex(OPTION3))
                val option4 = cursor.getString(cursor.getColumnIndex(OPTION4))
                val answer = cursor.getInt(cursor.getColumnIndex(ANSWER))

                val questionModel =
                    AndroidQuestionModel(question, option1, option2, option3, option4, answer)
                questionModelList.add(questionModel)

            } while (cursor.moveToNext())
        }
        return questionModelList
    }

    fun addQuestionToDB(androidQuestionModel: AndroidQuestionModel) {
        val db = writableDatabase
        val contentValues = ContentValues()

        contentValues.put(QUESTION, androidQuestionModel.question)
        contentValues.put(OPTION1, androidQuestionModel.option1)
        contentValues.put(OPTION2, androidQuestionModel.option2)
        contentValues.put(OPTION3, androidQuestionModel.option3)
        contentValues.put(OPTION4, androidQuestionModel.option4)
        contentValues.put(ANSWER, androidQuestionModel.answer)

        val id = db.insert(TABLE_NAME, null, contentValues)

        if (id.toInt() == -1) {
            Toast.makeText(context, "Failed to insert data", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Data Inserted Successfully", Toast.LENGTH_SHORT).show()
        }
    }
}