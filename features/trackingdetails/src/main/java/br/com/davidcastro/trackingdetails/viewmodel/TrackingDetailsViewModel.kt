package br.com.davidcastro.trackingdetails.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.davidcastro.data.usecase.db.DeleteTrackingInDbUseCase
import kotlinx.coroutines.launch

class TrackingDetailsViewModel(private val deleteTrackingInDbUseCase: DeleteTrackingInDbUseCase): ViewModel() {

    private var _hasDeleted = MutableLiveData(false)
    var hasDeleted: LiveData<Boolean> = _hasDeleted

    fun delete(code: String) {
        viewModelScope.launch {
            try {
                deleteTrackingInDbUseCase.deleteTracking(code)
                _hasDeleted.postValue(true)
            } catch (ex: Exception) {
                Log.d("###","$ex")
            }
        }
    }
}