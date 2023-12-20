package com.bangkit.turtlify.data.repository

import android.util.Log
import com.bangkit.turtlify.data.model.contact.Contact
import com.bangkit.turtlify.data.model.report.FormData
import com.bangkit.turtlify.data.model.report.Report
import com.bangkit.turtlify.data.network.api.ApiConfig
import com.bangkit.turtlify.data.network.model.FetchTurtlesResponseItem
import com.bangkit.turtlify.data.network.model.ImageUploadResponse
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import java.io.File

class TurtlifyRepository {

    private val apiService = ApiConfig.getApiService()
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
            val successResponse = apiService.uploadImage(multipartBody)
            successResponse.message?.let { onSuccess(successResponse) }
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ImageUploadResponse::class.java)
            errorResponse.message?.let { onError(it) }
        }
    }

    suspend fun fetchTurtles(
        onSuccess: (List<FetchTurtlesResponseItem>) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val response = apiService.fetchTurtles()
            onSuccess(response)
        } catch (e: Exception) {
            onError(e.message ?: "Unknown error occurred")
        }
    }

    suspend fun submitReportForm(
        formData: FormData,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        Log.d("FORMDATA", formData.toString())
        try {
            //TODO fill submit form fun param with form data
//            val successResponse = apiService.submitReportForm()
//            successResponse.message?.let { onSuccess(successResponse) }
            onSuccess("Report form submitted")
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ImageUploadResponse::class.java)
            errorResponse.message?.let { onError(it) }
        }
    }
}