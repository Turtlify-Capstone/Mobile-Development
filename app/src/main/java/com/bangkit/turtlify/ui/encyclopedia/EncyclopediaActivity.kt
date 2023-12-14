package com.bangkit.turtlify.ui.encyclopedia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bangkit.turtlify.R
import com.bangkit.turtlify.databinding.ActivityEncyclopediaBinding

class EncyclopediaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEncyclopediaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEncyclopediaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
    }

    private fun setupView() {
        val toolbar = findViewById<Toolbar>(R.id.my_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = getString(R.string.title_activity_encyclopedia)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}