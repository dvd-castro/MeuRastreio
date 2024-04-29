package br.com.davidcastro.meurastreio.domain.usecase.db.inserttrackingindbusecase

import br.com.davidcastro.meurastreio.domain.model.TrackingDomain

interface InsertTrackingInDbUseCase {
    suspend fun insert(tracking: TrackingDomain)
}