package br.com.davidcastro.meurastreio.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.davidcastro.meurastreio.data.model.RastreioModel
import br.com.davidcastro.meurastreio.data.repository.RastreioRepository
import kotlinx.coroutines.launch

class DetalhesViewModel (private val repository: RastreioRepository) : ViewModel() {

    private var _getResult = MutableLiveData<RastreioModel>()
    val getResult : LiveData<RastreioModel>
        get() = _getResult

    //Pega um rastreio armazenado localmente
    fun getTracking(codigo: String) = viewModelScope.launch {
        try {
            val tracking = repository.getTracking(codigo)
            _getResult.postValue(tracking)
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

}