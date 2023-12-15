package com.bangkit.turtlify.ui.encyclopediadetail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.bangkit.turtlify.R
import com.bangkit.turtlify.databinding.ActivityEncyclopediaBinding
import com.bangkit.turtlify.databinding.ActivityEncyclopediaDetailBinding
import com.bumptech.glide.Glide

class EncyclopediaDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEncyclopediaDetailBinding
    private lateinit var viewModel: EncyclopediaViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEncyclopediaDetailBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[EncyclopediaViewModel::class.java]
        setContentView(binding.root)
        setupView()
        val turtleId = intent.getIntExtra("turtleId", 0)

        if(turtleId !== null){
            viewModel.getEncyclopediaDetail(turtleId)
        }

        viewModel.isLoading.observe(this){isLoading ->
            showProgressCircular(isLoading)
        }

        viewModel.encyclopedia.observe(this){turtle ->
            if(turtle !== null){
                with(binding) {
                    tvTurtleName.text = turtle.name
                    tvTurtleLatinName.text = turtle.latinName
                    tvTurtleStatus.text = turtle.status
                    tvDescription.text = turtle.description
                    tvFoodValue.text = turtle.food
                    tvLifeValue.text = turtle.lifeExpectancy

                    Glide.with(this@EncyclopediaDetailActivity)
                        .load(turtle.imageUrl).centerCrop()
                        .into(turtleImage)

                    tvTurtleStatus.setTextColor(
                        resources.getColor(
                            if(turtle.status == "dilindungi") R.color.status_red else R.color.status_green
                        )
                    )
                }
            }
        }


    }
    private fun setupView() {
        val toolbar = findViewById<Toolbar>(R.id.my_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = getString(R.string.title_activity_encyclopedia)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun showProgressCircular(isLoading: Boolean) {
        binding.progressCircular.visibility = if(isLoading) View.VISIBLE else View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}