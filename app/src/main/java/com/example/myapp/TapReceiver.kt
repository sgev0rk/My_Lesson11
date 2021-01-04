package com.example.myapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class TapReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i("TAG", "$this - user tap on button")
        context.apply {
            Toast.makeText(this, "$this - user tap on button", Toast.LENGTH_LONG).show()
        }
    }
}