package br.com.davidcastro.meurastreio.di

import br.com.davidcastro.meurastreio.data.api.RetrofitClient
import br.com.davidcastro.meurastreio.data.api.TrackingApi
import br.com.davidcastro.meurastreio.data.dataSources.DatabaseDataSource
import br.com.davidcastro.meurastreio.data.repository.TrackingRepositoryImpl
import br.com.davidcastro.meurastreio.data.repository.TrackingRespository
import br.com.davidcastro.meurastreio.viewModel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val module = module {

    single { DatabaseDataSource(context = androidContext()) }

    single { RetrofitClient.getRetrofitInstance(TrackingApi::class.java,"https://proxyapp.correios.com.br/") }

    single<TrackingRespository> { TrackingRepositoryImpl(api = get())}

    viewModel {
        MainViewModel(
            repository = get()
        )
    }
}