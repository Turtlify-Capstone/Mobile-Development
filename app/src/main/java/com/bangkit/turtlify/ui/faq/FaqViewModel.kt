package com.bangkit.turtlify.ui.faq

import androidx.lifecycle.ViewModel
import com.bangkit.turtlify.data.model.feedback.FeedbackData
import com.bangkit.turtlify.data.repository.Repository

class FaqViewModel(private val repository: Repository) : ViewModel() {
    fun sendFeedback(feedbackData: FeedbackData, callback: (Boolean) -> Unit) {
        repository.sendFeedback(feedbackData) { success ->
            callback(success)
        }
    }
}