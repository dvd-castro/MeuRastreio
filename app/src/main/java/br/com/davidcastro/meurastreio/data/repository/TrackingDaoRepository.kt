package br.com.davidcastro.meurastreio.data.repository

import br.com.davidcastro.meurastreio.data.db.entity.TrackingEntity

interface TrackingDaoRepository {
    fun insert(rastreio: TrackingEntity)

    fun get(codigo: String): TrackingEntity

    fun delete(codigo: String)

    fun contains(codigo: String): Boolean

    fun getAll(): List<TrackingEntity>
}