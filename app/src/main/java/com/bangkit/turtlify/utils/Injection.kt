package com.bangkit.turtlify.utils

import android.content.Context
import com.bangkit.turtlify.data.network.api.ApiConfig
import com.bangkit.turtlify.data.repository.Repository

object Injection {
    fun provideRepository(context: Context): Repository {
        val apiService = ApiConfig.getApiService()
        return Repository.getInstance(apiService)
    }

}
