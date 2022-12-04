package br.com.davidcastro.meurastreio.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.davidcastro.data.model.TrackingList
import br.com.davidcastro.data.model.TrackingModel
import br.com.davidcastro.data.repository.TrackingDaoRepository
import br.com.davidcastro.data.usecase.GetTrackingUseCase
import br.com.davidcastro.data.usecase.ReloadAllTrackingUseCase
import kotlinx.coroutines.launch

class MainViewModel (
    private val getTrackingUseCase: GetTrackingUseCase,
    private val reloadAllTrackingUseCase: ReloadAllTrackingUseCase,
    private val trackingDaoRepository: TrackingDaoRepository,
    ): ViewModel() {

    private val _trackingInProgress = MutableLiveData<List<TrackingModel>>()
    val trackingInProgress: LiveData<List<TrackingModel>> = _trackingInProgress

    private val _trackingCompleted = MutableLiveData<List<TrackingModel>>()
    val trackingCompleted: LiveData<List<TrackingModel>> = _trackingCompleted

    private val _hasError = MutableLiveData(false)
    val hasError: LiveData<Boolean> = _hasError

    private val _trackingAlreadyExists = MutableLiveData(false)
    val trackingAlreadyExists: LiveData<Boolean> = _trackingAlreadyExists

    private val _loader = MutableLiveData(false)
    val loader: LiveData<Boolean> = _loader

    fun getAllTrackingInDataBase() {
        viewModelScope.launch {
            try {
                trackingDaoRepository.getAll().let {
                    _trackingCompleted.postValue(it.getAllTrackingCompleted())
                    _trackingInProgress.postValue(it.getAllTrackingInProgress())
                }
            } catch (ex: Exception) {
                Log.d("###","$ex")
                _hasError.postValue(true)
            }
        }
    }

    fun getTracking(codigo: String, name:String?) {
        //TODO refatorar para remover regras de negócio e passar para o usecase
        _loader.postValue(true)
        viewModelScope.launch {
            try {
                if(!containsTracking(codigo)) {
                    getTrackingUseCase.getTracking(codigo)?.let {
                        insertNewTracking(it, name)
                    } ?: run {
                        _hasError.postValue(true)
                    }
                } else {
                    _trackingAlreadyExists.postValue(true)
                }
                _loader.postValue(false)
            } catch (ex:Exception) {
                Log.d("###","$ex")
                _hasError.postValue(true)
                _loader.postValue(false)
            }
        }
    }

    fun reload() {
        _loader.postValue(true)
        viewModelScope.launch {
            try {
                if(reloadAllTrackingUseCase.reload()) {
                    getAllTrackingInDataBase()
                }
                _loader.postValue(false)
            } catch (ex: Exception) {
                Log.d("###","$ex")
                _loader.postValue(false)
                _hasError.postValue(true)
            }
        }
    }

    fun insertNewTracking(trackingModel: TrackingModel, name: String?) {
        viewModelScope.launch {
            trackingModel.name = name ?: ""
            trackingDaoRepository.insert(trackingModel)
            getAllTrackingInDataBase()
        }
    }

    private suspend fun containsTracking(codigo: String): Boolean =
        trackingDaoRepository.contains(codigo)
}

