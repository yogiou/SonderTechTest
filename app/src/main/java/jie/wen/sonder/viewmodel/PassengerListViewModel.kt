package jie.wen.sonder.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jie.wen.sonder.other.Constants.Companion.API_CALL_TIMEOUT
import jie.wen.sonder.model.dto.PassengerListResponseDTO
import jie.wen.sonder.other.Constants.Companion.TIME_OUT_MESSAGE
import jie.wen.sonder.other.Resource
import jie.wen.sonder.repository.PassengerDataRepository
import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

@HiltViewModel
open class PassengerListViewModel @Inject constructor(private val repository: PassengerDataRepository) : ViewModel() {
    var currentPage : AtomicInteger = AtomicInteger(0)

    val passengerListResponseDTOLiveData: MutableLiveData<Resource<PassengerListResponseDTO>> by lazy {
        MutableLiveData<Resource<PassengerListResponseDTO>>()
    }

    override fun onCleared() {
        viewModelScope.takeIf { it.isActive }?.cancel()
        super.onCleared()
    }

    fun makeApiCall() = viewModelScope.launch {
        passengerListResponseDTOLiveData.postValue(Resource.loading(null))

        val result = withTimeoutOrNull(API_CALL_TIMEOUT) {
            fetchPassengerListData(currentPage.get())
        }

        if (result == null) {
            passengerListResponseDTOLiveData.postValue(Resource.error(TIME_OUT_MESSAGE, null))
        }
    }

    suspend fun fetchPassengerListData(size: Int) {
        repository.fetchPassengerListData(size).let {
            if (it.isSuccessful) {
                passengerListResponseDTOLiveData.postValue(Resource.success(it.body()))
                currentPage.set(currentPage.get() + 1)
            } else {
                passengerListResponseDTOLiveData.postValue(Resource.error(it.errorBody().toString(), null))
            }
        }
    }
}