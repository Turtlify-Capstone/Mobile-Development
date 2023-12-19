package com.bangkit.turtlify.ui.homescreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.turtlify.data.network.model.FetchTurtlesResponseItem
import com.bangkit.turtlify.data.repository.TurtlifyRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val repository = TurtlifyRepository()

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    private val _encyclopedia:MutableLiveData<List<FetchTurtlesResponseItem>> = MutableLiveData()

    val isLoading: LiveData<Boolean> = _isLoading
    val encyclopedia: MutableLiveData<List<FetchTurtlesResponseItem>> = _encyclopedia

    fun getTurtlesEncyclopedia(){
        viewModelScope.launch {
            _isLoading.postValue(true)
            repository.fetchTurtles(
                onSuccess = {turtles ->
                    _encyclopedia.postValue(turtles.take(2))
                    _isLoading.postValue(false)
                },
                onError = {errorMsg ->
                    Log.e("ENCYCLOPEDIA_ERR", errorMsg)
                    _isLoading.postValue(false)
                }
            )
        }
    }
}