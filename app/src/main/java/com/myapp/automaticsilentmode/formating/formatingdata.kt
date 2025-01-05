package com.myapp.automaticsilentmode.formating

import androidx.core.text.isDigitsOnly
import com.myapp.automaticsilentmode.models.TimeFormat


fun formatTime(time: String): TimeFormat {

    val formattedTime = TimeFormat(
        hour = 0,
        minutes = 0
    )

    val ss = time.split("/")

        formattedTime.hour = ss.first().toInt()
        formattedTime.minutes = ss.last().toInt()


    return formattedTime
}