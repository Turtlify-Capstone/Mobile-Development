package com.bangkit.turtlify.ui.encyclopediadetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.turtlify.data.network.model.FetchTurtlesResponseItem
import com.bangkit.turtlify.data.repository.TurtlifyRepository

class EncyclopediaViewModel : ViewModel() {
    private var repository = TurtlifyRepository()

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    private val _encyclopedia: MutableLiveData<FetchTurtlesResponseItem> = MutableLiveData()

    val isLoading: LiveData<Boolean> = _isLoading
    val encyclopedia: MutableLiveData<FetchTurtlesResponseItem> = _encyclopedia
}