package com.bangkit.turtlify.ui.viemodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.turtlify.data.model.feedback.FeedbackData
import com.bangkit.turtlify.data.model.report.Report
import com.bangkit.turtlify.data.network.model.FetchTurtlesResponseItem
import com.bangkit.turtlify.data.network.model.ImageUploadResponse
import com.bangkit.turtlify.data.repository.Repository
import kotlinx.coroutines.launch
import java.io.File

class ReportTurtleViewModel(private val repository: Repository): ViewModel() {
    private val _contactsLiveData = MutableLiveData<List<String>>()
    private val _turtlesLiveData = MutableLiveData<List<FetchTurtlesResponseItem>>()

    val contactsLiveData: LiveData<List<String>> = _contactsLiveData
    val turtlesLiveData: LiveData<List<FetchTurtlesResponseItem>> = _turtlesLiveData

    fun fetchContacts(
        onError : (String) -> Unit
    ) {
        viewModelScope.launch {
            repository.getContacts(
                onSuccess = {contacts ->
                    val formattedContacts = contacts.map { "${it.email}, ${it.institution}" }
                    _contactsLiveData.postValue(formattedContacts)
                },
                onError = { errorMsg ->
                    Log.e("ERROR_FETCHING_CONTACTS", errorMsg)
                    onError("Error while retrieving contacts data")
                }
            )
        }
    }

    fun fetchTurtles(
        onError : (String) -> Unit
    ) {
        viewModelScope.launch {
            repository.fetchTurtles(
                onSuccess = {turtles ->
                    _turtlesLiveData.postValue(turtles)
                },
                onError = {errorMsg ->
                    Log.e("ERROR_FETCHING_TURTLES", errorMsg)
                    onError("Error while retrieving turtles data")
                }
            )
        }
    }

    fun submitReportForm(
        reportData: Report,
        callback: (Boolean) -> Unit
    ){
         repository.sendReport(reportData) { success -> callback(success) }
    }
}
