package br.com.davidcastro.meurastreio

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import br.com.davidcastro.meurastreio.data.model.RastreioModel
import br.com.davidcastro.meurastreio.data.repository.RastreioRepository
import br.com.davidcastro.meurastreio.helpers.extensions.toRastreioEntity
import br.com.davidcastro.meurastreio.helpers.utils.NetworkUtils
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
        if(NetworkUtils.hasConnectionActive(context)){
            try {
                val all = repository.getAllTracking()
                if(all.isNotEmpty()) {
                    all.forEachIndexed { index, rastreio ->
                        if(rastreio.eventos.first().status != context.getString(R.string.status_entregue)) {
                            val rastreioVerificado = repository.findTracking(rastreio.codigo)

                            if(rastreio.eventos.first() != rastreioVerificado.eventos.first()) {
                                val rastreioEntity = rastreioVerificado.toRastreioEntity()
                                repository.updateTracking(rastreioEntity.codigo, rastreioEntity.eventos)
                                notifyUpdates(context, rastreioVerificado, index)
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
    }

    private fun notifyUpdates(context: Context, rastreio: RastreioModel, index: Int) {

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val flag = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) PendingIntent.FLAG_IMMUTABLE else 0
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, flag)

        val builder = NotificationCompat.Builder(context, context.getString(R.string.notification_channel_id) )
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(rastreio.nome)
            .setContentText(rastreio.eventos.first().status)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            notify(index, builder.build())
        }
    }
}