package br.com.davidcastro.meurastreio.features.home.viewmodel

import androidx.lifecycle.viewModelScope
import br.com.davidcastro.meurastreio.commons.utils.extensions.getAllTrackingCompleted
import br.com.davidcastro.meurastreio.commons.utils.extensions.getAllTrackingInProgress
import br.com.davidcastro.meurastreio.commons.utils.extensions.toStringArgs
import br.com.davidcastro.meurastreio.core.navigation.Routes
import br.com.davidcastro.meurastreio.domain.model.StateEnum
import br.com.davidcastro.meurastreio.domain.usecase.db.containstrackingindbusecase.ContainsTrackingInDbUseCase
import br.com.davidcastro.meurastreio.domain.usecase.db.getalltrackingsindbusecase.GetAllTrackingsInDbUseCase
import br.com.davidcastro.meurastreio.domain.usecase.remote.gettrackingusecase.GetTrackingUseCase
import br.com.davidcastro.meurastreio.domain.usecase.remote.reloadalltrackingusecase.ReloadAllTrackingUseCase
import br.com.davidcastro.meurastreio.domain.viewmodel.BaseViewModel
import br.com.davidcastro.meurastreio.features.home.mvi.ErrorType
import br.com.davidcastro.meurastreio.features.home.mvi.HomeAction
import br.com.davidcastro.meurastreio.features.home.mvi.HomeResult
import br.com.davidcastro.meurastreio.features.home.mvi.HomeState
import kotlinx.coroutines.flow.collect
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
            is HomeAction.ShowError -> showError(event.errorType)
            is HomeAction.NavigateTo -> navigateTo(event.route)
        }
    }

    private fun showError(errorType: ErrorType) {
        updateUiState(
            uiState.value.copy(
                hasError = errorType
            )
        )
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
            showError(ErrorType.ErrorGetInDb)
        }
    }

    private fun hasAlreadyInsertedTracking(code: String) = viewModelScope.launch {
        containsTrackingInDbUseCase(code).collect { hasAlreadyInsertedTracking ->
            if(hasAlreadyInsertedTracking) {
                showError(ErrorType.ErrorHasAlreadyInserted)
            } else {
                dispatch(
                    HomeAction.GetTracking(code = code)
                )
            }
        }
    }

    private fun getTracking(code: String) = viewModelScope.launch {
        try {
            showLoading(enabled = true)
            getTrackingUseCase(code).collect {
                emitScreenResult(
                    HomeResult.NavigateTo(
                        Routes.DetailScreen(
                            tracking = it.toStringArgs(),
                            isFromResult = true
                        )
                    )
                )
            }
        } catch (ex: Exception) {
            showError(ErrorType.ErrorGetInRemote)
        } finally {
            showLoading(enabled = false)
        }
    }

    private fun reloadAll() = viewModelScope.launch {
        try {
            showLoading(enabled = true)
            reloadAllTrackingUseCase().collect()
        } catch (ex: Exception) {
            showError(ErrorType.ErrorGetInRemote)
        } finally {
            showLoading(enabled = false)
        }
    }

    private fun showLoading(enabled: Boolean) {
        updateUiState(
            uiState = uiState.value.copy(
                hasLoading = enabled,
            )
        )
    }
}