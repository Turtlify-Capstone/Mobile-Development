package com.bangkit.turtlify.ui.history

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.turtlify.data.database.entity.Turtle
import com.bangkit.turtlify.data.repository.Repository
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: Repository) : ViewModel(){
    private val _isLoading:MutableLiveData<Boolean> = MutableLiveData()
    private val _histories:MutableLiveData<List<Turtle>> = MutableLiveData()

    val isLoading:LiveData<Boolean> = _isLoading
    val histories: MutableLiveData<List<Turtle>> = _histories

    fun getAllTurtles(
        onError : (String) -> Unit
    ) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            repository.getAllTurtles(
                onSuccess = { turtles ->
                    _histories.postValue(turtles)
                    _isLoading.postValue(false)
                },
                onError = { errorMsg ->
                    Log.e("HISTORY_ERR", errorMsg)
                    _isLoading.postValue(false)
                    onError("Error while retrieving history data")
                }
            )
        }
    }
}