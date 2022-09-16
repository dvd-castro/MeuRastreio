package br.com.davidcastro.meurastreio.data.api

import br.com.davidcastro.meurastreio.data.model.TrackingModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TrackingApi {
    @GET("v1/sro-rastro/{codigo}")
    suspend fun getTracking(
        @Path("codigo") codigo: String
    ): Response<TrackingModel>
}