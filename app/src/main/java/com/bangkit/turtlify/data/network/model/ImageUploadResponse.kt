package com.bangkit.turtlify.data.network.model

import com.google.gson.annotations.SerializedName

data class ImageUploadResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)
