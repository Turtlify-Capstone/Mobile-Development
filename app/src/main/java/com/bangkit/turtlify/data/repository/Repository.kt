package com.bangkit.turtlify.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bangkit.turtlify.data.database.dao.TurtlifyDao
import com.bangkit.turtlify.data.database.entity.Turtle
import com.bangkit.turtlify.data.model.contact.Contact
import com.bangkit.turtlify.data.model.feedback.FeedbackData
import com.bangkit.turtlify.data.model.report.Report
import com.bangkit.turtlify.data.model.search.SearchResponse
import com.bangkit.turtlify.data.model.search.SearchResponseItem
import com.bangkit.turtlify.data.model.suggestion.Suggestion
import com.bangkit.turtlify.data.network.api.ApiConfig
import com.bangkit.turtlify.data.network.api.ApiService
import com.bangkit.turtlify.data.network.model.AdditionalData
import com.bangkit.turtlify.data.network.model.FetchTurtlesResponseItem
import com.bangkit.turtlify.data.network.model.ImageUploadResponse
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.File

class Repository(private val apiService: ApiService, private val turtlifyDao: TurtlifyDao) {

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

    fun sendReport(reportData: Report, callback: (Boolean) -> Unit) {
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

    fun getContacts(
        onSuccess: (List<Contact>) -> Unit,
        onError: (String) -> Unit
    ){
         try {
             onSuccess(
                 listOf(
                     Contact("BKSDA DKI Jakarta", "bksdajakarta@gmail.com"),
                     Contact("BBKSDA Jawa Timur", "bbksdajatim@yahoo.co.id"),
                     Contact("BBKSDA Sumut", "bbksdasumut@yahoo.co.id"),
                     Contact("BBKSDA Jawa Barat", "bbksdajb@gmail.com"),
                     Contact("BKSDA Jawa Tengah", "bksda_jateng@yahoo.co.id"),
                 )
             )

        } catch (e: Exception) {
            Log.d("ERROR_FETCH_CONTACTS", e.message.toString())
             onSuccess(listOf())
             onError(e.message.toString())
        }
    }

    suspend fun uploadImage(
        imageFile: File,
        onSuccess: (AdditionalData) -> Unit,
        onError: (String) -> Unit
    ) {
        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "file",
            imageFile.name,
            requestImageFile
        )
        try {
            val response = apiService.uploadImage(multipartBody)
            if(response.status == "success"){
                response.additionalData?.let { onSuccess(it) }
            }
        } catch (e: Exception) {
            Log.d("ERROR_UPLOAD_IMAGE", e.message.toString())
            onError(e.message.toString())
        }
    }

//    FUNCTION TO INTERACT WITH LOCAL DATABASE

    suspend fun insertAllTurtles(turtles: List<Turtle>) {
        turtlifyDao.insertAllTurtles(turtles)
    }

    suspend fun insertTurtle(
        turtle: Turtle,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            turtlifyDao.insertTurtle(turtle)
            onSuccess("Turtle saved")
        } catch (e: Exception) {
            onError(e.message ?: "Unknown error occurred")
        }
    }

    suspend fun getAllTurtles(
        onSuccess: (List<Turtle>) -> Unit,
        onError: (String) -> Unit
    ){
        try {
            val response = turtlifyDao.getAllTurtles()
            onSuccess(response)
        } catch (e: Exception) {
            onError(e.message ?: "Unknown error occurred")
        }
    }

    suspend fun getTurtleById(turtleId: Int): Turtle {
        return turtlifyDao.getTurtleById(turtleId)
    }

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            apiService: ApiService,
            turtlifyDao: TurtlifyDao
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(apiService, turtlifyDao)
            }.also { instance = it }
    }
}