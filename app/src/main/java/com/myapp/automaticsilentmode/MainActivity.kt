package com.myapp.automaticsilentmode

import android.annotation.SuppressLint
import android.app.Activity
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.myapp.automaticsilentmode.Screens.ListOfAlarms
import com.myapp.automaticsilentmode.Screens.MyDialogue
import com.myapp.automaticsilentmode.Utils.getSavedAlarmData
import com.myapp.automaticsilentmode.Utils.listOfFilledAlarmNames
import com.myapp.automaticsilentmode.models.SavedAlarmsDataModel
import com.myapp.automaticsilentmode.toast.makeToast
import com.myapp.automaticsilentmode.ui.theme.AutomaticSilentModeTheme


class MainActivity : ComponentActivity() {

    private var dndPermission = mutableStateOf(false)

    private var clickedItem: Int = 0

    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        val isGranted = checkPermissions()
        if (isGranted) makeToast(
            this@MainActivity,
            "Permission Granted Successfully"
        ) else makeToast(this@MainActivity, "permission not Granted")
        dndPermission.value = isGranted
    }



    @SuppressLint("ScheduleExactAlarm")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        System.currentTimeMillis()
        setContent {
            AutomaticSilentModeTheme {

                dndPermission.value = checkPermissions()

                val dialogState = remember { mutableStateOf(false) }

                val savedAlarmName = remember { mutableStateOf("") }


                val savedAlarmTime = remember { mutableStateOf("") }


                if (dialogState.value) {
                    MyDialogue(
                        context = this@MainActivity,
                        dialogState = dialogState,
                        savedAlarmData = SavedAlarmsDataModel(
                            alarmName = savedAlarmName.value,
                            timeName = savedAlarmTime.value
                        ),
                        clickedItem = clickedItem

                    )
                }

                AllUiSetup(
                    dialogState,
                    clickedItemSavedName = savedAlarmName,
                    clickedItemSavedTime = savedAlarmTime
                )


            }
        }
    }


    @Composable
    private fun AllUiSetup(
        dialogueState: MutableState<Boolean>,
        clickedItemSavedName: MutableState<String>,
        clickedItemSavedTime: MutableState<String>
    ) {
        Scaffold(
            floatingActionButton = {

                Box(
                    modifier = Modifier
                        .padding(bottom = 50.dp, end = 30.dp)
                        .clip(RoundedCornerShape(10.dp))

                ) {
                    IconButton(
                        onClick = {
                            clickedItemSavedName.value = ""
                            clickedItemSavedTime.value = ""
                            clickedItem = 0
                            dialogueState.value = true
                        },
                        modifier = Modifier
                            .background(Color.Green)
                            .clip(RoundedCornerShape(10.dp))
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "add alarms")
                    }
                }

            }
        ) { innerPadding ->

            if (dndPermission.value) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    ListOfAlarms(
                        listOfAlarms = listOfFilledAlarmNames(
                            context = this@MainActivity,
                        ),
                        dialogstate = dialogueState,
                        onclick = {
                            testing(
                                jaga = it,
                                clickedItemSavedName = clickedItemSavedName,
                                clickedItemSavedTime = clickedItemSavedTime,
                                dialogueState = dialogueState
                            )
                        }
                    )
                }
            } else {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)

                ) {
                    Text("Permissions Are Not Granted")

                    Spacer(Modifier.height(20.dp))

                    Button(onClick ={ requestBatteryOptimizationPermission(this@MainActivity) }) {
                        Text("battery Permission")
                    }

                    Button(
                        onClick = {
                            getPermissions()
                        }
                    ) {
                        Text(text = "Get Permissions ")
                    }



                }
            }


        }
    }


    private fun checkPermissions(): Boolean {
        val notificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        return notificationManager.isNotificationPolicyAccessGranted
    }

    private fun getPermissions() {
        val intent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
        notificationPermissionLauncher.launch(intent)
    }


    fun testing(
        jaga: Int,
        clickedItemSavedName: MutableState<String>,
        clickedItemSavedTime: MutableState<String>,
        dialogueState: MutableState<Boolean>
    ) {
        clickedItem = jaga
        val clickedSavedItemData = getSavedAlarmData(key = jaga)
        clickedItemSavedName.value = clickedSavedItemData.alarmName
        clickedItemSavedTime.value = clickedSavedItemData.timeName
        dialogueState.value = true

    }



}

fun requestBatteryOptimizationPermission(context: Context) {
    val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
    intent.data = Uri.parse("package:${context.packageName}")
    context.startActivity(intent)
}



