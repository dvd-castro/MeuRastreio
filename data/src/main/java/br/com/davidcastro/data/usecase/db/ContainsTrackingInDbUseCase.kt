package br.com.davidcastro.data.usecase.db

interface ContainsTrackingInDbUseCase {

    suspend fun contains(codigo: String): Boolean
}