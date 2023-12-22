package com.bangkit.turtlify.ui.identifier

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.turtlify.data.database.entity.Turtle
import com.bangkit.turtlify.data.network.model.AdditionalData
import com.bangkit.turtlify.data.repository.Repository
import kotlinx.coroutines.launch
import java.io.File

class IdentifierViewModel(private val repository: Repository): ViewModel() {
    fun uploadImage(
        imageFile: File,
        onSuccess: (AdditionalData) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            repository.uploadImage(imageFile,
                    onSuccess = {turtleData ->
                        onSuccess(turtleData)
                    },
                    onError = {errorMsg ->
                        onError("Failed identifying turtle, make sure to send a right image")
                        Log.e("ERROR_UPLOAD_IMAGE", errorMsg)
                    }
                )
        }
    }

    fun insertTurtle(imageUrl:String, turtle: AdditionalData) {
        val tempTurtle = Turtle(
            persebaranHabitat = turtle.persebaranHabitat,
            namaLokal = turtle.namaLokal,
            image = imageUrl,
            habitat = turtle.habitat,
            description = turtle.description,
            namaLatin = turtle.namaLatin,
            latitude = turtle.latitude,
            id =  turtle.id!!,
            longitude = turtle.longitude,
            statusKonversi = turtle.statusKonversi
        )

        viewModelScope.launch {
            repository.insertTurtle(
                tempTurtle,
                onSuccess = {successMsg ->
                    Log.d("SUCCESS_SAVE_TURTLE", successMsg)
                },
                onError = {errorMsg ->
                    Log.d("ERROR_SAVE_TURTLE", errorMsg)
                }
            )
        }
    }
}