package com.bangkit.turtlify.ui.encyclopedia

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.turtlify.R
import com.bangkit.turtlify.data.network.model.FetchTurtlesResponseItem
import com.bangkit.turtlify.databinding.ActivityEncyclopediaBinding
import com.bangkit.turtlify.ui.encyclopediadetail.EncyclopediaDetailActivity
import com.bangkit.turtlify.ui.identifier.IdentifierViewModel
import com.bangkit.turtlify.utils.ViewModelFactory

class EncyclopediaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEncyclopediaBinding
    private val viewModel by viewModels<EncyclopediaViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEncyclopediaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val rvEncyclopedia = binding.rvEncyclopedia
        rvEncyclopedia.layoutManager = LinearLayoutManager(this)

        viewModel.getTurtlesEncyclopedia()
        viewModel.encyclopedia.observe(this) { turtles ->
            turtles?.let { showRecyclerList(turtles) }
        }
        viewModel.isLoading.observe(this){
            binding.progressCircular.visibility = if (it) View.VISIBLE else View.GONE
        }

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

    private fun showRecyclerList(turtles: List<FetchTurtlesResponseItem>) {
        val rvEncyclopedia = binding.rvEncyclopedia

        val listTurtleAdapter = EncyclopediaAdapter(this, turtles)
        rvEncyclopedia.adapter = listTurtleAdapter

        listTurtleAdapter.setOnItemClickCallback(object : EncyclopediaAdapter.OnItemClickCallback {
            override fun onItemClicked(data: FetchTurtlesResponseItem) {
                val intent = Intent(this@EncyclopediaActivity, EncyclopediaDetailActivity::class.java)
                intent.putExtra("turtleData", data)
                startActivity(intent)
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}