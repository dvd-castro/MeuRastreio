package br.com.davidcastro.data.repository.trackingdaorepository

import br.com.davidcastro.data.db.dao.TrackingDao
import br.com.davidcastro.data.db.entity.TrackingEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TrackingDaoRepositoryImpl(private val trackingDao: TrackingDao): TrackingDaoRepository {
    override suspend fun insert(rastreio: TrackingEntity) =
        withContext(Dispatchers.IO) {
            trackingDao.insert(rastreio)
        }

    override suspend fun delete(codigo: String) =
        withContext(Dispatchers.IO) {
            trackingDao.delete(codigo)
        }

    override suspend fun contains(codigo: String): Boolean =
        withContext(Dispatchers.IO) {
            return@withContext trackingDao.contains(codigo)
        }

    override suspend fun getAll(): List<TrackingEntity> =
        withContext(Dispatchers.IO) {
            return@withContext trackingDao.getAll()
        }
}