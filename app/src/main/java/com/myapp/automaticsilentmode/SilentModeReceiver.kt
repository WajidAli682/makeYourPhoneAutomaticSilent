package com.myapp.automaticsilentmode

import android.app.AlarmManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.AUDIO_SERVICE
import android.content.Intent
import android.media.AudioManager
import android.widget.Toast


class SilentModeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {


        val notification = context?.getSystemService(AUDIO_SERVICE) as AudioManager

        Toast.makeText(context,"DoneFromBroadCast",Toast.LENGTH_SHORT).show()


        if (notification.ringerMode == AudioManager.RINGER_MODE_NORMAL){
            notification.ringerMode = AudioManager.RINGER_MODE_SILENT
        }else{
            notification.ringerMode = AudioManager.RINGER_MODE_NORMAL
        }

    }

}
