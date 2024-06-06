package br.com.davidcastro.meurastreio.domain.usecase.remote.reloadalltrackingusecase

interface ReloadAllTrackingUseCase {
    suspend operator fun invoke(): Boolean
}