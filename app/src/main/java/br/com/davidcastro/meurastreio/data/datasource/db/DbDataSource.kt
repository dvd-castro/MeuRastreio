package br.com.davidcastro.meurastreio.data.datasource.db

import br.com.davidcastro.meurastreio.data.service.db.entity.TrackingEntity
import kotlinx.coroutines.flow.Flow

interface DbDataSource {
    suspend fun insert(tracking: TrackingEntity)

    suspend fun delete(code: String)

    suspend fun contains(code: String): Flow<Boolean>

    suspend fun getAll(): Flow<List<TrackingEntity>>
}