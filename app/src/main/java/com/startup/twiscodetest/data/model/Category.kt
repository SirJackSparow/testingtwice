package com.startup.twiscodetest.data.model

data class Category(
    val added_date: String,
    val cat_id: String,
    val cat_name: String,
    val cat_ordering: String,
    val default_icon: DefaultIcon,
    val default_photo: DefaultPhoto,
    val is_food: String,
    val status: String
)