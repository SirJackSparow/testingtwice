package com.startup.twiscodetest.data.localdb

import android.content.Context
import androidx.room.*
import com.startup.twiscodetest.data.localdb.dao.KeranjangDao
import com.startup.twiscodetest.data.model.KeranjangModel
import com.startup.twiscodetest.util.DataConverter

@Database(entities = [KeranjangModel::class], version = 2, exportSchema = false)
@TypeConverters(DataConverter::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun keranjang(): KeranjangDao

    companion object {
        private var INSTANCE: AppDataBase? = null
        fun getInstance(context: Context): AppDataBase? {
            if (INSTANCE == null) {
                synchronized(AppDataBase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        AppDataBase::class.java, "test_db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}