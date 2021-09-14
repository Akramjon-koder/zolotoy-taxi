package com.iteach.taxi.fragment.mapfragment.base

import com.google.gson.annotations.SerializedName

class DefinitionModel (
    @field:SerializedName("order_id")
    val order_id: Int? = null,

    @field:SerializedName("begin_lat")
    val begin_lat: Double? = null,
    
    @field:SerializedName("begin_long")
    val begin_long: Double? = null
)