package com.bangkit.turtlify.data.repository

import com.bangkit.turtlify.data.model.feedback.FeedbackData
import com.bangkit.turtlify.data.model.suggestion.Suggestion
import com.bangkit.turtlify.data.network.api.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository(private val apiService: ApiService) {

    fun sendFeedback(feedbackData: FeedbackData, callback: (Boolean) -> Unit) {
        val call: Call<Void> = apiService.sendFeedback(feedbackData)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
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

    fun sendSuggestion(suggestionData: Suggestion, callback: (Boolean) -> Unit) {
        val call: Call<Void> = apiService.sendSuggestion(suggestionData)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
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

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            apiService: ApiService
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(apiService)
            }.also { instance = it }
    }
}