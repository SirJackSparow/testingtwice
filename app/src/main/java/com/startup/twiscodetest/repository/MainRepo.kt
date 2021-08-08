package com.startup.twiscodetest.repository

import com.startup.twiscodetest.data.model.ModelData
import com.startup.twiscodetest.data.model.ModelDataItem

interface MainRepo {
    suspend fun getData() : List<ModelDataItem>?
}