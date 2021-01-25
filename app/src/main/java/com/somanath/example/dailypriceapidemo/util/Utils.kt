package com.somanath.example.dailypriceapidemo.util

import java.io.FilterReader

class Utils {
    companion object {
        const val API_KEY = "579b464db66ec23bdd000001388d511a07bc449653533afbf2f600e0"
        const val BASE_URL = "https://api.data.gov.in/"
        const val QUERY_PARAM_API_KEY = "api-key"
        const val QUERY_PARAM_FILTERS_STATE = "filters[state]"
        const val QUERY_PARAM_FILTERS_DISTRICT = "filters[district]"
        const val QUERY_PARAM_FILTERS_MARKET = "filters[market]"
        const val QUERY_PARAM_FILTERS_MIN_PRICE = "filters[min_price]"
        const val QUERY_PARAM_FILTERS_MAX_PRICE = "filters[max_price]"
        const val QUERY_PARAM_FILTERS_MODAL_PRICE = "filters[modal_price]"
        const val QUERY_PARAM_OFFSET = "offset"
        const val QUERY_PARAM_LIMIT = "limit"
        const val QUERY_VALUE_PRICE = "price"
        const val QUERY_VALUE_STATE = "state"
        const val QUERY_VALUE_DISTRICT = "district"
        const val QUERY_VALUE_VILLAGE = "market"
        const val QUERY_VALUE_DATE = "date"
        const val FilterReader_PARAM_ID_PRE_FIX = "OrderedMap%20%7B%20%22"
        const val FilterReader_PARAM_ID_POST_FIX = "%22%3A%20"
        const val ITEM_LIMIT_PER_REQUEST = 10
        const val QUERY_PARAM_FORMAT = "format"
        const val QUERY_VALUE_JSON = "json"
        const val OFFSET_START_INDEX = 1
        const val VALUES_TO_GET = "resource/9ef84268-d588-465a-a308-a864a43d0070"

        val queryMapState = HashMap<String,HashMap<String,String>>().also {
            it.put("state",HashMap<String, String>().also {
                it.put("state","Andhra pradesh")
            })
        }
    }
}