package br.com.davidcastro.data.repository

import br.com.davidcastro.data.db.dao.TrackingDao
import br.com.davidcastro.data.model.TrackingList
import br.com.davidcastro.data.model.TrackingModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TrackingDaoRepositoryImpl(private val trackingDao: TrackingDao): TrackingDaoRepository {
    override suspend fun insert(rastreio: TrackingModel) =
        withContext(Dispatchers.IO) {
            trackingDao.insert(rastreio.toTrackingEntity())
        }

    override suspend fun get(codigo: String): TrackingModel =
        withContext(Dispatchers.IO) {
            return@withContext trackingDao.get(codigo).toTrackingModel()
        }

    override suspend fun delete(codigo: String) =
        withContext(Dispatchers.IO) {
            trackingDao.delete(codigo)
        }

    override suspend fun contains(codigo: String): Boolean =
        withContext(Dispatchers.IO) {
            return@withContext trackingDao.contains(codigo)
        }

    override suspend fun getAll(): TrackingList =
        withContext(Dispatchers.IO) {
            return@withContext TrackingList(trackingDao.getAll().map { it.toTrackingModel() }.reversed())
        }
}