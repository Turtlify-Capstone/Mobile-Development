package com.bangkit.turtlify.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.turtlify.data.database.repository.TurtlifyRepository

class HistoryViewModelFactory(private val repository: TurtlifyRepository) : ViewModelProvider.Factory {
    companion object {
        @Volatile
        private var INSTANCE: HistoryViewModelFactory? = null
        @JvmStatic
        fun getInstance(repository: TurtlifyRepository): HistoryViewModelFactory {
            if (INSTANCE == null) {
                synchronized(HistoryViewModelFactory::class.java) {
                    INSTANCE = HistoryViewModelFactory(repository)
                }
            }
            return INSTANCE as HistoryViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            return HistoryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
