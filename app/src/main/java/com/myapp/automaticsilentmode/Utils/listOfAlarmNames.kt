package com.myapp.automaticsilentmode.Utils

import android.annotation.SuppressLint
import android.app.Activity.MODE_PRIVATE
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.myapp.automaticsilentmode.SilentModeReceiver
import com.myapp.automaticsilentmode.foreGroundService
import com.myapp.automaticsilentmode.models.AlarmItemsData
import com.myapp.automaticsilentmode.models.SavedAlarmsDataModel
import com.myapp.automaticsilentmode.models.TimeFormat
import com.myapp.automaticsilentmode.toast.makeToast
import java.util.Calendar


fun listOfAlarmNames(): List<AlarmItemsData> {

    // only 10 alarms can be visible
    //you can increase the number if yo want more
    val listOfAlarms = mutableListOf<AlarmItemsData>()
    listOfAlarms.add(AlarmItemsData("alarm1Name", "alarm1Time"))
    listOfAlarms.add(AlarmItemsData("alarm2Name", "alarm2Time"))
    listOfAlarms.add(AlarmItemsData("alarm3Name", "alarm3Time"))
    listOfAlarms.add(AlarmItemsData("alarm4Name", "alarm4Time"))
    listOfAlarms.add(AlarmItemsData("alarm5Name", "alarm5Time"))
    listOfAlarms.add(AlarmItemsData("alarm6Name", "alarm6Time"))
    listOfAlarms.add(AlarmItemsData("alarm7Name", "alarm7Time"))
    listOfAlarms.add(AlarmItemsData("alarm8Name", "alarm8Time"))
    listOfAlarms.add(AlarmItemsData("alarm9Name", "alarm9Time"))
    listOfAlarms.add(AlarmItemsData("alarm10Name", "alarm10Time"))

    return listOfAlarms
}

fun listOfFilledAlarmNames(context: Context): List<AlarmItemsData> {

    val sharedPreferences = context.getSharedPreferences("allAlarms", MODE_PRIVATE)
    val filledAlarmNames = mutableListOf<AlarmItemsData>()

    for (i in listOfAlarmNames()) {
        val filledName = sharedPreferences.getString(i.name, "")
        val filledTime = sharedPreferences.getString(i.time, "")
        if (filledName!!.isNotEmpty()) {
            filledTime?.let {
                AlarmItemsData(
                    name = filledName,
                    time = it
                )
            }?.let { filledAlarmNames.add(it) }
        }
    }
    return filledAlarmNames
}


fun returnsFirstEmptyAlarmName(context: Context): AlarmItemsData {

    val firstEmptyName = listOfFilledAlarmNames(context).size
    val firstEmptyNameCorrectFormat = "alarm${firstEmptyName + 1}Name"
    val firstEmptyAlarmTime = "alarm${firstEmptyName + 1}Time"
    val firstEmptyAlarm = AlarmItemsData(
        name = firstEmptyNameCorrectFormat,
        time = firstEmptyAlarmTime
    )

    return firstEmptyAlarm
}




@SuppressLint("ScheduleExactAlarm")

fun SetAlarms(context: Context, formattedTime: TimeFormat,versioncode:Int) {
    makeToast(context,versioncode.toString())

    val calender = Calendar.getInstance()
    calender.set(Calendar.HOUR_OF_DAY, formattedTime.hour)
    calender.set(Calendar.MINUTE, formattedTime.minutes)
    calender.set(Calendar.SECOND, 0)
    calender.set(Calendar.MILLISECOND, 0)

    if (calender.before(Calendar.getInstance())) {
        // Move to the next day
        calender.add(Calendar.DAY_OF_MONTH, 1)
    }
    
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    val intent = Intent(context, SilentModeReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context.applicationContext, versioncode, intent,
        PendingIntent.FLAG_IMMUTABLE
    )


    alarmManager.setRepeating(
        AlarmManager.RTC_WAKEUP,
        calender.timeInMillis,
        AlarmManager.INTERVAL_DAY,
        pendingIntent
    )

    startForeGroundService(context)

}

fun startForeGroundService(context: Context) {

    val inetnt = Intent(context, foreGroundService::class.java)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        context.startForegroundService(inetnt)
    } else {
        context.startService(inetnt)
    }
}


fun getSavedAlarmData(key: Int): SavedAlarmsDataModel {
    return when (key) {
        1 -> {
            SavedAlarmsDataModel("alarm1Name", "alarm1Time")
        }

        2 -> {
            SavedAlarmsDataModel("alarm2Name", "alarm2Time")
        }

        3 -> {
            SavedAlarmsDataModel("alarm3Name", "alarm3Time")
        }

        4 -> {
            SavedAlarmsDataModel("alarm4Name", "alarm4Time")
        }

        5 -> {
            SavedAlarmsDataModel("alarm5Name", "alarm5Time")
        }

        6 -> {
            SavedAlarmsDataModel("alarm6Name", "alarm6Time")
        }

        7 -> {
            SavedAlarmsDataModel("alarm7Name", "alarm7Time")
        }

        8 -> {
            SavedAlarmsDataModel("alarm8Name", "alarm8Time")
        }

        9 -> {
            SavedAlarmsDataModel("alarm9Name", "alarm9Time")
        }

        10 -> {
            SavedAlarmsDataModel("alarm10Name", "alarm10Time")
        }

        else -> {
            SavedAlarmsDataModel("alarm1Name", "alarm1Time")
        }
    }
}