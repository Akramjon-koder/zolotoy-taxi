package com.iteach.taxi.model

data class BaseResponse<T> (
    val success :Boolean,
    val message :String,
    val data :T)