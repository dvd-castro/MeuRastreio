package br.com.davidcastro.meurastreio.data.di.modules

import br.com.davidcastro.meurastreio.data.repository.RastreioRepository
import br.com.davidcastro.meurastreio.viewModel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val module = module {
    viewModel {
        MainViewModel(
            repository = RastreioRepository(androidContext())
        )
    }
}