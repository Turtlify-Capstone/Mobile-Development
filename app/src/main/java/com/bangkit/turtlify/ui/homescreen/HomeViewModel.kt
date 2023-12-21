package com.bangkit.turtlify.ui.homescreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.turtlify.data.network.model.FetchTurtlesResponseItem
import com.bangkit.turtlify.data.repository.Repository
import com.bangkit.turtlify.data.repository.TurtlifyRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository) : ViewModel() {
    private val _encyclopedia:MutableLiveData<List<FetchTurtlesResponseItem>> = MutableLiveData()

    val encyclopedia: MutableLiveData<List<FetchTurtlesResponseItem>> = _encyclopedia

    fun getTurtlesEncyclopedia(
        onError: (String) -> Unit
    ){
        viewModelScope.launch {
            repository.fetchTurtles(
                onSuccess = {turtles ->
                    _encyclopedia.postValue(turtles.take(2))
                },
                onError = { errorMsg ->
                    onError("Error while retrieving encyclopedia data")
                    Log.e("ENCYCLOPEDIA_ERR", errorMsg)
                }
            )
        }
    }
}