package br.com.davidcastro.meurastreio.di

import br.com.davidcastro.data.api.RetrofitClient
import br.com.davidcastro.data.api.TrackingApi
import br.com.davidcastro.data.db.AppDatabase
import br.com.davidcastro.data.db.dao.TrackingDao
import br.com.davidcastro.data.repository.trackingdaorepository.TrackingDaoRepository
import br.com.davidcastro.data.repository.trackingdaorepository.TrackingDaoRepositoryImpl
import br.com.davidcastro.data.repository.trackingrepository.TrackingRepository
import br.com.davidcastro.data.repository.trackingrepository.TrackingRepositoryImpl
import br.com.davidcastro.data.usecase.db.containstrackingindbusecase.ContainsTrackingInDbUseCase
import br.com.davidcastro.data.usecase.db.containstrackingindbusecase.ContainsTrackingInDbUseCaseImpl
import br.com.davidcastro.data.usecase.db.getalltrackingsindbusecase.GetAllTrackingsInDbUseCase
import br.com.davidcastro.data.usecase.db.getalltrackingsindbusecase.GetAllTrackingsInDbUseCaseImpl
import br.com.davidcastro.data.usecase.db.inserttrackingindbusecase.InsertTrackingInDbUseCase
import br.com.davidcastro.data.usecase.db.inserttrackingindbusecase.InsertTrackingInDbUseCaseImpl
import br.com.davidcastro.data.usecase.remote.gettrackingusecase.GetTrackingUseCase
import br.com.davidcastro.data.usecase.remote.gettrackingusecase.GetTrackingUseCaseImpl
import br.com.davidcastro.data.usecase.remote.reloadalltrackingusecase.ReloadAllTrackingUseCase
import br.com.davidcastro.data.usecase.remote.reloadalltrackingusecase.ReloadAllTrackingUseCaseImpl
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

    single <GetAllTrackingsInDbUseCase> { GetAllTrackingsInDbUseCaseImpl(trackingDaoRepository = get()) }

    single <InsertTrackingInDbUseCase> { InsertTrackingInDbUseCaseImpl(trackingDaoRepository = get()) }

    single <ContainsTrackingInDbUseCase> { ContainsTrackingInDbUseCaseImpl(trackingDaoRepository = get()) }

    single <ReloadAllTrackingUseCase> {
        ReloadAllTrackingUseCaseImpl(
            getTrackingUseCase = get(),
            getAllTrackingsInDbUseCase = get(),
            insertTrackingInDbUseCase = get()
        )
    }

    viewModel {
        MainViewModel(
            getTrackingUseCase = get(),
            reloadAllTrackingUseCase = get(),
            getAllTrackingsInDbUseCase = get(),
            insertTrackingInDbUseCase = get(),
            containsTrackingInDbUseCase = get()
        )
    }
}