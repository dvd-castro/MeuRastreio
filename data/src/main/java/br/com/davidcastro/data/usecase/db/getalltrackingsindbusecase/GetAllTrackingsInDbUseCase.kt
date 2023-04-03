package br.com.davidcastro.data.usecase.db.getalltrackingsindbusecase

import br.com.davidcastro.data.model.TrackingModel

interface GetAllTrackingsInDbUseCase {

    suspend fun getAll(): List<TrackingModel>
}