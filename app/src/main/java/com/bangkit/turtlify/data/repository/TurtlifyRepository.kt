package com.bangkit.turtlify.data.repository

import android.util.Log
import com.bangkit.turtlify.data.network.api.ApiConfig
import com.bangkit.turtlify.data.network.model.ImageUploadResponse
import com.bangkit.turtlify.data.network.model.TurtleResponseItem
import com.bangkit.turtlify.ui.report.FormData
import com.bangkit.turtlify.ui.viemodels.Contact
import com.bangkit.turtlify.ui.viemodels.Response
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File

class TurtlifyRepository {

    private val apiService = ApiConfig().getApiService()
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

    fun getContacts() : List<Contact>{
        val contacts: List<Contact> = try {
            listOf(
                Contact("BKSDA Jakarta Utara", "john@example.com"),
                Contact("BKSDA Jakarta Selatan", "alice@example.com")
            )
        } catch (e: Exception) {
            Log.d("ERRORNETWORKREPO", e.message.toString())
            listOf() // Return an empty list if an exception occurs
        }

        return contacts
    }

    suspend fun getTurtles(): List<TurtleResponseItem?>? {
        return try {
            apiService.getTurtles()
        } catch (e: HttpException) {
            Log.e("ERRORNETWORKREPO", e.message.toString())
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