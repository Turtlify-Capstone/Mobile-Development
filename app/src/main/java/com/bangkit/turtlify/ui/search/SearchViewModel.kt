package com.bangkit.turtlify.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.turtlify.data.model.search.SearchResponse
import com.bangkit.turtlify.data.model.search.SearchResponseItem
import com.bangkit.turtlify.data.repository.Repository

class SearchViewModel(private val repository: Repository) : ViewModel() {

    private val _listTurtle:MutableLiveData<List<SearchResponseItem>> = MutableLiveData()
    private val _isLoading:MutableLiveData<Boolean> = MutableLiveData()

    val listTurtle: LiveData<List<SearchResponseItem>> = _listTurtle
    val isLoading:LiveData<Boolean> = _isLoading
//    val listTurtle: MutableLiveData<List<SearchResponseItem>?> get() = repository.listTurtle
    fun searchTurtle(query: String) {
    _isLoading.postValue(true)
        repository.searchTurtle(
            query,
            onSuccess = {turtles ->
                _isLoading.postValue(false)
                _listTurtle.postValue(turtles)
            },
            onFailure = {errorMsg ->
                _isLoading.postValue(false)
                Log.e("ERROR_SEARCH", errorMsg)
            }
        )
    }
}