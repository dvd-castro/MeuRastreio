package br.com.davidcastro.data.usecase.db.containstrackingindbusecase

interface ContainsTrackingInDbUseCase {

    suspend fun contains(codigo: String): Boolean
}