package com.myapp.automaticsilentmode.Screens

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.myapp.automaticsilentmode.Utils.SetAlarms
import com.myapp.automaticsilentmode.Utils.listOfFilledAlarmNames
import com.myapp.automaticsilentmode.Utils.returnsFirstEmptyAlarmName
import com.myapp.automaticsilentmode.formating.formatTime
import com.myapp.automaticsilentmode.models.SavedAlarmsDataModel

@Composable
fun MyDialogue(
    context: Context,
    dialogState: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    savedAlarmData:SavedAlarmsDataModel,
    clickedItem:Int
) {

    val sharedPreferences = context.getSharedPreferences("allAlarms", MODE_PRIVATE)
    val editor = sharedPreferences.edit()



    val alarmName = if (savedAlarmData.alarmName.isNotEmpty()){
        val savedValue =sharedPreferences.getString(savedAlarmData.alarmName,"") ?: ""
        remember { mutableStateOf(savedValue) }
    }else{
        remember { mutableStateOf("") }
    }

    val alarmTime = if (savedAlarmData.timeName.isNotEmpty()){
        val savedValue =sharedPreferences.getString(savedAlarmData.timeName,"") ?: ""
        remember { mutableStateOf(savedValue) }
    }else{
        remember { mutableStateOf("") }
    }


    Dialog(onDismissRequest = {
        dialogState.value = true
    }) {


        Column(
            modifier = Modifier.background(Color.White)

        ) {
            TextField(
                value = alarmName.value,
                onValueChange = {
                    alarmName.value = it
                },
                label = { Text("Name") },
                modifier = modifier
                    .fillMaxWidth()

            )


            Text(
                text = "Time Format = hh/mm \n example 11/30 or 22/15",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                )

            TextField(
                value = alarmTime.value,
                onValueChange = {
                    alarmTime.value = it
                },
                label = { Text("Time") },
                modifier = Modifier.fillMaxWidth()
            )



            Spacer(Modifier.height(20.dp))
            Row {
                Button(
                    onClick = {
                        dialogState.value = false
                    },
                    Modifier.weight(1f)
                ) {
                    Text("Cancel")
                }


                Button(
                    onClick = {
                        if (alarmName.value.isNotEmpty() && alarmTime.value.isNotEmpty()) {

                            if (clickedItem!=0){
                                editor.putString(savedAlarmData.alarmName,alarmName.value)
                                editor.putString(savedAlarmData.timeName,alarmTime.value)
                                editor.apply()
                            }else{
                                editor.putString(
                                    returnsFirstEmptyAlarmName(context).name,
                                    alarmName.value
                                )
                                editor.putString(
                                    returnsFirstEmptyAlarmName(context).time,
                                    alarmTime.value
                                )
                                editor.apply()
                            }


                            val formattedTime = formatTime(alarmTime.value)

                            SetAlarms(context,formattedTime, listOfFilledAlarmNames(context).size+1)

                            dialogState.value = false
                        } else {
                            Toast.makeText(
                                context,
                                "Fill Both Fields First",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    Modifier.weight(1f)
                ) {
                    Text("OK")
                }
            }

        }
    }


}