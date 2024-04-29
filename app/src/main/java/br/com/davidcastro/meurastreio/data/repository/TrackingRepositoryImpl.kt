package br.com.davidcastro.meurastreio.data.repository

import br.com.davidcastro.meurastreio.data.datasource.db.DbDataSource
import br.com.davidcastro.meurastreio.data.datasource.remote.RemoteDataSource
import br.com.davidcastro.meurastreio.domain.mappers.toDomain
import br.com.davidcastro.meurastreio.domain.mappers.toEntity
import br.com.davidcastro.meurastreio.domain.model.TrackingDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TrackingRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val dbDataSource: DbDataSource
): TrackingRepository {
    override suspend fun getTracking(code: String): Flow<TrackingDomain> {
        return remoteDataSource.getTracking(code = code).toDomain()
    }

    override suspend fun insert(tracking: TrackingDomain) {
        dbDataSource.insert(tracking.toEntity())
    }

    override suspend fun delete(code: String) {
        dbDataSource.delete(code)
    }

    override suspend fun contains(code: String): Flow<Boolean> {
        return dbDataSource.contains(code)
    }

    override suspend fun getAll(): Flow<List<TrackingDomain>> {
        return dbDataSource.getAll().map { list -> list.map { it.toDomain() } }
    }
}