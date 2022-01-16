package br.com.davidcastro.meurastreio.data.api

import br.com.davidcastro.meurastreio.data.api.Constansts.BASE_URL
import br.com.davidcastro.meurastreio.data.api.Constansts.USER
import br.com.davidcastro.meurastreio.data.api.Constansts.TOKEN
import br.com.davidcastro.meurastreio.helpers.utils.NetworkUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Api {

    suspend fun getData(codigo: String) = withContext(Dispatchers.IO){

        val retrofitClient = NetworkUtils.getRetrofitInstance(serviceClass = ApiService::class.java, path = BASE_URL )

        return@withContext retrofitClient.getRastreio(email = USER, token = TOKEN, codigo = codigo)
    }
}