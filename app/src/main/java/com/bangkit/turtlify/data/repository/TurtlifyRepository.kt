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
}