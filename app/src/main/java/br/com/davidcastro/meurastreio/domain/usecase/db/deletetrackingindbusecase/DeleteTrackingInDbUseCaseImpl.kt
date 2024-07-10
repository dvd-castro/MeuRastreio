package br.com.davidcastro.meurastreio.domain.usecase.db.deletetrackingindbusecase

import br.com.davidcastro.meurastreio.data.repository.TrackingRepository

class DeleteTrackingInDbUseCaseImpl(
    private val repository: TrackingRepository
    ): DeleteTrackingInDbUseCase {

    override suspend operator fun invoke(code: String) {
        repository.delete(code = code)
    }
}