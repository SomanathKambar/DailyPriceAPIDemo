package com.somanath.example.dailypriceapidemo.api

import com.somanath.example.dailypriceapidemo.data.PriceDetailsResponse
import com.somanath.example.dailypriceapidemo.util.Utils
import retrofit2.http.GET
import retrofit2.http.Query

interface PriceDetailsAPI {

    @GET(Utils.VALUES_TO_GET)
   suspend fun getInitialData(
//        @QueryMap option : Map<String,Map<String,String>>,
        @Query(Utils.QUERY_PARAM_API_KEY) apiKey : String,
        @Query(Utils.QUERY_PARAM_FORMAT) format: String,
        @Query(Utils.QUERY_PARAM_LIMIT) limit: Int,
        @Query(Utils.QUERY_PARAM_OFFSET) offset: Int,
        @Query(Utils.QUERY_PARAM_FILTERS) filters : String
    ) : PriceDetailsResponse
}