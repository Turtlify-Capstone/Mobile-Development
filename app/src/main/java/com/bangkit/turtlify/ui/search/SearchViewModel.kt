package com.bangkit.turtlify.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.turtlify.data.model.search.SearchResponse
import com.bangkit.turtlify.data.model.search.SearchResponseItem
import com.bangkit.turtlify.data.repository.Repository

class SearchViewModel(private val repository: Repository) : ViewModel() {

    private val _listTurtle = repository.listTurtle
    val listTurtle: LiveData<List<SearchResponseItem>> get() = _listTurtle
//    val listTurtle: MutableLiveData<List<SearchResponseItem>?> get() = repository.listTurtle
    fun searchTurtle(query: String) {
        repository.searchTurtle(query)
    }
}