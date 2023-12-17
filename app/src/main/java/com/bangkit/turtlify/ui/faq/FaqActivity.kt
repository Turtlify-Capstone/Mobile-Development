package com.bangkit.turtlify.ui.faq

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.turtlify.R
import com.bangkit.turtlify.data.model.faq.FaqData
import com.bangkit.turtlify.databinding.ActivityFaqBinding

class FaqActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFaqBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFaqBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupRecyclerView()
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

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}