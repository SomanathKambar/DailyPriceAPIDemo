package com.somanath.example.dailypriceapidemo.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.filter
import androidx.paging.rxjava2.cachedIn
import com.somanath.example.dailypriceapidemo.util.Utils

class PriceDetailsViewModel @ViewModelInject constructor(
    private val repository: PriceDetailsRepository
) : ViewModel() {
    val filteredData = MutableLiveData(INITIAL_FILTER)

    @ExperimentalPagingApi
    fun getPriceDetailsItems() = repository.getInitialData(INITIAL_FILTER).map { pagingData ->
        pagingData.filter { it.state.isNotEmpty() }
    }.cachedIn(viewModelScope)

    companion object {
        const val INITIAL_FILTER = Utils.QUERY_VALUE_STATE
    }
}