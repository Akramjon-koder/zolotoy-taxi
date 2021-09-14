package com.iteach.taxi.model

import com.google.gson.annotations.SerializedName

data class GetTarifModel(

	@field:SerializedName("updated_at")
	val updatedAt: Int? = null,

	@field:SerializedName("minimum_sum")
	val minimumSum: Int? = null,

	@field:SerializedName("outra_city_km_sum")
	val outraCityKmSum: Int? = null,

	@field:SerializedName("updated_by")
	val updatedBy: Int? = null,

	@field:SerializedName("minimum_km")
	val minimumKm: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("km_sum")
	val kmSum: Int? = null,

	@field:SerializedName("created_by")
	val createdBy: Int? = null,

	@field:SerializedName("status")
	val status: Int? = null
)
