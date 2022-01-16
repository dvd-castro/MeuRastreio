package br.com.davidcastro.meurastreio.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.davidcastro.meurastreio.data.repository.RastreioRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: RastreioRepository
): ViewModel() {

}