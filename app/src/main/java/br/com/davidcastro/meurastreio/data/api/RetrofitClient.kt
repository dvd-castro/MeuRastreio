package br.com.davidcastro.meurastreio.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    fun <S> getRetrofitInstance(serviceClass: Class<S>, path: String) : S =
        Retrofit.Builder()
            .baseUrl(path)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(serviceClass)
}