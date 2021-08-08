package com.startup.twiscodetest.data.server

import com.startup.twiscodetest.data.model.ModelData
import com.startup.twiscodetest.data.model.ModelDataItem
import retrofit2.Response
import retrofit2.http.POST

interface NetworkService {
    @POST("rest/items/search/api_key/teampsisthebest/")
    suspend fun getData() : Response<List<ModelDataItem>>
}