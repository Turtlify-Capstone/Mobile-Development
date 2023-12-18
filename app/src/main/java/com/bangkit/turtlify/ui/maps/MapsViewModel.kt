package com.bangkit.turtlify.ui.maps

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.turtlify.data.network.model.FetchTurtlesResponseItem
import com.bangkit.turtlify.data.repository.TurtlifyRepository
import kotlinx.coroutines.launch

class MapsViewModel : ViewModel() {
    private val repository = TurtlifyRepository()
    private val _turtles: MutableLiveData<List<FetchTurtlesResponseItem>> = MutableLiveData<List<FetchTurtlesResponseItem>>()
    val turtles: MutableLiveData<List<FetchTurtlesResponseItem>> = _turtles
    fun fetchTurtles() {
        viewModelScope.launch {
            repository.fetchTurtles(
                onSuccess = { turtles ->
                    _turtles.postValue(turtles)
                },
                onError = {errorMsg ->
                    Log.e("ERR_FETCH_TURTLES", errorMsg)
                }
            )
        }
    }
}