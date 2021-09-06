package com.iteach.taxi.fragment.signup.base

import com.google.gson.annotations.SerializedName

data class Send_Register_Model (
 @field:SerializedName("phone")
    val phone: String? = null,
 @field:SerializedName("first_name")
    val first_name: String? = null,
 @field:SerializedName("last_name")
    val last_name: String? = null,
 @field:SerializedName("password")
    val password: String? = null,
    )