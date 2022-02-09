package br.com.davidcastro.meurastreio

import android.app.Application
import android.content.ComponentName
import android.content.pm.PackageManager
import br.com.davidcastro.meurastreio.data.di.modules.module
import br.com.davidcastro.meurastreio.helpers.utils.AlarmReceiver
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
    }

    private fun setReceiverSettings(){
        val receiver = ComponentName(this, AlarmReceiver::class.java)

        packageManager.setComponentEnabledSetting(
            receiver,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
    }
}