package com.example.v.service

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import com.example.v.R


object SoundManager {
    private var soundPool: SoundPool? = null
    private var soundId: Int? = null
    private var wrongId: Int? = null
    private var correctId: Int? = null
    private var wordUsed: Int? = null
    private var win: Int? = null
    private var fail: Int? = null
    private var liod: Int? = null

    fun initialize(context: Context) {
        soundPool = SoundPool.Builder()
            .setMaxStreams(1)
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
            .build()

        soundId = soundPool?.load(context, R.raw.click, 1)  // Load your sound here
        correctId = soundPool?.load(context, R.raw.correct, 1)
        wrongId = soundPool?.load(context, R.raw.wrong, 1)
        wordUsed = soundPool?.load(context, R.raw.word_used, 1)
        win = soundPool?.load(context, R.raw.win, 1)
        fail = soundPool?.load(context, R.raw.fail, 1)
        liod = soundPool?.load(context, R.raw.liod, 2)
    }

    fun clickSound() {
        soundId?.let { id ->
            soundPool?.play(id, 1f, 1f, 1, 0, 1f)
        }
    }

    fun correctSound() {
        correctId?.let { id ->
            soundPool?.play(id, 1f, 1f, 1, 0, 1f)
        }
    }

    fun wrongSound() {
        wrongId?.let { id ->
            soundPool?.play(id, 1f, 1f, 1, 0, 1f)
        }
    }

    fun usedWord() {
        wordUsed?.let {
            id -> soundPool?.play(id, 1f, 1f, 1, 0, 1f)
        }
    }

    fun win() {
        win?.let {
            id -> soundPool?.play(id, 1f, 1f, 1, 0, 1f)
        }
    }

    fun fail() {
        fail?.let {
            id -> soundPool?.play(id, 1f, 1f, 1, 0, 1f)
        }
    }

    fun liod() {
        liod?.let {
            id -> soundPool?.play(id, 1f, 1f, 2, 0, 1f)
        }
    }

    fun release() {
        soundPool?.release()
        soundPool = null
    }


}