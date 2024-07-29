package br.com.davidcastro.meurastreio.core.worker

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ForegroundInfo
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import br.com.davidcastro.meurastreio.R
import br.com.davidcastro.meurastreio.commons.utils.Utils
import br.com.davidcastro.meurastreio.core.activity.MainActivity
import br.com.davidcastro.meurastreio.domain.usecase.remote.reloadalltrackingusecase.ReloadAllTrackingUseCase
import kotlinx.coroutines.flow.collectLatest
import org.koin.java.KoinJavaComponent.inject
import java.util.concurrent.TimeUnit

private const val TAG = "UpdateTrackingWorker"

class UpdateTrackingWorker(
    private val appContext: Context,
    workerParams: WorkerParameters
): CoroutineWorker(appContext, workerParams) {
    private val reloadAllTrackingUseCase: ReloadAllTrackingUseCase by inject(
        ReloadAllTrackingUseCase::class.java
    )

    override suspend fun getForegroundInfo(): ForegroundInfo {
        return ForegroundInfo(
            40028922, createNotification()
        )
    }

    override suspend fun doWork(): Result {
        return try {
            Log.d("Worker", "Checking for tracking updates")
            reloadAllTrackingUseCase().collectLatest { hasUpdates ->
                if(hasUpdates) {
                    Utils.notifyUpdates(appContext)
                }
            }
            Result.success()
        } catch (ex: Exception) {
            Log.e("Worker", "Error updating tracking: ${ex.message}")
            Result.failure()
        }
    }

    private fun createNotification() : Notification {

        val intent = Intent(appContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val flag = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.FLAG_IMMUTABLE
        } else {
            0
        }


        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(appContext, 0, intent, flag)

        val builder = NotificationCompat.Builder(
            appContext,
            appContext.getString(R.string.notification_channel_id)
        )
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(appContext.getText(R.string.message_notification_update_title))
            .setContentText(appContext.getText(R.string.message_notification_update_text))
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        return builder.build()
    }

    companion object {
        fun initWorker(context: Context) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(true)
                .build()

            val updateRequest = PeriodicWorkRequestBuilder<UpdateTrackingWorker>(
                repeatInterval = 15,
                repeatIntervalTimeUnit = TimeUnit.MINUTES
            )
                .setConstraints(constraints)
                .build()

            WorkManager
                .getInstance(context)
                .enqueueUniquePeriodicWork(
                    TAG,
                    ExistingPeriodicWorkPolicy.UPDATE,
                    updateRequest
                )
        }
    }
}