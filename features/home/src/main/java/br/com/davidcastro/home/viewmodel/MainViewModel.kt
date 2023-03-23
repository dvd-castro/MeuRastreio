package br.com.davidcastro.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.davidcastro.data.model.TrackingModel
import br.com.davidcastro.data.usecase.db.ContainsTrackingInDbUseCase
import br.com.davidcastro.data.usecase.db.GetAllTrackingsInDbUseCase
import br.com.davidcastro.data.usecase.db.InsertTrackingInDbUseCase
import br.com.davidcastro.data.usecase.remote.GetTrackingUseCase
import br.com.davidcastro.data.usecase.remote.ReloadAllTrackingUseCase
import br.com.davidcastro.data.utils.getAllTrackingCompleted
import br.com.davidcastro.data.utils.getAllTrackingInProgress
import kotlinx.coroutines.launch

class MainViewModel (
    private val getTrackingUseCase: GetTrackingUseCase,
    private val reloadAllTrackingUseCase: ReloadAllTrackingUseCase,
    private val getAllTrackingsInDbUseCase: GetAllTrackingsInDbUseCase,
    private val insertTrackingInDbUseCase: InsertTrackingInDbUseCase,
    private val containsTrackingInDbUseCase: ContainsTrackingInDbUseCase
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
                getAllTrackingsInDbUseCase.getAll().let {
                    _trackingCompleted.postValue(it.getAllTrackingCompleted())
                    _trackingInProgress.postValue(it.getAllTrackingInProgress())
                }
            } catch (ex: Exception) {
                Log.d("###","$ex")
                showError()
            }
        }
    }

    fun getTracking(codigo: String, name:String?) {
        showLoader(true)
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
            } catch (ex:Exception) {
                Log.d("###","$ex")
                showError()

            } finally {
                showLoader(false)
            }
        }
    }

    fun reload() {
        showLoader(true)
        viewModelScope.launch {
            try {
                if(reloadAllTrackingUseCase.reload()) {
                    getAllTrackingInDataBase()
                }
            } catch (ex: Exception) {
                Log.d("###","$ex")
                showError()
            } finally {
               showLoader(false)
            }
        }
    }

    fun insertNewTracking(trackingModel: TrackingModel, name: String?) {
        viewModelScope.launch {
            trackingModel.name = name ?: ""
            insertTrackingInDbUseCase.insert(trackingModel)
            getAllTrackingInDataBase()
        }
    }

    private suspend fun containsTracking(codigo: String): Boolean =
        containsTrackingInDbUseCase.contains(codigo)

    private fun showError() =
        _hasError.postValue(true)

    private fun showLoader(enable: Boolean) =
        _loader.postValue(enable)

}

