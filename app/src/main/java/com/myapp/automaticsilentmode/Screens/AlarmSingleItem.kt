package com.myapp.automaticsilentmode.Screens

import android.app.Dialog
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.myapp.automaticsilentmode.models.AlarmItemsData
import com.myapp.automaticsilentmode.toast.makeToast


@Composable
fun AlarmSingleItem(modifier: Modifier = Modifier,alarms: AlarmItemsData,dialogState: MutableState<Boolean>,
                    index:Int,onclick:(Int)->Unit) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(Color(0xffd8efff))
            .clickable {
                onclick(index+1)
            }

    ) {

        Text(text = alarms.name,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 10.dp)

            )


        Text(text = alarms.time,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.End,
            fontSize = 30.sp,
            modifier = Modifier.padding(end = 10.dp)
                .weight(1f)
        )

    }
}

