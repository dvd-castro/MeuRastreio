package br.com.davidcastro.meurastreio.core.di

import br.com.davidcastro.home.viewmodel.MainViewModel
import br.com.davidcastro.meurastreio.BuildConfig
import br.com.davidcastro.meurastreio.data.datasource.db.DbDataSource
import br.com.davidcastro.meurastreio.data.datasource.db.DbDataSourceImpl
import br.com.davidcastro.meurastreio.data.datasource.remote.RemoteDataSource
import br.com.davidcastro.meurastreio.data.datasource.remote.RemoteDataSourceImpl
import br.com.davidcastro.meurastreio.data.repository.TrackingRepository
import br.com.davidcastro.meurastreio.data.repository.TrackingRepositoryImpl
import br.com.davidcastro.meurastreio.data.service.db.AppDatabase
import br.com.davidcastro.meurastreio.data.service.db.dao.TrackingDao
import br.com.davidcastro.meurastreio.data.service.remote.api.TrackingApi
import br.com.davidcastro.meurastreio.data.service.remote.client.RetrofitClient
import br.com.davidcastro.meurastreio.domain.usecase.db.containstrackingindbusecase.ContainsTrackingInDbUseCase
import br.com.davidcastro.meurastreio.domain.usecase.db.containstrackingindbusecase.ContainsTrackingInDbUseCaseImpl
import br.com.davidcastro.meurastreio.domain.usecase.db.getalltrackingsindbusecase.GetAllTrackingsInDbUseCase
import br.com.davidcastro.meurastreio.domain.usecase.db.getalltrackingsindbusecase.GetAllTrackingsInDbUseCaseImpl
import br.com.davidcastro.meurastreio.domain.usecase.db.inserttrackingindbusecase.InsertTrackingInDbUseCase
import br.com.davidcastro.meurastreio.domain.usecase.db.inserttrackingindbusecase.InsertTrackingInDbUseCaseImpl
import br.com.davidcastro.meurastreio.domain.usecase.remote.gettrackingusecase.GetTrackingUseCase
import br.com.davidcastro.meurastreio.domain.usecase.remote.gettrackingusecase.GetTrackingUseCaseImpl
import br.com.davidcastro.meurastreio.domain.usecase.remote.reloadalltrackingusecase.ReloadAllTrackingUseCase
import br.com.davidcastro.meurastreio.domain.usecase.remote.reloadalltrackingusecase.ReloadAllTrackingUseCaseImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val module = module {

    single <TrackingDao> {
        AppDatabase.getDatabase(context = androidContext()).trackingDao
    }

    single <TrackingApi> {
        RetrofitClient.getRetrofitInstance(TrackingApi::class.java, BuildConfig.BASE_URL)
    }

    single <CoroutineDispatcher> {
        Dispatchers.IO
    }

    single <RemoteDataSource> {
        RemoteDataSourceImpl(
            api = get(),
            dispatcher = get()
        )
    }

    single <DbDataSource> {
        DbDataSourceImpl(
            dao = get(),
            dispatcher = get()
        )
    }

    single <TrackingRepository> {
        TrackingRepositoryImpl(
            remoteDataSource = get(),
            dbDataSource = get()
        )
    }

    single <GetTrackingUseCase> {
        GetTrackingUseCaseImpl(
            repository = get()
        )
    }

    single <GetAllTrackingsInDbUseCase> {
        GetAllTrackingsInDbUseCaseImpl(
            repository = get()
        )
    }

    single <InsertTrackingInDbUseCase> {
        InsertTrackingInDbUseCaseImpl(
            repository = get()
        )
    }

    single <ContainsTrackingInDbUseCase> {
        ContainsTrackingInDbUseCaseImpl(
            repository = get()
        )
    }

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