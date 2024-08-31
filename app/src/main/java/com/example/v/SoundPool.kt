package com.example.v

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool


object SoundManager {
    private var soundPool: SoundPool? = null
    private var soundId: Int? = null

    fun initialize(context: Context) {
        soundPool = SoundPool.Builder()
            .setMaxStreams(1)
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build()
            )
            .build()

        soundId = soundPool?.load(context, R.raw.click, 1)  // Load your sound here
    }

    fun playSound() {
        soundId?.let { id ->
            soundPool?.play(id, 1f, 1f, 1, 0, 1f)
        }
    }

    fun release() {
        soundPool?.release()
        soundPool = null
    }
}