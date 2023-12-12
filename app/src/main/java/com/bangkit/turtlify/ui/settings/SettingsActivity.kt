package com.bangkit.turtlify.ui.settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.turtlify.R
import com.bangkit.turtlify.databinding.ActivitySettingsBinding
import com.bangkit.turtlify.ui.about.AboutActivity
import com.bangkit.turtlify.ui.faq.FaqActivity
import com.bangkit.turtlify.ui.suggestion.SuggestionActivity

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
    }

    private fun setupAction() {
        binding.tvLanguage.setOnClickListener {
            navigateToLanguageActivity()
        }
        binding.tvFaq.setOnClickListener {
            navigateToFAQActivity()
        }
        binding.tvSuggestion.setOnClickListener {
            navigateToSuggestionActivity()
        }
        binding.tvAbout.setOnClickListener {
            navigateToAboutActivity()
        }
        binding.tvShare.setOnClickListener {
            shareAction()
        }


    }

    private fun navigateToLanguageActivity() {
        true
    }

    private fun navigateToFAQActivity() {
        val intent = Intent(this, FaqActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToSuggestionActivity() {
        intent = Intent(this, SuggestionActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToAboutActivity() {
        val intent = Intent(this, AboutActivity::class.java)
        startActivity(intent)
    }

    private fun shareAction() {
        val linkRepository = "https://github.com/Turtlify-Capstone/Mobile-Development"
        val shareText = "Check out this cool Application called Turtlify: $linkRepository"
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Share Application")
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText)

        startActivity(Intent.createChooser(shareIntent, "Share via"))
    }
}