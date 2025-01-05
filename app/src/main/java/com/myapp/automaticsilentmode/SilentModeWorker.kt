package com.myapp.automaticsilentmode

import android.content.Context
import android.media.AudioManager
import androidx.work.Worker
import androidx.work.WorkerParameters

class SilentModeWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        val audioManager = applicationContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        if (audioManager.ringerMode == AudioManager.RINGER_MODE_NORMAL) {
            audioManager.ringerMode = AudioManager.RINGER_MODE_SILENT
        } else {
            audioManager.ringerMode = AudioManager.RINGER_MODE_NORMAL
        }

        return Result.success()
    }
}
