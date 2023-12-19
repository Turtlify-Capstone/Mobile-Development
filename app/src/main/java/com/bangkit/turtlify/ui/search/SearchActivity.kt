package com.bangkit.turtlify.ui.search

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.turtlify.databinding.ActivitySearchBinding
import com.bangkit.turtlify.utils.ViewModelFactory

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var adapter: ListSearchAdapter
    private val searchViewModel by viewModels<SearchViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
    }


    private fun setupAction() {
        binding.rvSearchTurtle.layoutManager = LinearLayoutManager(this)
        adapter = ListSearchAdapter(this)
        binding.rvSearchTurtle.adapter = adapter
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    val query = searchView.text.toString()
                    searchViewModel.searchTurtle(query)
                    searchView.hide()
                    true
                }
        }
        searchViewModel.listTurtle.observe(this) { listSearch ->
            adapter.submitList(listSearch)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}