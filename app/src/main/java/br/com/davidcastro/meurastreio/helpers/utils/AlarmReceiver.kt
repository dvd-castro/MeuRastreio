package br.com.davidcastro.meurastreio.helpers.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            Log.d("####","iniciou")
        }
        Log.d("####","iniciou 2")
        Toast.makeText(context,"DSadasdsadsa",Toast.LENGTH_LONG).show()
    }
}