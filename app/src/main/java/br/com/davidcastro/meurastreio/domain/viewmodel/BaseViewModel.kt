package br.com.davidcastro.meurastreio.domain.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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

    protected fun emitScreenResult(screen: RESULT) = viewModelScope.launch {
        _result.emit(screen)
    }
}