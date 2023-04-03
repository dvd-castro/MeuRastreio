package br.com.davidcastro.data.repository.trackingrepository

import br.com.davidcastro.data.api.TrackingApi
import br.com.davidcastro.data.model.TrackingModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class TrackingRepositoryImpl(private val api: TrackingApi): TrackingRepository {
    override suspend fun getTracking(codigo: String): Response<TrackingModel> = withContext(Dispatchers.IO) {
        return@withContext api.getTracking(codigo = codigo)
    }
}