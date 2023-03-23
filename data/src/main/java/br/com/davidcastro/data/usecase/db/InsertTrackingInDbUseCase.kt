package br.com.davidcastro.data.usecase.db

import br.com.davidcastro.data.model.TrackingModel

interface InsertTrackingInDbUseCase {

    suspend fun insert(rastreio: TrackingModel)
}