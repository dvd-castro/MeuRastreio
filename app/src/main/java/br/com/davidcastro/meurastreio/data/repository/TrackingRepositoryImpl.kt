package br.com.davidcastro.meurastreio.data.repository

import br.com.davidcastro.meurastreio.data.api.TrackingApi
import br.com.davidcastro.meurastreio.data.model.TrackingModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class TrackingRepositoryImpl(private val api: TrackingApi): TrackingRespository {
    override suspend fun getTracking(codigo: String): Response<TrackingModel> = withContext(Dispatchers.IO) {
        return@withContext api.getTracking(codigo)
    }
}