package br.com.davidcastro.meurastreio.data.service.remote.api

import br.com.davidcastro.meurastreio.BuildConfig
import br.com.davidcastro.meurastreio.data.service.remote.response.TrackingResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TrackingApi {
    @GET("json")
    suspend fun getTracking(
        @Query("user") email: String = BuildConfig.API_USER,
        @Query("token") token: String = BuildConfig.API_TOKEN,
        @Query("codigo") code: String
    ): TrackingResponse
}