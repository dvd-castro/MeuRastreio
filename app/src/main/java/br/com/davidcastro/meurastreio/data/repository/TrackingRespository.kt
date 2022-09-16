package br.com.davidcastro.meurastreio.data.repository

import br.com.davidcastro.meurastreio.data.model.TrackingModel
import retrofit2.Response

interface TrackingRespository {
    suspend fun getTracking(codigo: String) : Response<TrackingModel>
}