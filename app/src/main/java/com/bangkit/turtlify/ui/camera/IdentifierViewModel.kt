package com.bangkit.turtlify.ui.camera

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.turtlify.data.network.model.ImageUploadResponse
import com.bangkit.turtlify.data.repository.TurtlifyRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File

class IdentifierViewModel: ViewModel() {
    private val repository = TurtlifyRepository()

    fun uploadImage(
        imageFile: File,
        onSuccess: (ImageUploadResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            repository.uploadImage(imageFile, onSuccess, onError)
        }
    }
}