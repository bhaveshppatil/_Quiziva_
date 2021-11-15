package com.example.quizapp.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "security_table")
data class SecurityQuestionModel(
    @ColumnInfo(name = "question") var question: String,
    @ColumnInfo(name = "option1") var option1: String,
    @ColumnInfo(name = "option2") var option2: String,
    @ColumnInfo(name = "option3") var option3: String,
    @ColumnInfo(name = "option4") var option4: String,
    @ColumnInfo(name = "answer") var answer: Int
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int? = null

}