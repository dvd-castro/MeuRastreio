package br.com.davidcastro.trackingdetails.di

import br.com.davidcastro.data.db.AppDatabase
import br.com.davidcastro.data.db.dao.TrackingDao
import br.com.davidcastro.data.repository.TrackingDaoRepository
import br.com.davidcastro.data.repository.TrackingDaoRepositoryImpl
import br.com.davidcastro.data.usecase.db.DeleteTrackingInDbUseCase
import br.com.davidcastro.data.usecase.db.DeleteTrackingInDbUseCaseImpl
import br.com.davidcastro.trackingdetails.viewmodel.TrackingDetailsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object TrackingDetailsModule {

    private val modules = module {
        single <TrackingDao> { AppDatabase.getDatabase(context = androidContext() ).trackingDao }

        single <TrackingDaoRepository> { TrackingDaoRepositoryImpl(trackingDao = get()) }

        single<DeleteTrackingInDbUseCase> { DeleteTrackingInDbUseCaseImpl(trackingDaoRepository = get()) }

        viewModel { TrackingDetailsViewModel(deleteTrackingInDbUseCase = get()) }
    }

    fun inject() = loadKoinModules(modules)
}