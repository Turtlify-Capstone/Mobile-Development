package com.bangkit.turtlify.ui.identifier

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.turtlify.data.network.model.ImageUploadResponse
import com.bangkit.turtlify.data.repository.TurtlifyRepository
import kotlinx.coroutines.launch
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