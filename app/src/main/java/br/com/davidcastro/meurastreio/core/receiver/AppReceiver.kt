package br.com.davidcastro.meurastreio.core.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import br.com.davidcastro.meurastreio.core.worker.UpdateTrackingWorker

class AppReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if(intent.action == Intent.ACTION_BOOT_COMPLETED) {
            UpdateTrackingWorker.initWorker(context)
        }
    }
}