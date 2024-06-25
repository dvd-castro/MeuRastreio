package br.com.davidcastro.meurastreio.features.details.viewmodel

import br.com.davidcastro.meurastreio.domain.usecase.db.deletetrackingindbusecase.DeleteTrackingInDbUseCase
import br.com.davidcastro.meurastreio.domain.usecase.db.inserttrackingindbusecase.InsertTrackingInDbUseCase
import br.com.davidcastro.meurastreio.domain.viewmodel.BaseViewModel
import br.com.davidcastro.meurastreio.features.details.mvi.DetailsAction
import br.com.davidcastro.meurastreio.features.details.mvi.DetailsResult
import br.com.davidcastro.meurastreio.features.details.mvi.DetailsState

class DetailsViewModel(
    private val insertTrackingInDbUseCase: InsertTrackingInDbUseCase,
    private val deleteTrackingInDbUseCase: DeleteTrackingInDbUseCase,
): BaseViewModel<DetailsAction, DetailsResult, DetailsState>() {
    override val initialState: DetailsState
        get() = DetailsState()

    override fun dispatch(event: DetailsAction) {

    }
}