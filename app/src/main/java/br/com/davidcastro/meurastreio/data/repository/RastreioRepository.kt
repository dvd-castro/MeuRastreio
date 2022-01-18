package br.com.davidcastro.meurastreio.data.repository

import android.content.Context
import br.com.davidcastro.meurastreio.data.api.Api
import br.com.davidcastro.meurastreio.data.model.RastreioModel
import br.com.davidcastro.meurastreio.data.source.DatabaseDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RastreioRepository(context: Context) {

    private val databaseDataSource = DatabaseDataSource(context)
    private val networkApi = Api()

    suspend fun insertTracking(rastreio: RastreioModel) = withContext(Dispatchers.IO) {
        databaseDataSource.insert(rastreio)
    }

    suspend fun getAllTracking(): List<RastreioModel> = withContext(Dispatchers.IO){
        return@withContext databaseDataSource.getAll()
    }

    suspend fun getTracking(codigo: String): RastreioModel = withContext(Dispatchers.IO) {
        return@withContext databaseDataSource.get(codigo)
    }

    suspend fun deleteTracking(codigo: String) = withContext(Dispatchers.IO) {
        databaseDataSource.delete(codigo)
    }

    suspend fun containsTracking(codigo: String): Boolean = withContext(Dispatchers.IO) {
        return@withContext databaseDataSource.contains(codigo)
    }

    suspend fun findTracking(codigo: String): RastreioModel = withContext(Dispatchers.IO) {
        return@withContext networkApi.getData(codigo)
    }
}