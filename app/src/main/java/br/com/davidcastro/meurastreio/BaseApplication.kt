package br.com.davidcastro.meurastreio

import android.app.Application
import br.com.davidcastro.meurastreio.data.di.modules.module
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BaseApplication)
            modules(module)
        }
    }
}