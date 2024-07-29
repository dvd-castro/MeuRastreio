package br.com.davidcastro.meurastreio.core.application

import android.app.Application
import br.com.davidcastro.meurastreio.core.di.module
import br.com.davidcastro.meurastreio.core.worker.UpdateTrackingWorker
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin

class BaseApplication: Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            modules(module)
        }

        UpdateTrackingWorker.initWorker(this)
    }
}