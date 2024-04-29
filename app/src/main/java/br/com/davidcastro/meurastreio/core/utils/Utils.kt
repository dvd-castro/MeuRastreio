package br.com.davidcastro.meurastreio.core.utils

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.text.Spanned
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.text.HtmlCompat
import br.com.davidcastro.meurastreio.R
import br.com.davidcastro.meurastreio.core.activity.MainActivity

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

        if (Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.TIRAMISU &&
            ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) !=
            PackageManager.PERMISSION_GRANTED) {
            return
        }

        NotificationManagerCompat.from(context).notify(0, builder.build())
    }

    fun getTrackingStatusColor(context: Context, status: String): Int =
        when(status) {
            context.getString(R.string.status_encaminhado) -> {
                context.getColor(R.color.blue)
            }
            context.getString(R.string.status_entregue) -> {
                context.getColor(R.color.green)
            }
            else -> {
                context.getColor(R.color.orange)
            }
        }

    fun getHtmlString(text: String): Spanned =
        HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
}