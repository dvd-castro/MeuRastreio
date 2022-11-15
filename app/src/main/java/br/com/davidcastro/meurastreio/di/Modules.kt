package br.com.davidcastro.meurastreio.di

import br.com.davidcastro.data.api.RetrofitClient
import br.com.davidcastro.data.api.TrackingApi
import br.com.davidcastro.data.db.AppDatabase
import br.com.davidcastro.data.db.dao.TrackingDao
import br.com.davidcastro.data.repository.TrackingDaoRepository
import br.com.davidcastro.data.repository.TrackingDaoRepositoryImpl
import br.com.davidcastro.data.repository.TrackingRepository
import br.com.davidcastro.data.repository.TrackingRepositoryImpl
import br.com.davidcastro.data.usecase.GetTrackingUseCase
import br.com.davidcastro.data.usecase.GetTrackingUseCaseImpl
import br.com.davidcastro.data.usecase.ReloadAllTrackingUseCase
import br.com.davidcastro.data.usecase.ReloadAllTrackingUseCaseImpl
import br.com.davidcastro.meurastreio.viewModel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val module = module {

    single <TrackingDao> { AppDatabase.getDatabase(context = androidContext()).trackingDao }

    single <TrackingDaoRepository> { TrackingDaoRepositoryImpl(trackingDao = get()) }

    single <TrackingApi> { RetrofitClient.getRetrofitInstance(TrackingApi::class.java, "https://api.linketrack.com/track/") }

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