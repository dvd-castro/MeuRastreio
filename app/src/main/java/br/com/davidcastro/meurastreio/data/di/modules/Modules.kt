package br.com.davidcastro.meurastreio.data.di.modules

import br.com.davidcastro.meurastreio.data.repository.RastreioRepository
import br.com.davidcastro.meurastreio.data.source.DatabaseDataSource
import br.com.davidcastro.meurastreio.viewModel.DetalhesViewModel
import br.com.davidcastro.meurastreio.viewModel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val module = module {

    single {
        DatabaseDataSource(context = androidContext())
    }

    factory {
        RastreioRepository(databaseDataSource = get())
    }

    viewModel {
        MainViewModel(
            repository = get(),
            context = androidContext()
        )
    }
    viewModel {
        DetalhesViewModel(
            repository = get()
        )
    }
}