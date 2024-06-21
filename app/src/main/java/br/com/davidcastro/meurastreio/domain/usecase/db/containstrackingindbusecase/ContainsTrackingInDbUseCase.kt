package br.com.davidcastro.meurastreio.domain.usecase.db.containstrackingindbusecase

import kotlinx.coroutines.flow.Flow

interface ContainsTrackingInDbUseCase {
    suspend operator fun invoke(code: String): Flow<Boolean>
}