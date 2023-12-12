package com.bangkit.turtlify.ui.about

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.turtlify.databinding.ActivityAboutBinding
import com.bangkit.turtlify.databinding.ActivitySettingsBinding

class AboutActivity : AppCompatActivity(){
    private lateinit var binding: ActivityAboutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}