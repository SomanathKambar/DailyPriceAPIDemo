package com.somanath.example.dailypriceapidemo.ui

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.rxjava2.flowable
import com.somanath.example.dailypriceapidemo.api.PriceDetailsAPI
import com.somanath.example.dailypriceapidemo.paging.PriceDetailsPagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PriceDetailsRepository @Inject constructor(private val api: PriceDetailsAPI) {

    fun getInitialData(filter: String) = Pager(
    config = PagingConfig(
    pageSize = 20,
    maxSize = 200,
    enablePlaceholders = false
    ),
    pagingSourceFactory = { PriceDetailsPagingSource(api,filter) }
    ).flowable
}