package br.com.davidcastro.meurastreio.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.davidcastro.meurastreio.data.model.RastreioModel
import br.com.davidcastro.meurastreio.data.repository.RastreioRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: RastreioRepository
): ViewModel() {

    fun getAllTracking() = viewModelScope.launch {
        try {
            val all = repository.getAllTracking()
        } catch (ex: Exception) {
            ex.localizedMessage?.let { localizedMessage ->
                Log.e("ERROR", localizedMessage)
            }
        }
    }

    //Pega um rastreio armazenado localmente se ele existir
    fun getTracking(codigo: String) = viewModelScope.launch {
        try {
            val tracking = repository.getTracking(codigo = codigo)
        } catch (ex: Exception) {
            ex.localizedMessage?.let { localizedMessage ->
                Log.e("ERROR", localizedMessage)
            }
        }
    }

    //Procura um rastreio na api
    fun findTracking(codigo: String) = viewModelScope.launch {
        try {
            val tracking = repository.findTracking(codigo = codigo)
        } catch (ex: Exception) {
            ex.localizedMessage?.let { localizedMessage ->
                Log.e("ERROR", localizedMessage)
            }
        }
    }

    fun deleteTracking(codigo: String) = viewModelScope.launch {
        try {
            repository.deleteTracking(codigo = codigo)
        } catch (ex: Exception) {
            ex.localizedMessage?.let { localizedMessage ->
                Log.e("ERROR", localizedMessage)
            }
        }
    }

    fun insertTracking(rastreio: RastreioModel) = viewModelScope.launch {
        try {
            repository.insertTracking(rastreio = rastreio)
        } catch (ex: Exception) {
            ex.localizedMessage?.let { localizedMessage ->
                Log.e("ERROR", localizedMessage)
            }
        }
    }

}