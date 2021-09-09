package com.iteach.taxi.fragment.register.base

import com.google.gson.annotations.SerializedName

class SendCode (
    @field:SerializedName("phone")
    val phone: String? = null,
    @field:SerializedName("code")
    val code: String? = null
)