package br.com.davidcastro.meurastreio

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch


class AlarmReceiver : BroadcastReceiver() {

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
//    private val repository: RastreioRepository by inject(RastreioRepository::class.java)

    override fun onReceive(context: Context, intent: Intent) {
        scope.launch {
//            checkIfHaveUpdates(context)
        }
    }

//    private suspend fun checkIfHaveUpdates(context: Context) {
//        if(NetworkUtils.hasConnectionActive(context)){
//            try {
//                val all = repository.getAllTracking()
//                if(all.isNotEmpty()) {
//                    all.forEachIndexed { index, rastreio ->
//                        if(rastreio.eventos.first().status != context.getString(R.string.status_entregue)) {
//                            val rastreioVerificado = repository.findTracking(rastreio.codigo)
//
//                            if(rastreioVerificado.eventos.count() > rastreio.eventos.count()) {
//                                val rastreioEntity = rastreioVerificado.toRastreioEntity()
//                                repository.updateTracking(rastreioEntity.codigo, rastreioEntity.eventos)
//                                notifyUpdates(context, rastreioVerificado, index)
//                            }
//                        }
//                    }
//                }
//
//            } catch (ex: Exception) {
//                ex.localizedMessage?.let { localizedMessage ->
//                    Log.e("ERROR -> get update trancking: ", localizedMessage)
//                }
//            }
//        }
//    }
//
//    private fun notifyUpdates(context: Context, rastreio: RastreioModel, index: Int) {
//
//        val intent = Intent(context, MainActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        }
//
//        val flag = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) PendingIntent.FLAG_IMMUTABLE else 0
//        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, flag)
//
//        val builder = NotificationCompat.Builder(context, context.getString(R.string.notification_channel_id) )
//            .setSmallIcon(R.drawable.ic_notification)
//            .setContentTitle(rastreio.eventos.first().status)
//            .setContentText(rastreio.codigo)
//            .setContentIntent(pendingIntent)
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setAutoCancel(true)
//
//        with(NotificationManagerCompat.from(context)) {
//            notify(index, builder.build())
//        }
//    }
}