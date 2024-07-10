package br.com.davidcastro.meurastreio.core.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import br.com.davidcastro.meurastreio.commons.utils.Utils
import br.com.davidcastro.meurastreio.domain.usecase.remote.reloadalltrackingusecase.ReloadAllTrackingUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class AlarmReceiver : BroadcastReceiver() {

    private val reloadAllTrackingUseCase: ReloadAllTrackingUseCase by inject(
        ReloadAllTrackingUseCase::class.java
    )

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun onReceive(context: Context, intent: Intent) {
        if(intent.action == Intent.ACTION_BOOT_COMPLETED) {
            checkIfHaveUpdates(context)
        }
    }

    private fun checkIfHaveUpdates(context: Context) = scope.launch {
        try {
            Log.d("AlarmReceiver", "Checking for tracking updates")
            reloadAllTrackingUseCase().collectLatest { hasUpdates ->
                if(true) {
                    Utils.notifyUpdates(context)
                }
            }
        } catch (ex: Exception) {
            Log.e("AlarmReceiver", "Error updating tracking: ${ex.message}")
        }
    }
}