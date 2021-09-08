package com.iteach.taxi.fragment.addcab.base

import com.google.gson.annotations.SerializedName

data class CarTypeModel(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("status")
	val status: Int? = null
)
