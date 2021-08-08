package com.startup.twiscodetest.repository

import com.startup.twiscodetest.data.server.NetworkService
import com.startup.twiscodetest.data.model.ModelDataItem
import java.net.SocketTimeoutException

class MainRepoImpl (private val network : NetworkService) : MainRepo {
    override suspend fun getData(): List<ModelDataItem>? {
        return try {
             network.getData().body()
        }catch (e :SocketTimeoutException){
             null
        }
    }
}