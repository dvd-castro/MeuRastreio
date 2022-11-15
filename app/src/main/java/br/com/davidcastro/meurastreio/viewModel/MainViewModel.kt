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

    fun getAllTrackingInDataBase() {
        viewModelScope.launch {
            processTrackingInDataBaseToView(trackingDaoRepository.getAll())
        }
    }

    private fun processTrackingInDataBaseToView(all: TrackingList) {
        if(all.isNotEmpty()) {
            if(all.getAllTrackingCompleted().isNotEmpty())
                _trackingCompleted.postValue(all.getAllTrackingCompleted())

            if(all.getAllTrackingInProgress().isNotEmpty())
                _trackingInProgress.postValue(all.getAllTrackingInProgress())
        }
    }

    fun getTracking(codigo: String, name:String?) {
        //TODO refatorar para remover regras de negócio e passar para o usecase
        viewModelScope.launch {
            try {
                if(!containsTracking(codigo)) {
                    getTrackingUseCase.getTracking(codigo)?.let {
                        if(it.events.isNotEmpty()) {
                            insertNewTracking(it, name)
                            getAllTrackingInDataBase()
                        } else {
                            _hasError.postValue(true)
                        }
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

    fun reload() {
        viewModelScope.launch {
            try {
                if(reloadAllTrackingUseCase.reload()) {
                    getAllTrackingInDataBase()
                }
            } catch (ex: Exception){
                Log.d("###","$ex")
                _hasError.postValue(true)
            }
        }
    }

    private suspend fun insertNewTracking(trackingModel: TrackingModel, name: String?) {
        trackingDaoRepository.insert(trackingModel.apply {
            this.name = name?: ""
        })
    }

    suspend fun containsTracking(codigo: String): Boolean =
        trackingDaoRepository.contains(codigo)
}

