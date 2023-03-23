package br.com.davidcastro.data.usecase.db

import br.com.davidcastro.data.model.TrackingModel

interface GetAllTrackingsInDbUseCase {

    suspend fun getAll(): List<TrackingModel>
}