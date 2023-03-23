package br.com.davidcastro.meurastreio.utils

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import br.com.davidcastro.meurastreio.R
import br.com.davidcastro.meurastreio.view.activities.MainActivity

object Utils {

    fun notifyUpdates(context: Context) {

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val flag = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) PendingIntent.FLAG_IMMUTABLE else 0
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, flag)

        val builder = NotificationCompat.Builder(context, context.getString(R.string.notification_channel_id) )
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(context.getText(R.string.message_notification_title))
            .setContentText(context.getText(R.string.message_notification_text))
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        NotificationManagerCompat.from(context).notify(0, builder.build())
    }
}