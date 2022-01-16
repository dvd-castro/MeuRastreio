package br.com.davidcastro.meurastreio.data.api

import br.com.davidcastro.meurastreio.data.model.RastreioModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("json")
    suspend fun getRastreio(
        @Query("user") email: String,
        @Query("token") token: String,
        @Query("codigo") codigo: String,
    ): RastreioModel

}