package br.com.davidcastro.meurastreio.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.davidcastro.meurastreio.R
import br.com.davidcastro.meurastreio.data.model.RastreioModel
import br.com.davidcastro.meurastreio.data.repository.RastreioRepository
import kotlinx.coroutines.launch

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
        try {
            val tracking = repository.findTracking(codigo)
            _findResult.postValue(tracking)
        } catch (ex: Exception) {
            ex.localizedMessage?.let { localizedMessage ->
                Log.e("ERROR -> Find Tracking ", localizedMessage)
            }
        }
    }

    fun insertTracking(rastreio: RastreioModel) = viewModelScope.launch {
        try {
            repository.insertTracking(rastreio)
            _insertSucess.postValue(true)
        } catch (ex: Exception) {
            _insertSucess.postValue(false)
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

    fun reload() = viewModelScope.launch{
        try {
            val all = repository.getAllTracking()
            all.forEach { rastreio ->
                if(rastreio.eventos.first().status != context.getString(R.string.status_entregue)){
                    findTracking(rastreio.codigo)
                }
            }

        } catch (ex: Exception) {
            ex.localizedMessage?.let { localizedMessage ->
                Log.e("ERROR -> Reload ", localizedMessage)
            }
        }
    }

}