package br.com.davidcastro.meurastreio.data.repository

import android.content.Context
import br.com.davidcastro.meurastreio.data.api.Api
import br.com.davidcastro.meurastreio.data.model.RastreioModel
import br.com.davidcastro.meurastreio.data.source.DatabaseDataSource

class RastreioRepository(context: Context) {

    private val databaseDataSource = DatabaseDataSource(context)
    private val networkApi = Api()

    suspend fun insertTracking(rastreio: RastreioModel) {
        databaseDataSource.inserirRastreio(rastreio)
    }

    suspend fun getAllTracking(): List<RastreioModel> {
        return databaseDataSource.getAll()
    }

    suspend fun getTracking(codigo: String): RastreioModel {
        return databaseDataSource.getRastreio(codigo)
    }

    suspend fun deleteTracking(codigo: String) {
        databaseDataSource.deleteRastreio(codigo)
    }

    suspend fun findTracking(codigo: String): RastreioModel{
        return networkApi.getData(codigo)
    }
}