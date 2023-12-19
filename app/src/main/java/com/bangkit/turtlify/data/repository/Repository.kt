package com.bangkit.turtlify.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bangkit.turtlify.data.model.feedback.FeedbackData
import com.bangkit.turtlify.data.model.report.Report
import com.bangkit.turtlify.data.model.search.SearchResponse
import com.bangkit.turtlify.data.model.search.SearchResponseItem
import com.bangkit.turtlify.data.model.suggestion.Suggestion
import com.bangkit.turtlify.data.network.api.ApiConfig
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

    fun searchTurtle(
        query: String,
        onSuccess: (List<SearchResponseItem>) -> Unit,
        onFailure: (String) -> Unit
    ){
        ApiConfig.getApiService()
            .searchTurtle(query)
            .enqueue(object : Callback<List<SearchResponseItem>>{
                override fun onResponse(
                    call: Call<List<SearchResponseItem>>,
                    response: Response<List<SearchResponseItem>>
                ) {
                    if (response.isSuccessful){
                        response.body()?.let { onSuccess(it) }
                    }
                }

                override fun onFailure(call: Call<List<SearchResponseItem>>, t: Throwable) {
                    onFailure(t.message.toString())
                    Log.d("Failure", t.message.toString())
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