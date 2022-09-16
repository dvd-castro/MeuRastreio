package br.com.davidcastro.meurastreio.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.davidcastro.meurastreio.data.model.TrackingModel
import br.com.davidcastro.meurastreio.data.repository.TrackingRespository
import kotlinx.coroutines.launch

class MainViewModel (private val repository: TrackingRespository): ViewModel() {

    private val _tracking = MutableLiveData<TrackingModel>()
    val tracking: LiveData<TrackingModel> = _tracking

    private val _hasError = MutableLiveData(false)
    val hasError: LiveData<Boolean> = _hasError

    fun getTracking(codigo: String) {
        viewModelScope.launch {
            try {
                val response = repository.getTracking(codigo)
                if(response.code() == 200 && response.body()?.isValidTracking() == true) {
                    _tracking.postValue(response.body())
                } else {
                    _hasError.postValue(true)
                }
            } catch (ex:Exception) {
                _hasError.postValue(true)
            }
        }
    }
}

