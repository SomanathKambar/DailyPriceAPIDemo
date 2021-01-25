package com.somanath.example.dailypriceapidemo.database

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxRemoteMediator
import com.somanath.example.dailypriceapidemo.api.PriceDetailsAPI
import com.somanath.example.dailypriceapidemo.data.PriceDetailsResponse
import com.somanath.example.dailypriceapidemo.data.RecordRemoteKeys
import com.somanath.example.dailypriceapidemo.data.RecordToDB
import com.somanath.example.dailypriceapidemo.util.Utils
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.io.InvalidObjectException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OptIn(ExperimentalPagingApi::class)
class GetInitialDataRxRemoteMediator @Inject constructor(
    private val api: PriceDetailsAPI,
    private val database: PriceDetailsDatabase
) : RxRemoteMediator<Int, RecordToDB>() {

    private var counter = 0
    var internalCount = 0L
    override fun loadSingle(
        loadType: LoadType,
        state: PagingState<Int, RecordToDB>
    ): Single<MediatorResult> {
        return Single.just(loadType)
            .subscribeOn(Schedulers.io())
            .map {
                when (it) {
                    LoadType.REFRESH -> {
                        val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                            remoteKeys?.nextKey?.minus(1) ?: 1
                    }
                    LoadType.PREPEND -> {
                        val remoteKeys = getRemoteKeyForFirstItem(state)
                        if(remoteKeys == null) 0 else
                        remoteKeys.prevKey ?: INVALID_PAGE
                    }
                    LoadType.APPEND -> {
                        val remoteKeys = getRemoteKeyForLastItem(state)
                        if(remoteKeys == null) 1 else
                        remoteKeys.nextKey ?: INVALID_PAGE
                    }
                }
            }
            .flatMap { page ->
                if(page == INVALID_PAGE){
                    Single.just(MediatorResult.Success(endOfPaginationReached = true))
                } else {
                api.getInitialData(
                    Utils.API_KEY, Utils.QUERY_VALUE_JSON,
                    Utils
                        .ITEM_LIMIT_PER_REQUEST,
                    page,
                ).subscribeOn(Schedulers.io()).map {
                        insertToDb(it.offSet,loadType,it)
                }.map<MediatorResult> {
                    MediatorResult.Success( false )
                }.onErrorReturn {
                    MediatorResult.Error(it)
                }
            }
            }
    }
    @Suppress("DEPRECATION")
    private fun insertToDb(page: Int, loadType: LoadType,data: PriceDetailsResponse): PriceDetailsResponse {
        database.beginTransaction()

        try {
            if (loadType == LoadType.REFRESH) {
                database.priceDetailsRxDao().clearPriceDetails()
                database.recordRemoteKeyDao().clearRemoteKeys()
            }

            val prevKey = if (page == 1) 0 else page - 1
            val nextKey = if (data.endOfPage) null else page + 1
            counter = page + 1
            val keys = data.records.map {
                RecordRemoteKeys(recordId = internalCount++,prevKey = prevKey, nextKey = nextKey)
            }

            val records = data.records.map {
                RecordToDB(arrivalDate = it.arrivalDate,commodity = it.commodity,district = it.district,market =
                it.market,maxPrice = it.maxPrice,minPrice = it.minPrice,modalPrice = it.modalPrice
                ,state = it.state, timestamp = it.timestamp,variety = it.variety)
            }

            database.recordRemoteKeyDao().insertAll(keys)
            database.priceDetailsRxDao().insertAll(records)
            database.setTransactionSuccessful()

        } finally {
            database.endTransaction()
        }

        return data
    }


    private fun getRemoteKeyForLastItem(state: PagingState<Int, RecordToDB>): RecordRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { repo ->
            database.recordRemoteKeyDao().remoteKeysByRecordId(repo.id)
        }
    }

    private fun getRemoteKeyForFirstItem(state: PagingState<Int, RecordToDB>): RecordRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { record ->
            database.recordRemoteKeyDao().remoteKeysByRecordId(record.id)
        }
    }

    private fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, RecordToDB>): RecordRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.recordRemoteKeyDao().remoteKeysByRecordId(id)
            }
        }
    }

    companion object {
            const val INVALID_PAGE = -1
        }
}