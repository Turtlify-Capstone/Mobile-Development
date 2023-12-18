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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEncyclopediaDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()

        val turtle: FetchTurtlesResponseItem? = intent.getParcelableExtra("turtleData")

        Log.d("RECEIVED_TURTLE", turtle.toString())

        if(turtle !== null){
            with(binding) {
                tvName.text = turtle.namaLokal!!.split(",").first()
                tvLatinName.text = turtle.namaLatin
                tvAlias.text = turtle.namaLokal

                if(turtle.statusKonversi!!.split(" ").contains("dilindungi")){
                    tvStatus.text = "dilindungi"
                    tvStatus.setTextColor(getColor(R.color.red_text))
                    tvStatus.setBackgroundColor(getColor(R.color.red_text_bg))
                } else{
                    tvStatus.text = "tidak dilindungi"
                    tvStatus.setTextColor(getColor(R.color.green_text))
                    tvStatus.setBackgroundColor(getColor(R.color.green_text_bg))
                }

                tvDescription.text = turtle.description
                tvHabitat.text = turtle.habitat
                tvPersebaranHabitat.text = turtle.persebaranHabitat

                Glide.with(this@EncyclopediaDetailActivity)
                    .load(turtle.image!!.split(",").first()).fitCenter()
                    .into(turtleImage)
            }
        }


    }
    private fun setupView() {
        val toolbar = findViewById<Toolbar>(R.id.my_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = getString(R.string.title_activity_encyclopedia_detail)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}