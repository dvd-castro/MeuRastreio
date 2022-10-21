package br.com.davidcastro.data.api

import br.com.davidcastro.data.model.TrackingModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TrackingApi {
    @GET("json")
    suspend fun getTracking(
        @Query("user") email: String = "",
        @Query("token") token: String = "",
        @Query("codigo") codigo: String
    ): Response<TrackingModel>
}