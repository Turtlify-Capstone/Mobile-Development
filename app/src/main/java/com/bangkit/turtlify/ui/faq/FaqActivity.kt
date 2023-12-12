package com.bangkit.turtlify.ui.faq

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.turtlify.databinding.ActivityFaqBinding
import com.bangkit.turtlify.databinding.ActivitySettingsBinding

class FaqActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFaqBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFaqBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}