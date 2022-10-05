package br.com.davidcastro.meurastreio.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.davidcastro.meurastreio.data.model.TrackingHome
import br.com.davidcastro.meurastreio.data.usecase.GetTrackingUseCase
import kotlinx.coroutines.launch

class MainViewModel (private val getTrackingUseCase: GetTrackingUseCase): ViewModel() {

    private val _tracking = MutableLiveData<TrackingHome>()
    val tracking: LiveData<TrackingHome> = _tracking

    private val _hasError = MutableLiveData(false)
    val hasError: LiveData<Boolean> = _hasError

    fun getTracking(codigo: String) {
        viewModelScope.launch {
            try {
                val response = getTrackingUseCase.getTracking(codigo)
                response?.let {
                    _tracking.postValue(it.toTrackingHome())
                } ?: run {
                    _hasError.postValue(true)
                }
            } catch (ex:Exception) {
                _hasError.postValue(true)
            }
        }
    }
}

