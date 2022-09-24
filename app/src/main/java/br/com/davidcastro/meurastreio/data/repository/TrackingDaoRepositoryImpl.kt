package br.com.davidcastro.meurastreio.data.repository

import br.com.davidcastro.meurastreio.data.db.dao.TrackingDao
import br.com.davidcastro.meurastreio.data.db.entity.TrackingEntity

class TrackingDaoRepositoryImpl(private val trackingDao: TrackingDao): TrackingDaoRepository {
    override fun insert(rastreio: TrackingEntity) =
        trackingDao.insert(rastreio)

    override fun get(codigo: String): TrackingEntity =
        trackingDao.get(codigo)

    override fun delete(codigo: String) =
        trackingDao.delete(codigo)

    override fun contains(codigo: String): Boolean =
        trackingDao.contains(codigo)

    override fun getAll(): List<TrackingEntity> =
        trackingDao.getAll()
}