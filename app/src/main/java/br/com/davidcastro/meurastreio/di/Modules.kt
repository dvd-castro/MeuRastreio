package br.com.davidcastro.meurastreio.di

import br.com.davidcastro.data.api.RetrofitClient
import br.com.davidcastro.data.api.TrackingApi
import br.com.davidcastro.data.db.AppDatabase
import br.com.davidcastro.data.db.dao.TrackingDao
import br.com.davidcastro.data.repository.TrackingDaoRepository
import br.com.davidcastro.data.repository.TrackingDaoRepositoryImpl
import br.com.davidcastro.data.repository.TrackingRepository
import br.com.davidcastro.data.repository.TrackingRepositoryImpl
import br.com.davidcastro.data.usecase.*
import br.com.davidcastro.home.viewmodel.MainViewModel
import br.com.davidcastro.meurastreio.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val module = module {

    single <TrackingDao> { AppDatabase.getDatabase(context = androidContext()).trackingDao }

    single <TrackingDaoRepository> { TrackingDaoRepositoryImpl(trackingDao = get()) }

    single <TrackingApi> { RetrofitClient.getRetrofitInstance(TrackingApi::class.java, BuildConfig.BASE_URL) }

    single <TrackingRepository> { TrackingRepositoryImpl(api = get()) }

    single <GetTrackingUseCase> { GetTrackingUseCaseImpl(repository = get()) }

    single <ReloadAllTrackingUseCase> {
        ReloadAllTrackingUseCaseImpl(
            getTrackingUseCase = get(),
            trackingDaoRepository = get()
        )
    }

    viewModel {
        MainViewModel(
            getTrackingUseCase = get(),
            reloadAllTrackingUseCase = get(),
            trackingDaoRepository = get()
        )
    }
}