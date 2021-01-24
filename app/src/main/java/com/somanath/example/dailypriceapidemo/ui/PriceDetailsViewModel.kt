package com.somanath.example.dailypriceapidemo.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.somanath.example.dailypriceapidemo.api.Resource
import com.somanath.example.dailypriceapidemo.data.Record
import com.somanath.example.dailypriceapidemo.util.Utils

class PriceDetailsViewModel @ViewModelInject constructor(
    private val repository: PriceDetailsRepository) : ViewModel() {
    val filteredData = MutableLiveData(INITIAL_FILTER)

    fun getInitialData() = liveData<Resource<PagingData<Record>?>> {
        try {
            emit(Resource.success(data = repository.getInitialData(INITIAL_FILTER).cachedIn(viewModelScope)
                    .value))
        } catch (exception: Exception) {
            exception.printStackTrace()
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }

    }

    var items = filteredData.switchMap { filter ->
            repository.getInitialData(filter).cachedIn(viewModelScope)
    }

    companion object{
        const val INITIAL_FILTER = Utils.QUERY_VALUE_STATE
    }
}