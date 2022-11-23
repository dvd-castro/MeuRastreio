package br.com.davidcastro.data.usecase

import br.com.davidcastro.data.repository.TrackingDaoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteTrackingUseCaseImpl(private val trackingDaoRepository: TrackingDaoRepository): DeleteTrackingUseCase {
    override suspend fun deleteTracking(code: String) {
        withContext(Dispatchers.IO) {
            trackingDaoRepository.delete(codigo = code)
        }
    }
}