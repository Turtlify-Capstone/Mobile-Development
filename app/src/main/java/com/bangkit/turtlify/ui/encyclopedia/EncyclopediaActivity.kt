package com.bangkit.turtlify.ui.encyclopedia

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.turtlify.R
import com.bangkit.turtlify.data.repository.Turtle
import com.bangkit.turtlify.databinding.ActivityEncyclopediaBinding

class EncyclopediaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEncyclopediaBinding
    private lateinit var viewModel: EncyclopediaViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEncyclopediaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val rvEncyclopedia = binding.rvEncyclopedia
        rvEncyclopedia.layoutManager = LinearLayoutManager(this)
        viewModel = ViewModelProvider(this)[EncyclopediaViewModel::class.java]

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

    private fun showRecyclerList(turtles: List<Turtle>) {
        val rvEncyclopedia = binding.rvEncyclopedia // Access RecyclerView from binding

        val listHeroAdapter = EncyclopediaAdapter(this, turtles)
        rvEncyclopedia.adapter = listHeroAdapter

        listHeroAdapter.setOnItemClickCallback(object : EncyclopediaAdapter.OnItemClickCallback {
            override fun onItemClicked(turtle: Turtle) {
                Log.d("TURTLE", turtle.toString())
//                val intent = Intent(this@HistoryFragment, EnsiklopediaDetail::class.java)
//                intent.putStringExtra("turtle_id", turtle_id)
//                startActivity(intent)
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}