package br.com.davidcastro.meurastreio.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.davidcastro.meurastreio.BaseApplication
import br.com.davidcastro.meurastreio.R
import br.com.davidcastro.meurastreio.data.model.ErrorEnum
import br.com.davidcastro.meurastreio.data.model.RastreioModel
import br.com.davidcastro.meurastreio.data.repository.RastreioRepository
import br.com.davidcastro.meurastreio.helpers.utils.NetworkUtils
import br.com.davidcastro.meurastreio.helpers.utils.showSnackbar
import kotlinx.coroutines.launch
import java.lang.Error

class MainViewModel (private val repository: RastreioRepository, private val context: Context): ViewModel() {

    private var _databaseList = MutableLiveData<MutableList<RastreioModel>>()
    val databaseList: LiveData<MutableList<RastreioModel>>
        get() = _databaseList

    private var _findResult = MutableLiveData<RastreioModel>()
    val findResult: LiveData<RastreioModel>
        get() = _findResult

    private var _ifTrackingExists = MutableLiveData<Boolean>()
    val ifTrackingExists: LiveData<Boolean>
        get() = _ifTrackingExists

    private var _insertSucess = MutableLiveData<Boolean>()
    val insertSucess: LiveData<Boolean>
        get() = _insertSucess

    private var _getOfflineResult = MutableLiveData<RastreioModel>()
    val getOfflineResult: LiveData<RastreioModel>
        get() = _getOfflineResult

    private var _deleteIsCompleted = MutableLiveData<Boolean>()
    val deleteOnComplete: LiveData<Boolean>
        get() = _deleteIsCompleted

    private var _error = MutableLiveData<Int>()
    val error : LiveData<Int>
        get() = _error

    private var _loader= MutableLiveData(false)
    val loader : LiveData<Boolean>
        get() = _loader


    fun getAllTracking() = viewModelScope.launch {
        try {
            val all = repository.getAllTracking()
            _databaseList.postValue(all.toMutableList())
        } catch (ex: Exception) {
            ex.localizedMessage?.let { localizedMessage ->
                Log.e("ERROR -> Get All Tracking ", localizedMessage)
            }
        }
    }

    //Procura um rastreio na api
    fun findTracking(codigo: String) = viewModelScope.launch {
        if(NetworkUtils.hasConnectionActive(context)) {
            _loader.postValue(true)
            try {
                val tracking = repository.findTracking(codigo)
                if (tracking.eventos.isNotEmpty()) {
                    _findResult.postValue(tracking)
                } else {
                    _error.postValue(ErrorEnum.ERROR_NAO_ENCONTRADO.id)
                }
                _loader.postValue(false)
            } catch (ex: Exception) {
                _loader.postValue(false)
                _error.postValue(ErrorEnum.ERROR_NAO_ENCONTRADO.id)
                ex.localizedMessage?.let { localizedMessage ->
                    Log.e("ERROR -> Find Tracking ", localizedMessage)
                }
            }
        } else {
            _error.postValue(ErrorEnum.ERROR_NETWORK.id)
        }
    }

    //Pega um rastreio armazenado localmente
    fun getTracking(codigo: String) = viewModelScope.launch {
        try {
            val tracking = repository.getTracking(codigo)
            _getOfflineResult.postValue(tracking)
        } catch (ex: Exception) {
            ex.localizedMessage?.let { localizedMessage ->
                Log.e("ERROR", localizedMessage)
            }
        }
    }

    fun deleteTracking(codigo: String) = viewModelScope.launch {
        try {
            repository.deleteTracking(codigo)
        } catch (ex: Exception) {
            ex.localizedMessage?.let { localizedMessage ->
                Log.e("ERROR", localizedMessage)
            }
        }
    }

    fun insertTracking(rastreio: RastreioModel) = viewModelScope.launch {
        try {
            repository.insertTracking(rastreio)
            _insertSucess.postValue(true)
        } catch (ex: Exception) {
            ex.localizedMessage?.let { localizedMessage ->
                Log.e("ERROR -> Insert Tracking ", localizedMessage)
            }
        }
    }

    fun verifyIfTrackingExists(codigo: String) = viewModelScope.launch {
        try {
            val exists = repository.containsTracking(codigo)
            _ifTrackingExists.postValue(exists)
        } catch (ex: Exception) {
            ex.localizedMessage?.let { localizedMessage ->
                Log.e("ERROR -> Verify If Tracking Exists ", localizedMessage)
            }
        }
    }

    fun reload() = viewModelScope.launch {
        if(NetworkUtils.hasConnectionActive(context)) {
            _loader.postValue(true)
            try {
                val all = repository.getAllTracking()
                all.forEach { rastreio ->
                    if(rastreio.eventos.first().status != context.getString(R.string.status_entregue)){
                        findTracking(rastreio.codigo)
                    }
                }
                _loader.postValue(false)
            } catch (ex: Exception) {
                _loader.postValue(false)
                ex.localizedMessage?.let { localizedMessage ->
                    Log.e("ERROR -> Reload ", localizedMessage)
                }
            }
        } else {
            _error.postValue(ErrorEnum.ERROR_NETWORK.id)
        }
    }

}