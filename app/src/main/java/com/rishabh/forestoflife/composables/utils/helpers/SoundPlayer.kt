package com.rishabh.forestoflife.composables.utils.helpers

import android.content.Context
import android.media.MediaPlayer

interface SoundPlayerListener {
    fun onCompletion()
}

class SoundPlayer {

    private var mediaPlayer: MediaPlayer? = null
    private var soundPlayerListener: SoundPlayerListener? = null

    fun setListener(listener: SoundPlayerListener) {
        soundPlayerListener = listener
    }

    fun playSound(context: Context, fileName: String?) {
        try {
            val descriptor = context.assets.openFd(fileName!!)
            mediaPlayer = MediaPlayer()
            mediaPlayer?.setDataSource(
                descriptor.fileDescriptor,
                descriptor.startOffset,
                descriptor.length
            )
            descriptor.close()

            mediaPlayer?.setOnCompletionListener {
                soundPlayerListener?.onCompletion()
            }

            mediaPlayer?.prepare()
            mediaPlayer?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun stopSound() {
        mediaPlayer?.apply {
            if (isPlaying) {
                stop()
            }
            release()
        }
        mediaPlayer = null
    }
}
