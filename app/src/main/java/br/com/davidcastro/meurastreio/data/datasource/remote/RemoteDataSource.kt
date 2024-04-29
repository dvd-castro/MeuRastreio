package br.com.davidcastro.meurastreio.data.datasource.remote

import br.com.davidcastro.meurastreio.data.service.remote.response.TrackingResponse
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    suspend fun getTracking(code: String): Flow<TrackingResponse>
}