package com.iteach.taxi.fragment.mapfragment.base

import com.google.gson.annotations.SerializedName

class EndDefinitionModel (
    val order_id: Int? = null,
    val end_lat: Double? = null,
    val end_long: Double? = null,
    val distance: Double? = null,
    val total_amount: Int? = null
)