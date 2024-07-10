package br.com.davidcastro.meurastreio.domain.usecase.remote.reloadalltrackingusecase

import kotlinx.coroutines.flow.Flow

interface ReloadAllTrackingUseCase {
    suspend operator fun invoke(): Flow<Boolean>
}