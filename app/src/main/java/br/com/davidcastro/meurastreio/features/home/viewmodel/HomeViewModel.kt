package br.com.davidcastro.meurastreio.features.home.viewmodel

import androidx.lifecycle.viewModelScope
import br.com.davidcastro.meurastreio.core.utils.extensions.getAllTrackingCompleted
import br.com.davidcastro.meurastreio.core.utils.extensions.getAllTrackingInProgress
import br.com.davidcastro.meurastreio.domain.model.StateEnum
import br.com.davidcastro.meurastreio.domain.usecase.db.getalltrackingsindbusecase.GetAllTrackingsInDbUseCase
import br.com.davidcastro.meurastreio.domain.usecase.db.inserttrackingindbusecase.InsertTrackingInDbUseCase
import br.com.davidcastro.meurastreio.domain.usecase.remote.gettrackingusecase.GetTrackingUseCase
import br.com.davidcastro.meurastreio.domain.usecase.remote.reloadalltrackingusecase.ReloadAllTrackingUseCase
import br.com.davidcastro.meurastreio.domain.viewmodel.BaseViewModel
import br.com.davidcastro.meurastreio.features.home.mvi.HomeAction
import br.com.davidcastro.meurastreio.features.home.mvi.HomeResult
import br.com.davidcastro.meurastreio.features.home.mvi.HomeState
import kotlinx.coroutines.launch

class HomeViewModel (
    private val getTrackingUseCase: GetTrackingUseCase,
    private val reloadAllTrackingUseCase: ReloadAllTrackingUseCase,
    private val getAllTrackingsInDbUseCase: GetAllTrackingsInDbUseCase,
    private val insertTrackingInDbUseCase: InsertTrackingInDbUseCase,
): BaseViewModel<HomeAction, HomeResult, HomeState>() {

    override val initialState: HomeState
        get() = HomeState()

    override fun dispatch(event: HomeAction) {
        when (event) {
            is HomeAction.GetTracking -> getTracking(event.code)
            is HomeAction.GetAllTracking -> getAllTrackings()
            is HomeAction.UpdateTrackingFilter -> updateTrackingFilter(event.filter)
            is HomeAction.ReloadAllTracking -> reloadAll()
        }
    }

    private fun updateTrackingFilter(filter: String) {
        updateUiState(
            uiState.value.copy(
                currentSelectedFilter = when(filter) {
                    StateEnum.IN_PROGRESS.value -> {
                        uiState.value.allTrackings.getAllTrackingInProgress()
                    }
                    StateEnum.DONE.value -> {
                        uiState.value.allTrackings.getAllTrackingCompleted()
                    }
                    else -> {
                        uiState.value.allTrackings
                    }
                }
            )
        )
    }

    private fun getAllTrackings() = viewModelScope.launch {
        try {
            getAllTrackingsInDbUseCase().collect {
                updateUiState(
                    uiState.value.copy(
                        allTrackings = it,
                        currentSelectedFilter = it
                    )
                )
            }
        } catch (e: Exception) {
            updateUiState(
                uiState.value.copy(
                    hasError = true
                )
            )
        }
    }

    private fun getTracking(code: String) = viewModelScope.launch {
        try {
            showLoading(true)
            getTrackingUseCase(code).collect {
                if(it.events.isNullOrEmpty()) {
                    updateUiState(
                        uiState.value.copy(
                            hasNotEvents = true
                        )
                    )
                } else {
                    insertTrackingInDbUseCase(it)
                }
            }
        } catch (e: Exception) {
            updateUiState(
                uiState.value.copy(
                    hasError = true
                )
            )
        } finally {
            showLoading(false)
        }
    }

    private fun reloadAll() = viewModelScope.launch {
        try {
            showLoading(true)
            reloadAllTrackingUseCase()
        } catch (e: Exception) {
            updateUiState(
                uiState.value.copy(
                    hasError = true
                )
            )
        } finally {
            showLoading(false)
        }
    }

    private fun showLoading(enable: Boolean) {
        updateUiState(
            uiState = uiState.value.copy(
                hasLoading = enable,
            )
        )
    }
}