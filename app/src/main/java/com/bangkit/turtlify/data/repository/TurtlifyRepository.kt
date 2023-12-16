package com.bangkit.turtlify.data.repository

import com.bangkit.turtlify.data.network.api.ApiConfig
import com.bangkit.turtlify.data.network.model.ImageUploadResponse
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File

class TurtlifyRepository {
    suspend fun uploadImage(
        imageFile: File,
        onSuccess: (ImageUploadResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "file",
            imageFile.name,
            requestImageFile
        )

        try {
            val apiService = ApiConfig().getApiService()
            val successResponse = apiService.uploadImage(multipartBody)
            successResponse.message?.let { onSuccess(successResponse) }
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ImageUploadResponse::class.java)
            errorResponse.message?.let { onError(it) }
        }
    }
}