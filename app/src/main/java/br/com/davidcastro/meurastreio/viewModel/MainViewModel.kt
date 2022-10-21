package br.com.davidcastro.meurastreio.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.davidcastro.data.model.TrackingModel
import br.com.davidcastro.data.repository.TrackingDaoRepository
import br.com.davidcastro.data.usecase.GetTrackingUseCase
import kotlinx.coroutines.launch

class MainViewModel (
    private val getTrackingUseCase: GetTrackingUseCase,
    private val trackingDaoRepository: TrackingDaoRepository,
    ): ViewModel() {

    private val _tracking = MutableLiveData<List<TrackingModel>>()
    val tracking: LiveData<List<TrackingModel>> = _tracking

    private val _hasError = MutableLiveData(false)
    val hasError: LiveData<Boolean> = _hasError

    private val _trackingAlreadyExists = MutableLiveData(false)
    val trackingAlreadyExists: LiveData<Boolean> = _trackingAlreadyExists

    fun getAllTrackingInDataBase() {
        viewModelScope.launch {
            trackingDaoRepository.getAll().let {
                if(it.isNotEmpty()) {
                    _tracking.postValue(it)
                }
            }
        }
    }

    fun getTracking(codigo: String) {
        viewModelScope.launch {
            try {
                if(!containsTracking(codigo)) {
                    getTrackingUseCase.getTracking(codigo)?.let {
                        trackingDaoRepository.insert(it)
                        getAllTrackingInDataBase()
                    } ?: run {
                        _hasError.postValue(true)
                    }
                } else {
                    _trackingAlreadyExists.postValue(true)
                }
            } catch (ex:Exception) {
                Log.d("###","$ex")
                _hasError.postValue(true)
            }
        }
    }

    suspend fun containsTracking(codigo: String): Boolean =
        trackingDaoRepository.contains(codigo)
}

