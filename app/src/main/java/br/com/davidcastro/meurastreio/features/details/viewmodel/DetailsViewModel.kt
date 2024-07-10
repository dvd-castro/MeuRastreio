package br.com.davidcastro.meurastreio.features.details.viewmodel

import androidx.lifecycle.viewModelScope
import br.com.davidcastro.meurastreio.domain.model.TrackingDomain
import br.com.davidcastro.meurastreio.domain.usecase.db.deletetrackingindbusecase.DeleteTrackingInDbUseCase
import br.com.davidcastro.meurastreio.domain.usecase.db.inserttrackingindbusecase.InsertTrackingInDbUseCase
import br.com.davidcastro.meurastreio.domain.viewmodel.BaseViewModel
import br.com.davidcastro.meurastreio.features.details.mvi.DetailsAction
import br.com.davidcastro.meurastreio.features.details.mvi.DetailsResult
import br.com.davidcastro.meurastreio.features.details.mvi.DetailsState
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val insertTrackingInDbUseCase: InsertTrackingInDbUseCase,
    private val deleteTrackingInDbUseCase: DeleteTrackingInDbUseCase,
): BaseViewModel<DetailsAction, DetailsResult, DetailsState>() {
    override val initialState: DetailsState
        get() = DetailsState()

    override fun dispatch(event: DetailsAction) {
        when(event) {
            is DetailsAction.SaveTracking -> {
                saveTracking(event.trackingDomain)
            }

            is DetailsAction.DeleteTracking -> {
                deleteTracking(event.code)
            }

            is DetailsAction.ShareTracking -> {

            }

            is DetailsAction.ShowSetNameDialog -> {
                showSetNameDialog(event.enable)
            }
        }
    }

    private fun showSetNameDialog(enable: Boolean) {
        updateUiState(
            uiState.value.copy(
                showSetNameDialog = enable
            )
        )
    }

    private fun deleteTracking(
        code: String
    ) = viewModelScope.launch {
        try {
            deleteTrackingInDbUseCase(code)
            emitScreenResult(
                DetailsResult.ExitScreen
            )
        } catch (ex: Exception) {

        }
    }

    private fun saveTracking(
        trackingDomain: TrackingDomain
    ) = viewModelScope.launch {
        try {
            insertTrackingInDbUseCase(trackingDomain)
            showSetNameDialog(false)
            emitScreenResult(
                DetailsResult.ExitScreen
            )
        } catch (ex: Exception) {

        }
    }
}