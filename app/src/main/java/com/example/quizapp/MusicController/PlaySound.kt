package com.example.quizapp.MusicController

import android.content.Context
import android.media.MediaPlayer
import android.media.MediaPlayer.OnPreparedListener
import android.media.MediaPlayer.OnCompletionListener
import com.example.quizapp.R

class PlaySound(private val mContext: Context) {

    private lateinit var mediaPlayer: MediaPlayer

    fun seAudioforAnswers(flag: Int) {
        when (flag) {
            1 -> {
                val correctAudio: Int = R.raw.correct
                playMusic(correctAudio)
            }
            2 -> {
                val wrongAudio: Int = R.raw.wrong
                playMusic(wrongAudio)
            }
            3 -> {
                val timerAudio: Int = R.raw.times_up_sound
                playMusic(timerAudio)
            }
        }
    }

    private fun playMusic(audiofile: Int) {
        mediaPlayer = MediaPlayer.create(mContext, audiofile)
        mediaPlayer.setOnPreparedListener(OnPreparedListener { mediaPlayer -> mediaPlayer.start() })
        mediaPlayer.setOnCompletionListener(OnCompletionListener { mediaPlayer -> mediaPlayer.release() })
    }
}