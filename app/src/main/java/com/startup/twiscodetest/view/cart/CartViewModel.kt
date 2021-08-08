package com.startup.twiscodetest.view.cart

import androidx.lifecycle.ViewModel
import com.startup.twiscodetest.data.localdb.dao.KeranjangDao
import com.startup.twiscodetest.data.model.KeranjangModel

class CartViewModel(val cart : KeranjangDao) : ViewModel(){

    suspend fun getKeranjang() : MutableList<KeranjangModel>{
        return cart.getDataKeranjang()
    }

    suspend fun deleteAll(){
        cart.deleteAll()
    }
}