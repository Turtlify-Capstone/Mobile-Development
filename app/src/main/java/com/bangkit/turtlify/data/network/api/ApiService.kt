package com.bangkit.turtlify.data.network.api

import com.bangkit.turtlify.data.model.feedback.FeedbackData
import com.bangkit.turtlify.data.model.report.Report
import com.bangkit.turtlify.data.model.search.SearchResponseItem
import com.bangkit.turtlify.data.model.suggestion.Suggestion
import com.bangkit.turtlify.data.network.model.FetchTurtlesResponseItem
import com.bangkit.turtlify.data.network.model.ImageUploadResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {
    @Multipart
    @POST("upload")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part
    ): ImageUploadResponse

    @POST("FeedbackEmail")
    fun sendFeedback(@Body feedbackData: FeedbackData): Call<Void>

    @POST("AddFeedback")
    fun sendSuggestion(@Body suggestionData: Suggestion): Call<Void>

    @GET("data")
    suspend fun fetchTurtles(): List<FetchTurtlesResponseItem>

    @POST("FeedbackEmail")
    fun sendReport(@Body reportData: Report): Call<Void>

    @GET("search")
    fun searchTurtle(@Query("nama_lokal") page: String): Call<List<SearchResponseItem>>
}