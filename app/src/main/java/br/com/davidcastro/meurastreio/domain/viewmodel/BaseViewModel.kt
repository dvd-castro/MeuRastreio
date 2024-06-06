package br.com.davidcastro.meurastreio.domain.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel<ACTION, RESULT, STATE>: ViewModel() {
    private val _result = MutableSharedFlow<RESULT>()
    val result: SharedFlow<RESULT> = _result

    private val _uiState: MutableStateFlow<STATE> by lazy { MutableStateFlow(initialState) }
    val uiState: StateFlow<STATE> = _uiState

    protected abstract val initialState: STATE

    abstract fun dispatch(event: ACTION)

    protected fun updateUiState(uiState: STATE) {
        _uiState.update { uiState }
    }
}