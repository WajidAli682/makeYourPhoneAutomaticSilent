package com.myapp.automaticsilentmode.Screens

import android.app.Dialog
import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myapp.automaticsilentmode.models.AlarmItemsData


@Composable
fun ListOfAlarms(listOfAlarms: List<AlarmItemsData>,dialogstate: MutableState<Boolean>,onclick:(Int)->Unit) {


    if (listOfAlarms.isNotEmpty()){

        LazyColumn(
            contentPadding = PaddingValues(10.dp),
            modifier = Modifier.fillMaxSize()

        ) {

            itemsIndexed(items = listOfAlarms) {index,it->
                AlarmSingleItem(
                    Modifier.padding(bottom = 10.dp),
                    alarms = it,
                    dialogState = dialogstate,
                    index = index,
                    onclick = onclick
                )

            }
        }
    }else{

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()

        ) {
            Text(text = "NO Alarms Found ")
        }
    }



}

