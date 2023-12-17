package com.bangkit.turtlify.ui.suggestion

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bangkit.turtlify.R
import com.bangkit.turtlify.data.model.feedback.FeedbackData
import com.bangkit.turtlify.data.model.suggestion.Suggestion
import com.bangkit.turtlify.databinding.ActivitySuggestionBinding
import com.bangkit.turtlify.ui.faq.FaqViewModel
import com.bangkit.turtlify.utils.ViewModelFactory

class SuggestionActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySuggestionBinding
    private val suggestionViewModel by viewModels<SuggestionViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuggestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
    }

    private fun setupView() {
        val toolbar = findViewById<Toolbar>(R.id.my_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = getString(R.string.title_activity_suggestion)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun getSelectedFeedbackType(): String {
        val rbTechnicalIssue = findViewById<RadioButton>(R.id.rbTechnicalIssue)
        val rbQuestion = findViewById<RadioButton>(R.id.rbQuestion)
        val rbFeedback = findViewById<RadioButton>(R.id.rbFeedback)
        val rbContent = findViewById<RadioButton>(R.id.rbContent)

        return when {
            rbTechnicalIssue.isChecked -> getString(R.string.rb_issue)
            rbQuestion.isChecked -> getString(R.string.rb_feedback)
            rbFeedback.isChecked -> getString(R.string.rb_feature)
            rbContent.isChecked -> getString(R.string.rb_content)
            else -> null.toString()
        }
    }

    private fun setupAction(){
        val submitButton: Button = findViewById(R.id.submit_feedback_button)
        submitButton.setOnClickListener {
            sendSuggestion()
        }
    }

    private fun sendSuggestion() {
        val email = findViewById<EditText>(R.id.email_input_text).text.toString()
        val feedbackType = getSelectedFeedbackType()
        val inputMessage = findViewById<EditText>(R.id.feedback_input_text).text.toString()
        val userMessage = "$feedbackType: $inputMessage"

        val suggestionData = Suggestion(email, userMessage)

        suggestionViewModel.sendSuggestion(suggestionData) { success ->
            if (success) {
                showSuccessDialog()
                Log.d(ContentValues.TAG, "sendFeedback: Suggestion Succesfully Send ")
            } else {
                showErrorDialog()
                Log.d(ContentValues.TAG, "sendFeedback: Suggestion Failed to Send ")
            }
        }
    }

    private fun showSuccessDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Success")
            .setMessage("Suggestion successfully sent!")
            .setPositiveButton("OK") { _, _ ->
                finish()
            }
            .show()
    }

    private fun showErrorDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
            .setMessage("Failed to send suggestion. Please try again.")
            .setPositiveButton("OK") { _, _ ->
                findViewById<EditText>(R.id.email_input_text).text.clear()
                findViewById<EditText>(R.id.feedback_input_text).text.clear()
                clearRadioButtons()
            }
            .show()
    }

    private fun clearRadioButtons() {
        val rbTechnicalIssue = findViewById<RadioButton>(R.id.rbTechnicalIssue)
        val rbQuestion = findViewById<RadioButton>(R.id.rbQuestion)
        val rbFeedback = findViewById<RadioButton>(R.id.rbFeedback)
        val rbContent = findViewById<RadioButton>(R.id.rbContent)

        rbTechnicalIssue.isChecked = false
        rbQuestion.isChecked = false
        rbFeedback.isChecked = false
        rbContent.isChecked = false
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}