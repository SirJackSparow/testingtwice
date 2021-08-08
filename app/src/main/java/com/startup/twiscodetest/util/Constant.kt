package com.startup.twiscodetest.util

import java.text.NumberFormat
import java.util.*

val CHECK_CONNECTION = "PLEASE CHECK CONNECTION"
val EMPTY_DATA = "EMPTY DATA"
val TIMEOUT = "Request Timeout"
val IMAGE_URL = "https://ranting.twisdev.com/uploads/"

fun rupiah(number: Double): String{
    val localeID =  Locale("in", "ID")
    val numberFormat = NumberFormat.getCurrencyInstance(localeID)
    return numberFormat.format(number).toString()
}