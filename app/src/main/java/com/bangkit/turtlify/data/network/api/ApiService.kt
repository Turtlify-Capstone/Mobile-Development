package com.bangkit.turtlify.data.network.api

import com.bangkit.turtlify.data.network.model.ImageUploadResponse
import com.bangkit.turtlify.data.network.model.TurtleResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("upload")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part
    ): ImageUploadResponse

    @GET("data")
    suspend fun getTurtles() : TurtleResponse

}