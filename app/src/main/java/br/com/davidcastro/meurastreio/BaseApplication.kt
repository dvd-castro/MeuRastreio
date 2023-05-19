package br.com.davidcastro.meurastreio

import android.app.*
import android.app.AlarmManager.INTERVAL_FIFTEEN_MINUTES
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.SystemClock
import br.com.davidcastro.meurastreio.di.module
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BaseApplication)
            modules(module)
        }

        setReceiverSettings()
        initAlarmManager()
    }

    private fun setReceiverSettings() {
        val receiver = ComponentName(this, AlarmReceiver::class.java)

        packageManager.setComponentEnabledSetting(
            receiver,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
    }

    private fun initAlarmManager() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        val flag = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) PendingIntent.FLAG_IMMUTABLE else 0
        val alarmIntent = Intent(this, AlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(this, 0, intent, flag)
        }

        alarmManager?.setRepeating(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime(),
            INTERVAL_FIFTEEN_MINUTES,
            alarmIntent
        )
    }
}