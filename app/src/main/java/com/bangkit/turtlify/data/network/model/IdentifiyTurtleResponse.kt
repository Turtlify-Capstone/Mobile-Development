package com.bangkit.turtlify.data.network.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class IdentifiyTurtleResponse(

	@field:SerializedName("additional_data")
	val additionalData: AdditionalData? = null,

	@field:SerializedName("response")
	val response: Int? = null,

	@field:SerializedName("prediction")
	val prediction: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable

@Parcelize
data class AdditionalData(

	@field:SerializedName("Persebaran_Habitat")
	val persebaranHabitat: String? = null,

	@field:SerializedName("nama_lokal")
	val namaLokal: String? = null,

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("habitat")
	val habitat: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("nama_latin")
	val namaLatin: String? = null,

	@field:SerializedName("Latitude")
	val latitude: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("Longitude")
	val longitude: String? = null,

	@field:SerializedName("status_konversi")
	val statusKonversi: String? = null
) : Parcelable
