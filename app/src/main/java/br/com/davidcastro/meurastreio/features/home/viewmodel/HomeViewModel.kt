package br.com.davidcastro.meurastreio.features.home.viewmodel

import androidx.lifecycle.viewModelScope
import br.com.davidcastro.meurastreio.core.navigation.Routes
import br.com.davidcastro.meurastreio.core.utils.extensions.getAllTrackingCompleted
import br.com.davidcastro.meurastreio.core.utils.extensions.getAllTrackingInProgress
import br.com.davidcastro.meurastreio.domain.model.StateEnum
import br.com.davidcastro.meurastreio.domain.usecase.db.containstrackingindbusecase.ContainsTrackingInDbUseCase
import br.com.davidcastro.meurastreio.domain.usecase.db.getalltrackingsindbusecase.GetAllTrackingsInDbUseCase
import br.com.davidcastro.meurastreio.domain.usecase.remote.gettrackingusecase.GetTrackingUseCase
import br.com.davidcastro.meurastreio.domain.usecase.remote.reloadalltrackingusecase.ReloadAllTrackingUseCase
import br.com.davidcastro.meurastreio.domain.viewmodel.BaseViewModel
import br.com.davidcastro.meurastreio.features.home.mvi.HomeAction
import br.com.davidcastro.meurastreio.features.home.mvi.HomeResult
import br.com.davidcastro.meurastreio.features.home.mvi.HomeState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeViewModel (
    private val getTrackingUseCase: GetTrackingUseCase,
    private val reloadAllTrackingUseCase: ReloadAllTrackingUseCase,
    private val getAllTrackingsInDbUseCase: GetAllTrackingsInDbUseCase,
    private val containsTrackingInDbUseCase: ContainsTrackingInDbUseCase
): BaseViewModel<HomeAction, HomeResult, HomeState>() {

    override val initialState: HomeState
        get() = HomeState()

    override fun dispatch(event: HomeAction) {
        when (event) {
            is HomeAction.GetTracking -> getTracking(event.code)
            is HomeAction.CheckIfHasAlreadyInserted -> hasAlreadyInsertedTracking(event.code)
            is HomeAction.GetAllTracking -> getAllTrackings()
            is HomeAction.UpdateTrackingFilter -> updateTrackingFilter(event.filter)
            is HomeAction.ReloadAllTracking -> reloadAll()
            is HomeAction.ShowError -> showError(event.enabled)
            is HomeAction.NavigateTo -> navigateTo(event.route)
        }
    }

    private fun navigateTo(routes: Routes) {
        emitScreenResult(HomeResult.NavigateTo(routes))
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
        } catch (ex: Exception) {
            showError(enabled = true)
        }
    }

    private fun hasAlreadyInsertedTracking(code: String) = viewModelScope.launch {
        containsTrackingInDbUseCase(code).collect { hasAlreadyInsertedTracking ->
            if(hasAlreadyInsertedTracking) {
                updateUiState(
                    uiState.value.copy(
                        hasAlreadyInserted = true
                    )
                )
            } else {
                dispatch(
                    HomeAction.GetTracking(code = code)
                )
            }
        }
    }

    private fun getTracking(code: String) = viewModelScope.launch {
        getTrackingUseCase(code).onEach {
            if(it.events.isNullOrEmpty()) {
                updateUiState(
                    uiState.value.copy(
                        hasNotEvents = true
                    )
                )
            } else {
//                emitScreenResult(
//                    HomeResult.OpenDetailScreen(
//                        tracking = it,
//                        isFromResult = true
//                    )
//                )
            }
        }.onStart {
            showLoading(enabled = true)
        }.onCompletion {
            showLoading(enabled = false)
        }.catch {
            showError(enabled = true)
        }
    }

    private fun reloadAll() = viewModelScope.launch {
        try {
            showLoading(enabled = true)
            reloadAllTrackingUseCase()
        } catch (ex: Exception) {
            showError(enabled = true)
        } finally {
            showLoading(enabled = false)
        }
    }

    private fun showError(enabled: Boolean) {
        updateUiState(
            uiState.value.copy(
                hasError = enabled
            )
        )
    }

    private fun showLoading(enabled: Boolean) {
        updateUiState(
            uiState = uiState.value.copy(
                hasLoading = enabled,
            )
        )
    }
}