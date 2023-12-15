package com.bangkit.turtlify.ui.encyclopediadetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.turtlify.data.repository.EncyclopediaRepository
import com.bangkit.turtlify.data.repository.Turtle
import kotlinx.coroutines.launch

class EncyclopediaViewModel : ViewModel() {
    private var repository = EncyclopediaRepository()

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    private val _encyclopedia: MutableLiveData<Turtle?> = MutableLiveData()

    val isLoading: LiveData<Boolean> = _isLoading
    val encyclopedia: MutableLiveData<Turtle?> = _encyclopedia

    fun getEncyclopediaDetail(turtleId: Int){
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                _encyclopedia.postValue(repository.getEncyclopediaDetail(turtleId))
                _isLoading.postValue(false)
            } catch (e: Exception) {
                _isLoading.postValue(false)
            }
        }
    }
}