package com.bangkit.turtlify.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.turtlify.data.database.repository.DatabaseRepository
import com.bangkit.turtlify.data.database.repository.Turtle
import kotlinx.coroutines.launch

class HistoryViewModel : ViewModel(){
    private val repository = DatabaseRepository()
    private val _isLoading:MutableLiveData<Boolean> = MutableLiveData()
    private val _histories:MutableLiveData<List<Turtle>?> = MutableLiveData()

    val isLoading:LiveData<Boolean> = _isLoading
    val histories: MutableLiveData<List<Turtle>?> = _histories


    fun getHistories(){
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                val histories = repository.getHistory()
                _histories.postValue(histories)
                _isLoading.postValue(false)
            }catch (e: Exception){
                _isLoading.postValue(false)
            }
        }
    }
}