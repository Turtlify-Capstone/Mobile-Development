package com.bangkit.turtlify.ui.viemodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.turtlify.network.repository.NetworkRepository
import com.bangkit.turtlify.ui.report.FormData
import kotlinx.coroutines.launch

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
    private val networkRepository: NetworkRepository = NetworkRepository()
    private val _contactsLiveData = MutableLiveData<List<String>>()
    private val _turtlesLiveData = MutableLiveData<List<Turtle>>()
    private val _isFormSubmitting = MutableLiveData<Boolean>(false)

    val contactsLiveData: LiveData<List<String>> = _contactsLiveData
    val turtlesLiveData: LiveData<List<Turtle>> = _turtlesLiveData
    val isFormSubmitting : LiveData<Boolean> = _isFormSubmitting

    fun fetchContacts() {
        viewModelScope.launch {
            try {
                val contacts = networkRepository.getContacts()
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
                val turtles = networkRepository.getTurtles()
                _turtlesLiveData.postValue(turtles)
            } catch (e: Exception) {
                // Handle the error or propagate it further
            }
        }
    }

    fun submitReportForm(formData: FormData): Response? {
        var response: Response? = null
        viewModelScope.launch {
             try {
                 _isFormSubmitting.postValue(true)
                 networkRepository.submitReportForm(formData)
                response = Response(true, "Report submitted successfully")
                _isFormSubmitting.postValue(false)
            }catch (e: Exception){
                 _isFormSubmitting.postValue(false)
             }
        }

        return response
    }
}
