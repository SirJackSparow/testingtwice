package com.startup.twiscodetest.data.localdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.startup.twiscodetest.data.model.KeranjangModel


@Dao
interface KeranjangDao {
    @Query("select * from KeranjangModel")
    suspend fun getDataKeranjang() : MutableList<KeranjangModel>

    @Query("delete from KeranjangModel")
    suspend fun deleteAll()

    @Insert
    suspend fun AddKeranjang(model : KeranjangModel)
}