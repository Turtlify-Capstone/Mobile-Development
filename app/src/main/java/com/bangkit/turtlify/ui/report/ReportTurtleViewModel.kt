package com.bangkit.turtlify.ui.viemodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.turtlify.data.network.model.ImageUploadResponse
import com.bangkit.turtlify.data.repository.TurtlifyRepository
import com.bangkit.turtlify.ui.report.FormData
import kotlinx.coroutines.launch
import java.io.File

class Contact (
    val email: String,
    val institution: String
)

data class Response (
    val success: Boolean,
    val message: String
)

class Turtle (
    val name: String,
    val image: String
){
    override fun toString(): String {
        return name
    }
}


class ReportTurtleViewModel: ViewModel() {
    private val turtlifyRepository:  TurtlifyRepository = TurtlifyRepository()
    private val _contactsLiveData = MutableLiveData<List<String>>()
    private val _turtlesLiveData = MutableLiveData<List<Turtle>>()

    val contactsLiveData: LiveData<List<String>> = _contactsLiveData
    val turtlesLiveData: LiveData<List<Turtle>> = _turtlesLiveData

    fun fetchContacts() {
        viewModelScope.launch {
            try {
                val contacts = turtlifyRepository.getContacts()
                val formattedContacts = contacts.map { "${it.email}, ${it.institution}" }
                _contactsLiveData.postValue(formattedContacts)
            } catch (e: Exception) {
                // Handle the error or propagate it further
            }
        }
    }

    fun fetchTurtles() {
        viewModelScope.launch {
            try {
                val turtles = turtlifyRepository.getTurtles()
                _turtlesLiveData.postValue(turtles)
            } catch (e: Exception) {
                // Handle the error or propagate it further
            }
        }
    }

    fun submitReportForm(
        formData: FormData,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ){
        viewModelScope.launch {
             turtlifyRepository.submitReportForm(formData, onSuccess, onError)
        }
    }
}
