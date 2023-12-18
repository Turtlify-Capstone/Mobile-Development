package com.bangkit.turtlify.ui.encyclopediadetail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.bangkit.turtlify.R
import com.bangkit.turtlify.data.network.model.FetchTurtlesResponseItem
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

        val turtle: FetchTurtlesResponseItem? = intent.getParcelableExtra("turtleData")

        Log.d("RECEIVED_TURTLE", turtle.toString())

        if(turtle !== null){
            with(binding) {
                tvTurtleName.text = turtle.namaLokal!!.split(",").first()
                tvTurtleLatinName.text = turtle.namaLatin

                if(turtle.statusKonversi!!.split(" ").contains("dilindungi")){
                    tvTurtleStatus.text = "dilindungi"
                    tvTurtleStatus.setTextColor(getColor(R.color.status_red))
                } else{
                    tvTurtleStatus.text = "tidak dilindungi"
                    tvTurtleStatus.setTextColor(getColor(R.color.status_green))
                }

                tvDescription.text = turtle.description
                tvFoodValue.text = "Not available"
                tvLifeValue.text = "Not available"

                Glide.with(this@EncyclopediaDetailActivity)
                    .load(turtle.image!!.split(",").first()).centerCrop()
                    .into(turtleImage)
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

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}