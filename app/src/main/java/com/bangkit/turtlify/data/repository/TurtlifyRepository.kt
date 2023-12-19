package com.bangkit.turtlify.data.repository

import android.util.Log
import com.bangkit.turtlify.data.model.report.Report
import com.bangkit.turtlify.data.network.api.ApiConfig
import com.bangkit.turtlify.data.network.model.FetchTurtlesResponseItem
import com.bangkit.turtlify.data.network.model.ImageUploadResponse
import com.bangkit.turtlify.ui.report.FormData
import com.bangkit.turtlify.ui.viemodels.Contact
import com.bangkit.turtlify.ui.viemodels.Turtle
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

    fun getContacts() : List<Contact>{
        val contacts: List<Contact> = try {
            listOf(
                Contact("BKSDA Jakarta Utara", "john@example.com"),
                Contact("BKSDA Jakarta Selatan", "alice@example.com")
            )
        } catch (e: Exception) {
            Log.d("ERRORNETWORKREPO", e.message.toString())
            listOf()
        }

        return contacts
    }

    fun getTurtles(): List<Turtle>{
        val turtles: List<Turtle> = try {
            listOf(
                Turtle("Moncong babi", "https://www.balisafarimarinepark.com/wp-content/uploads/2022/02/fosil-penyu-hidung-babi-yang-langka-ditemukan-di-australia-nmu-600x400.jpg?p=27766"),
                Turtle("Galapagos", "https://asset-a.grid.id/crop/0x0:0x0/780x800/photo/bobofoto/original/11638_kura-kura-raksa-di-pulau-aldabra.jpg")
            )
        }catch (e: Exception){
            Log.d("ERRORNETWORKREPO", e.message.toString())
            listOf()
        }

        return turtles
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

    suspend fun sendReport(reportData: Report, callback: (Boolean) -> Unit) {
        val call: Call<Void> = apiService.sendReport(reportData)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: retrofit2.Response<Void>) {
                if (response.isSuccessful) {
                    callback(true)
                } else {
                    callback(false)
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                callback(false)
            }
        })
    }
}