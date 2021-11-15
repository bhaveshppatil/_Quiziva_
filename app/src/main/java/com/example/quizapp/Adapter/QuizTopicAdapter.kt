package com.example.quizapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.Model.QuizTopicModel
import com.example.quizapp.R

class QuizTopicAdapter(
    val context: Context,
    val quizTopicList: MutableList<QuizTopicModel>,
    val clickListener: ClickListener
) : RecyclerView.Adapter<QuizTopicViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizTopicViewHolder {
        val inflater = LayoutInflater.from(context)
        val view1: View = inflater.inflate(R.layout.quiz_topic_layout, parent, false)
        return QuizTopicViewHolder(view1)
    }

    override fun onBindViewHolder(holder: QuizTopicViewHolder, position: Int) {
        val quizTopicModel = quizTopicList[position]

        holder.title.text = quizTopicModel.topicName
        holder.ivIcon.setImageResource(quizTopicModel.iconID)

        holder.ivStart.setOnClickListener {
            clickListener.onStartQuiz(quizTopicModel, position)
        }
    }

    override fun getItemCount(): Int {
        return quizTopicList.size
    }
}

class QuizTopicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val title: TextView = itemView.findViewById(R.id.tvTopicName)
    val ivIcon: ImageView = itemView.findViewById(R.id.ivTopicIcon)
    val ivStart: ImageView = itemView.findViewById(R.id.ivStart)

}