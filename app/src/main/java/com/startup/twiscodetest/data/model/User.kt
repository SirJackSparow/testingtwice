package com.startup.twiscodetest.data.model

data class User(
    val added_date: String,
    val apple_id: String,
    val apple_verify: String,
    val city: String,
    val code: String,
    val device_token: String,
    val email_verify: String,
    val facebook_id: String,
    val facebook_verify: String,
    val follower_count: String,
    val following_count: String,
    val google_id: String,
    val google_verify: String,
    val is_banned: String,
    val is_followed: String,
    val messenger: String,
    val overall_rating: String,
    val phone_id: String,
    val phone_verify: String,
    val rating_count: String,
    val rating_details: RatingDetails,
    val role_id: String,
    val status: String,
    val user_about_me: String,
    val user_address: String,
    val user_cover_photo: String,
    val user_email: String,
    val user_id: String,
    val user_is_sys_admin: String,
    val user_name: String,
    val user_phone: String,
    val user_profile_photo: String,
    val whatsapp: String
)