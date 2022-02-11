package br.com.davidcastro.meurastreio.helpers.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import br.com.davidcastro.meurastreio.R
import br.com.davidcastro.meurastreio.data.repository.RastreioRepository
import kotlinx.coroutines.*
import org.koin.java.KoinJavaComponent.inject

class AlarmReceiver : BroadcastReceiver() {

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val repository: RastreioRepository by inject(RastreioRepository::class.java)

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            scope.launch {
                checkIfHaveUpdates(context)
            }
        }
        scope.launch {
            checkIfHaveUpdates(context)
        }
    }

    private suspend fun checkIfHaveUpdates(context: Context) {

        try {
            val all = repository.getAllTracking()
            if(all.isNotEmpty()){
                all.forEach { rastreio ->
                    if(rastreio.eventos.first().status != context.getString(R.string.status_entregue)) {
                        val rastreioVerificado = repository.findTracking(rastreio.codigo)
                        if(rastreio.eventos.first() != rastreioVerificado.eventos.first()) {
                            //TODO Notificar
                        }
                    }
                }
            }

        } catch (ex: Exception) {
            ex.localizedMessage?.let { localizedMessage ->
                Log.e("ERROR -> Verify If Tracking Exists", localizedMessage)
            }
        }
    }
}