package com.startup.twiscodetest.util

import androidx.room.TypeConverter
import java.util.*

class DataConverter {
    @TypeConverter
    fun toDate(dateLong: Long?): Date? {
        return dateLong?.let { Date(it) }
    }
    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }
}