package com.startup.twiscodetest.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class KeranjangModel (
    val ids : String,
    val title : String,
    val price : String,
    val image_url :  String,
    val touch_count : String,
    val date_transaction : Date){
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}