package br.com.davidcastro.data.repository.trackingrepository

import br.com.davidcastro.data.model.TrackingModel
import retrofit2.Response

interface TrackingRepository {
    suspend fun getTracking(codigo: String): Response<TrackingModel>
}