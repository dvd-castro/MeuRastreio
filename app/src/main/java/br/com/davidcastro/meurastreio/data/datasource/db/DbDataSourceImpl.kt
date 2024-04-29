package br.com.davidcastro.meurastreio.data.datasource.db

import br.com.davidcastro.meurastreio.data.service.db.dao.TrackingDao
import br.com.davidcastro.meurastreio.data.service.db.entity.TrackingEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class DbDataSourceImpl(
    private val dao: TrackingDao,
    private val dispatcher: CoroutineDispatcher
): DbDataSource {
    override suspend fun insert(tracking: TrackingEntity) = withContext(dispatcher) {
        dao.insert(tracking)
    }

    override suspend fun delete(code: String) = withContext(dispatcher) {
        dao.delete(code)
    }

    override suspend fun contains(code: String): Flow<Boolean> = flow {
        emit(dao.contains(code))
    }.flowOn(dispatcher)

    override suspend fun getAll(): Flow<List<TrackingEntity>> = flow {
        emit(dao.getAll())
    }.flowOn(dispatcher)
}