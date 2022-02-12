package br.com.davidcastro.meurastreio.data.di.modules

import br.com.davidcastro.meurastreio.data.api.Api
import br.com.davidcastro.meurastreio.data.api.ApiService
import br.com.davidcastro.meurastreio.data.api.Constansts
import br.com.davidcastro.meurastreio.data.repository.RastreioRepository
import br.com.davidcastro.meurastreio.data.source.DatabaseDataSource
import br.com.davidcastro.meurastreio.helpers.utils.NetworkUtils
import br.com.davidcastro.meurastreio.viewModel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val module = module {

    single {
        DatabaseDataSource(context = androidContext())
    }

    single {
        Api(retrofitClient = NetworkUtils.getRetrofitInstance(serviceClass = ApiService::class.java, path = Constansts.BASE_URL))
    }

    factory {
        RastreioRepository(databaseDataSource = get(), networkApi = get())
    }

    viewModel {
        MainViewModel(
            repository = get(),
            context = androidContext()
        )
    }
}