package com.startup.twiscodetest.view.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.startup.twiscodetest.data.localdb.dao.KeranjangDao
import com.startup.twiscodetest.data.model.KeranjangModel
import com.startup.twiscodetest.data.model.ModelDataItem
import com.startup.twiscodetest.util.CheckingNetwork
import com.startup.twiscodetest.repository.MainRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class MainViewModel(val data : MainRepo,val cart:KeranjangDao) : ViewModel() {
    val datax = MutableLiveData<List<ModelDataItem>>()
    suspend fun getData() = withContext(Dispatchers.IO) {
       datax.postValue(data.getData())
    }
    fun checkConnection(context: Context): Boolean {
        return CheckingNetwork.isOnline(context)
    }

    suspend fun getSize() : Int = cart.getDataKeranjang().size
    suspend fun getDataKeranjang() : MutableList<KeranjangModel> = cart.getDataKeranjang()

    suspend fun addKeranjang(modelDataItem: ModelDataItem) {
        val currentTime: Date = Calendar.getInstance().getTime()
        cart.AddKeranjang(KeranjangModel(
            modelDataItem.id!!,
            modelDataItem.title!!,
            modelDataItem.price!!,
            modelDataItem.default_photo!!.img_path,
            modelDataItem.touch_count!!,
            currentTime
        ))
    }
}