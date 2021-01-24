package com.somanath.example.dailypriceapidemo.paging

import androidx.paging.PagingSource
import com.somanath.example.dailypriceapidemo.api.PriceDetailsAPI
import com.somanath.example.dailypriceapidemo.data.Record
import com.somanath.example.dailypriceapidemo.util.Utils
import retrofit2.HttpException
import java.io.IOException

class PriceDetailsPagingSource(private val api: PriceDetailsAPI,private val filter : String) :
        PagingSource<Int,
        Record>(){
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Record> {
        val position = params.key ?: Utils.OFFSET_START_INDEX

        return try {
            val response = api.getInitialData(Utils.API_KEY,Utils.QUERY_VALUE_JSON,Utils
                    .ITEM_LIMIT_PER_REQUEST,position,filter)
            val records = response.records

            LoadResult.Page(
                    data = records,
                    prevKey = if (position == Utils.OFFSET_START_INDEX) null else position - 1,
                    nextKey = if (records.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}