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

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private fun showLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    val listTurtle = MutableLiveData<List<SearchResponseItem>>()

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

    fun searchTurtle(query: String){
        showLoading(true)
        ApiConfig.getApiService()
            .searchTurtle(query)
            .enqueue(object : Callback<SearchResponse>{
                override fun onResponse(
                    call: Call<SearchResponse>,
                    response: Response<SearchResponse>
                ) {
                    showLoading(false)
                    if (response.isSuccessful){
                        listTurtle.postValue(response.body()?.searchResponse as List<SearchResponseItem>?)
                    }
                }

                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    showLoading(false)
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