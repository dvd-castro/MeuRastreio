package br.com.davidcastro.meurastreio.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.davidcastro.meurastreio.R
import br.com.davidcastro.meurastreio.data.model.MessageEnum
import br.com.davidcastro.meurastreio.data.model.RastreioModel
import br.com.davidcastro.meurastreio.data.repository.RastreioRepository
import br.com.davidcastro.meurastreio.helpers.extensions.toRastreioEntity
import br.com.davidcastro.meurastreio.helpers.utils.NetworkUtils
import kotlinx.coroutines.launch

class MainViewModel (private val repository: RastreioRepository, private val context: Context): ViewModel() {

    private var _databaseList = MutableLiveData<MutableList<RastreioModel>>()
    val databaseList: LiveData<MutableList<RastreioModel>>
        get() = _databaseList


    private var _getOfflineResult = MutableLiveData<RastreioModel>()
    val getOfflineResult: LiveData<RastreioModel>
        get() = _getOfflineResult

    private var _deleteIsCompleted = MutableLiveData<Boolean>()
    val deleteOnComplete: LiveData<Boolean>
        get() = _deleteIsCompleted

    private var _message = MutableLiveData<Int>()
    val message : LiveData<Int>
        get() = _message

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

    //Procura um rastreio na api e insere no banco de dados local
    fun findTracking(codigo: String, nome: String) = viewModelScope.launch {
        if(NetworkUtils.hasConnectionActive(context)) {
            _loader.postValue(true)

            try {
                val tracking = repository.findTracking(codigo)

                if (tracking.eventos.isNotEmpty()) {
                    val trackingWithName = RastreioModel(nome, codigo, tracking.eventos)
                    insertTrackingOnDb(trackingWithName)
                } else {
                    _message.postValue(MessageEnum.NOT_FOUND.id)
                }

                _loader.postValue(false)

            } catch (ex: Exception) {
                _loader.postValue(false)
                _message.postValue(MessageEnum.NOT_FOUND.id)

                ex.localizedMessage?.let { localizedMessage ->
                    Log.e("ERROR -> Find Tracking ", localizedMessage)
                }
            }

        } else {
            _message.postValue(MessageEnum.NETWORK_ERROR.id)
        }
    }

    //Procura um rastreio na api e atualiza no banco local
    fun findTrackingUpdate(rastreio: RastreioModel) = viewModelScope.launch {
        if(NetworkUtils.hasConnectionActive(context)) {
            try {
                val tracking = repository.findTracking(rastreio.codigo)

                if (tracking.eventos.isNotEmpty() && rastreio.eventos.count() > tracking.eventos.count()) {
                    updateTrackingOnDb(tracking)
                }
            } catch (ex: Exception) {
                ex.localizedMessage?.let { localizedMessage ->
                    Log.e("ERROR -> Find Tracking ", localizedMessage)
                }
            }
        } else {
            _message.postValue(MessageEnum.NETWORK_ERROR.id)
        }
    }

    //Pega um rastreio armazenado localmente
    fun getTrackingOnDb(codigo: String) = viewModelScope.launch {
        try {
            val tracking = repository.getTracking(codigo)
            _getOfflineResult.postValue(tracking)
        } catch (ex: Exception) {
            ex.localizedMessage?.let { localizedMessage ->
                Log.e("ERROR", localizedMessage)
            }
        }
    }

    fun deleteTrackingOnDb(codigo: String) = viewModelScope.launch {
        try {
            repository.deleteTracking(codigo)
            _deleteIsCompleted.postValue(true)
            getAllTracking()
        } catch (ex: Exception) {
            _deleteIsCompleted.postValue(false)
            _message.postValue(MessageEnum.DELETE_ERROR.id)
            ex.localizedMessage?.let { localizedMessage ->
                Log.e("ERROR", localizedMessage)
            }
        }
    }

    fun updateTrackingOnDb(rastreio: RastreioModel) = viewModelScope.launch {
        try {
            val entity = rastreio.toRastreioEntity()
            repository.updateTracking(entity.codigo, entity.eventos)
            getAllTracking()
        } catch (ex: Exception) {
            ex.localizedMessage?.let { localizedMessage ->
                Log.e("ERROR -> Update Tracking ", localizedMessage)
            }
        }
    }

    fun insertTrackingOnDb(rastreio: RastreioModel) = viewModelScope.launch {
        try {
            repository.insertTracking(rastreio)
            _message.postValue(MessageEnum.INSERTED_WITH_SUCESS.id)
            getAllTracking()
        } catch (ex: Exception) {
            ex.localizedMessage?.let { localizedMessage ->
                Log.e("ERROR -> Insert Tracking ", localizedMessage)
            }
        }
    }

    fun verifyIfTrackingExistsOnDb(codigo: String, nome: String) = viewModelScope.launch {
        try {
            val exists = repository.containsTracking(codigo)

            if(!exists) {
                findTracking(codigo, nome)
            } else {
                _message.postValue(MessageEnum.ALREADY_INSERTED.id)
            }
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
                    if(rastreio.eventos.first().status != context.getString(R.string.status_entregue)) {
                        findTrackingUpdate(rastreio)
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
            _message.postValue(MessageEnum.NETWORK_ERROR.id)
        }
    }

}