package com.bangkit.turtlify.data.model.search

import com.google.gson.annotations.SerializedName

data class SearchResponse(

	@field:SerializedName("SearchResponse")
	val searchResponse: List<SearchResponseItem?>? = null
)

data class SearchResponseItem(

	@field:SerializedName("nama_lokal")
	val namaLokal: String? = null,

	@field:SerializedName("Persebaran_Habitat")
	val persebaranHabitat: String? = null,

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("habitat")
	val habitat: String? = null,

	@field:SerializedName("nama_latin")
	val namaLatin: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("Latitude")
	val latitude: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("Longitude")
	val longitude: String? = null,

	@field:SerializedName("status_konversi")
	val statusKonversi: String? = null
)
