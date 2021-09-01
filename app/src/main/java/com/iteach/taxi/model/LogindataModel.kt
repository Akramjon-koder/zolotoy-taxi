package com.iteach.taxi.model

data class LogindataModel (
    val id :Int,
    val username :String,
    val auth_key :String,
    val password_hash :String,
    val password_reset_token :String,
    val email :String,
    val status :Int,
    val created_at :Int,
    val updated_at :Int,
    val first_name :String,
    val last_name :String,
    val phone :Int,
    val type_id :Int)