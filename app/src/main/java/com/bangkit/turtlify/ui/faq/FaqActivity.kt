package com.bangkit.turtlify.ui.faq

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.turtlify.R
import com.bangkit.turtlify.data.model.faq.FaqData
import com.bangkit.turtlify.data.model.feedback.FeedbackData
import com.bangkit.turtlify.data.network.api.ApiConfig
import com.bangkit.turtlify.data.network.api.ApiService
import com.bangkit.turtlify.data.repository.Repository
import com.bangkit.turtlify.databinding.ActivityFaqBinding
import com.bangkit.turtlify.utils.ViewModelFactory

class FaqActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFaqBinding
    private val faqViewModel by viewModels<FaqViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFaqBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupView()
        setupRecyclerView()

        val submitButton: Button = findViewById(R.id.submit_message_button)
        submitButton.setOnClickListener {
            showFormDialog()
        }
    }
    private fun setupView() {
        val toolbar = findViewById<Toolbar>(R.id.my_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = getString(R.string.title_activity_faq)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setupRecyclerView() {
        val faqList = FaqData.faqList
        val faqAdapter = ListFaqAdapter(faqList)

        binding.rvItemFaq.layoutManager = LinearLayoutManager(this)
        binding.rvItemFaq.adapter = faqAdapter
    }

    private fun showFormDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_form, null)

        val alertDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Send Message")
            .setPositiveButton("Send") { dialog, which ->
                val email = dialogView.findViewById<EditText>(R.id.email_edit_text).text.toString()
                val message = dialogView.findViewById<EditText>(R.id.feedback_edit_text).text.toString()

                // Perform actions with email and message
                // ...
                val feedbackData = FeedbackData(userEmail = email, userMessage = message)
                sendFeedback(feedbackData)


                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }
            .create()

        alertDialog.show()
    }

    private fun sendFeedback(feedbackData: FeedbackData) {
        faqViewModel.sendFeedback(feedbackData) { success ->
            if (success) {
                Log.d(TAG, "sendFeedback: Email Succesfully Send ")
            } else {
                Log.d(TAG, "sendFeedback: Email Failed to Send ")
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}