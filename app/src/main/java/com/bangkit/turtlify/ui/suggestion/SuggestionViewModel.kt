package com.bangkit.turtlify.ui.suggestion

import androidx.lifecycle.ViewModel
import com.bangkit.turtlify.data.model.suggestion.Suggestion
import com.bangkit.turtlify.data.repository.Repository

class SuggestionViewModel(private val repository: Repository) : ViewModel() {
    fun sendSuggestion(suggestionData: Suggestion, callback: (Boolean) -> Unit) {
        repository.sendSuggestion(suggestionData) { success ->
            callback(success)
        }
    }
}