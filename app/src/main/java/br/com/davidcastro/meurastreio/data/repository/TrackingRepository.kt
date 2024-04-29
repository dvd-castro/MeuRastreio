package br.com.davidcastro.meurastreio.data.repository

import br.com.davidcastro.meurastreio.domain.model.TrackingDomain
import kotlinx.coroutines.flow.Flow

interface TrackingRepository {
    suspend fun getTracking(code: String): Flow<TrackingDomain>

    suspend fun insert(tracking: TrackingDomain)

    suspend fun delete(code: String)

    suspend fun contains(code: String): Flow<Boolean>

    suspend fun getAll(): Flow<List<TrackingDomain>>
}