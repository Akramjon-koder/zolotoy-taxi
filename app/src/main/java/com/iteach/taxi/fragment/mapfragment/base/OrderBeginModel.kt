package com.iteach.taxi.fragment.mapfragment.base

import com.google.gson.annotations.SerializedName

class OrderBeginModel (
    @field:SerializedName("id")
    val id: Int? = null,
    @field:SerializedName("minimum_km")
    val minimum_km: Int? = null,
    @field:SerializedName("minimum_sum")
    val minimum_sum: Int? = null,
    @field:SerializedName("km_sum")
    val km_sum: Int? = null,
    @field:SerializedName("status")
    val status: Int? = null,

    @field:SerializedName("outra_city_km_sum")
    val outra_city_km_sum: Int? = null,
    @field:SerializedName("created_by")
    val created_by: Int? = null,
    @field:SerializedName("updated_by")
    val updated_by: Int? = null,
    @field:SerializedName("created_at")
    val created_at: Int? = null,

    @field:SerializedName("updated_at")
    val updated_at: Int? = null
)