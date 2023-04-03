package br.com.davidcastro.data.repository.trackingdaorepository

import br.com.davidcastro.data.db.entity.TrackingEntity

interface TrackingDaoRepository {
    suspend fun insert(rastreio: TrackingEntity)

    suspend fun delete(codigo: String)

    suspend fun contains(codigo: String): Boolean

    suspend fun getAll(): List<TrackingEntity>
}