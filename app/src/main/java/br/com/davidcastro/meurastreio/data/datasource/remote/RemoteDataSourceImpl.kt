package br.com.davidcastro.meurastreio.data.datasource.remote

import br.com.davidcastro.meurastreio.data.service.remote.api.TrackingApi
import br.com.davidcastro.meurastreio.data.service.remote.response.TrackingResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSourceImpl(
    private val api: TrackingApi,
    private val dispatcher: CoroutineDispatcher
): RemoteDataSource {
    override suspend fun getTracking(code: String): Flow<TrackingResponse> = flow {
        emit(api.getTracking(code = code))
    }.flowOn(dispatcher)
}