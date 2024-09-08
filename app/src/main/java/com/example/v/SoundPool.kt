package com.example.v

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool


object SoundManager {
    private var soundPool: SoundPool? = null
    private var soundId: Int? = null
    private var wrongId: Int? = null
    private var correctId: Int? = null

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

    fun release() {
        soundPool?.release()
        soundPool = null
    }
}