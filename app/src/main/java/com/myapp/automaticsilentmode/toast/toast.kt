package com.myapp.automaticsilentmode.toast

import android.content.Context
import android.widget.Toast
import androidx.compose.material3.TopAppBar

fun makeToast(context: Context,message:String){
    Toast.makeText(context,message, Toast.LENGTH_SHORT).show()
}
