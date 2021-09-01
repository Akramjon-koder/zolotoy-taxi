package com.iteach.taxi.fragment.login.base

import com.google.gson.annotations.SerializedName

data class LoginModel(
	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("type_id")
	val typeId: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: Int? = null,

	@field:SerializedName("last_name")
	val lastName: String? = null,

	@field:SerializedName("password_reset_token")
	val passwordResetToken: String? = null,

	@field:SerializedName("auth_key")
	val authKey: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: Int? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("password_hash")
	val passwordHash: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("first_name")
	val firstName: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)
