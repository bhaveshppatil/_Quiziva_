package com.example.quizapp.MusicController

import android.app.Dialog
import android.content.Context
import android.widget.Button
import android.view.View
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.graphics.Color
import com.example.quizapp.MainActivity
import com.example.quizapp.R

class TimerDialog(private val mContext: Context) {
    private lateinit var TimerDialog: Dialog

    fun timerDialog() {
        TimerDialog = Dialog(mContext)
        TimerDialog.setContentView(R.layout.timer_dialog)
        val btTimer = TimerDialog.findViewById<View>(R.id.bt_timer) as Button

        btTimer.setOnClickListener {
            TimerDialog.dismiss()
            val intent = Intent(mContext, MainActivity::class.java)
            mContext.startActivity(intent)
        }
        TimerDialog.show()
        TimerDialog.setCancelable(false)
        TimerDialog.setCanceledOnTouchOutside(false)
        TimerDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}