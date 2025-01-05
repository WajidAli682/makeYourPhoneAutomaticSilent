package com.myapp.automaticsilentmode

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat


class foreGroundService : Service() {

    private val notificationId = "Foreground_Notification"

    private lateinit var notification: Notification

    override fun onCreate() {
        super.onCreate()

        createNotificationChannel()
        notification = createNotification()
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {


        startForeground(1, notification)
        return START_STICKY
    }

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(
            this@foreGroundService,
            notificationId
        )
            .setContentTitle("AutomaticSilentApp")
            .setContentText("Service is Running...")
            .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                notificationId,
                "Automatic_Silent_App",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)

        }

    }


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

}