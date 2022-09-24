package br.com.davidcastro.meurastreio.di

import br.com.davidcastro.meurastreio.data.api.RetrofitClient
import br.com.davidcastro.meurastreio.data.api.TrackingApi
import br.com.davidcastro.meurastreio.data.db.AppDatabase
import br.com.davidcastro.meurastreio.data.repository.TrackingDaoRepositoryImpl
import br.com.davidcastro.meurastreio.data.repository.TrackingRepositoryImpl
import br.com.davidcastro.meurastreio.data.repository.TrackingRepository
import br.com.davidcastro.meurastreio.viewModel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val module = module {

    single { AppDatabase.getDatabase(context = androidContext()).rastreioDao }

    single { TrackingDaoRepositoryImpl(trackingDao = get()) }

    single { RetrofitClient.getRetrofitInstance(TrackingApi::class.java,"https://proxyapp.correios.com.br/") }

    single<TrackingRepository> { TrackingRepositoryImpl(api = get())}

    viewModel {
        MainViewModel(
            repository = get()
        )
    }
}