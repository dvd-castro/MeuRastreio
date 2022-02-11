package br.com.davidcastro.meurastreio.data.api

import br.com.davidcastro.meurastreio.data.api.Constansts.TOKEN
import br.com.davidcastro.meurastreio.data.api.Constansts.USER
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Api(private val retrofitClient: ApiService) {

    suspend fun getData(codigo: String) = withContext(Dispatchers.IO){
        return@withContext retrofitClient.getRastreio(email = USER, token = TOKEN, codigo = codigo)
    }
}