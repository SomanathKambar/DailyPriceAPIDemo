package com.somanath.example.dailypriceapidemo.ui

import androidx.core.content.ContextCompat
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.rxjava2.flowable
import com.google.android.material.internal.ContextUtils
import com.somanath.example.dailypriceapidemo.api.PriceDetailsAPI
import com.somanath.example.dailypriceapidemo.database.GetInitialDataRxRemoteMediator
import com.somanath.example.dailypriceapidemo.database.PriceDetailsDatabase
import com.somanath.example.dailypriceapidemo.paging.PriceDetailsPagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PriceDetailsRepository @Inject constructor(private val remoteMediator: GetInitialDataRxRemoteMediator,
                                                 private val database: PriceDetailsDatabase) {


    @OptIn(ExperimentalPagingApi::class)
    fun getInitialData(filter: String) = Pager(
    config = PagingConfig(
    pageSize = 20,
    maxSize = 200,
        initialLoadSize = 40,
        prefetchDistance = 5,
    enablePlaceholders = true
    ),
    remoteMediator = remoteMediator,
    pagingSourceFactory = { database.priceDetailsRxDao().selectAll() }
    ).flowable
}