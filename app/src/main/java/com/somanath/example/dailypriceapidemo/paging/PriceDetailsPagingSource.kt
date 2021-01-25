package com.somanath.example.dailypriceapidemo.paging

import androidx.paging.rxjava2.RxPagingSource
import com.somanath.example.dailypriceapidemo.api.PriceDetailsAPI
import com.somanath.example.dailypriceapidemo.data.PriceDetailsResponse
import com.somanath.example.dailypriceapidemo.data.Record
import com.somanath.example.dailypriceapidemo.util.Utils
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class PriceDetailsPagingSource(private val api: PriceDetailsAPI,private val filter : String) :
        RxPagingSource<Int,
        Record>(){

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Record>> {
        val position = params.key ?: Utils.OFFSET_START_INDEX

        return api.getInitialData(
            Utils.API_KEY, Utils.QUERY_VALUE_JSON, Utils
                .ITEM_LIMIT_PER_REQUEST, position/*, filter*/
        ).subscribeOn(Schedulers.io()).map {
            toLoadResult(it, position)
        }.onErrorReturn { LoadResult.Error(it) }

    }

    private fun toLoadResult(data: PriceDetailsResponse, position: Int): LoadResult<Int, Record> {
        return LoadResult.Page(
            data = data.records,
            prevKey = if (position == Utils.OFFSET_START_INDEX) null else position - 1,
            nextKey = if (data.records.isEmpty()) null else position + 1
        )
    }
}