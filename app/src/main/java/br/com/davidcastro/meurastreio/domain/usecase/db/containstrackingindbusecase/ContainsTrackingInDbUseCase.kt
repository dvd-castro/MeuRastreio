package br.com.davidcastro.meurastreio.domain.usecase.db.containstrackingindbusecase

import kotlinx.coroutines.flow.Flow

interface ContainsTrackingInDbUseCase {
    suspend fun contains(code: String): Flow<Boolean>
}