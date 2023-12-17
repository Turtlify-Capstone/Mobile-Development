package com.bangkit.turtlify.data.network.api

import com.bangkit.turtlify.data.model.feedback.FeedbackData
import com.bangkit.turtlify.data.model.suggestion.Suggestion
import com.bangkit.turtlify.data.network.model.ImageUploadResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("stories/guest")
    fun uploadImage(
        @Part file: MultipartBody.Part
    ): Call<ImageUploadResponse>

    @POST("FeedbackEmail")
    fun sendFeedback(@Body feedbackData: FeedbackData): Call<Void>

    @POST("AddFeedback")
    fun sendSuggestion(@Body suggestionData: Suggestion): Call<Void>
}