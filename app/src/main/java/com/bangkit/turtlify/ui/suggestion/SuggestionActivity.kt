package com.bangkit.turtlify.ui.suggestion

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.turtlify.databinding.ActivitySuggestionBinding

class SuggestionActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySuggestionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuggestionBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}