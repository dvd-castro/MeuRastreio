package br.com.davidcastro.meurastreio.helpers.utils

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import br.com.davidcastro.meurastreio.R
import br.com.davidcastro.meurastreio.data.model.RastreioModel
import br.com.davidcastro.meurastreio.data.repository.RastreioRepository
import br.com.davidcastro.meurastreio.view.activities.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject


class AlarmReceiver : BroadcastReceiver() {

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val repository: RastreioRepository by inject(RastreioRepository::class.java)

    override fun onReceive(context: Context, intent: Intent) {
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
                            repository.insertTracking(rastreioVerificado)
                            notifyUpdates(context, rastreioVerificado)
                        }
                    }
                }
            }

        } catch (ex: Exception) {
            ex.localizedMessage?.let { localizedMessage ->
                Log.e("ERROR -> get update trancking: ", localizedMessage)
            }
        }
    }

    private fun notifyUpdates(context: Context, rastreio: RastreioModel) {

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val builder = NotificationCompat.Builder(context, context.getString(R.string.notification_channel_id) )
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(rastreio.codigo)
            .setContentText(rastreio.eventos.first().status)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        with(NotificationManagerCompat.from(context)) {
            notify(0, builder.build())
        }
    }
}