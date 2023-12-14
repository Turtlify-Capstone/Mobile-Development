package com.bangkit.turtlify.ui.encyclopedia

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.turtlify.data.repository.EncyclopediaRepository
import com.bangkit.turtlify.data.repository.Turtle
import kotlinx.coroutines.launch

class EncyclopediaViewModel : ViewModel() {
    private val repository = EncyclopediaRepository()
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    private val _encyclopedia:MutableLiveData<List<Turtle>?> = MutableLiveData()

    val isLoading: LiveData<Boolean> = _isLoading
    val encyclopedia: MutableLiveData<List<Turtle>?> = _encyclopedia


    fun getTurtlesEncyclopedia(){
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                val encyclopedia = repository.getTurtlesEncyclopedia()
                _encyclopedia.postValue(encyclopedia)
                _isLoading.postValue(false)
            }catch (e: Exception){
                _isLoading.postValue(false)
            }
        }
    }
}