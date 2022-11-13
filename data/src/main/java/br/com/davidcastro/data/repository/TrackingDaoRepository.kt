package br.com.davidcastro.data.repository

import br.com.davidcastro.data.model.TrackingList
import br.com.davidcastro.data.model.TrackingModel

interface TrackingDaoRepository {
    suspend fun insert(rastreio: TrackingModel)

    suspend fun update(rastreio: TrackingModel)

    suspend fun get(codigo: String): TrackingModel

    suspend fun delete(codigo: String)

    suspend fun contains(codigo: String): Boolean

    suspend fun getAll(): TrackingList
}