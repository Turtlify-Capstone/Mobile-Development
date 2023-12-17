package com.bangkit.turtlify.ui.maps

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.bangkit.turtlify.R
import com.bangkit.turtlify.databinding.ActivityMapsBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val boundsBuilder = LatLngBounds.Builder()
    private val selectedTurtle:MutableLiveData<Turtle?> = MutableLiveData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        setupView()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val defaultLocation = LatLng(-7.175819, 108.618369)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(defaultLocation))

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        getMyLocation()
        addManyMarker()
        observeSelectedTurtle()

        mMap.setOnMapClickListener {
            selectedTurtle.value = null
        }

        binding.turtleCard.setOnClickListener{
            Log.d("SELECTED_TURTE", selectedTurtle.value.toString())
        }
    }

    private fun setupView() {
        val toolbar = findViewById<Toolbar>(R.id.my_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = "Turtle Maps"
            setDisplayHomeAsUpEnabled(true)
        }
        toolbar.setNavigationOnClickListener{
            finish()
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getMyLocation()
            }
        }
    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }
    private fun addManyMarker() {
        turtlePlace.forEach { turtle ->
            val latLng = LatLng(turtle.latitude, turtle.longitude)
            turtlePlace.forEach { turtle ->
                val latLng = LatLng(turtle.latitude, turtle.longitude)
                Glide.with(this)
                    .asBitmap()
                    .apply(RequestOptions().override(70, 70).circleCrop())
                    .load(turtle.imageUrl)
                    .into(object : SimpleTarget<Bitmap?>() {
                        override fun onResourceReady(
                            originalBitmap: Bitmap,
                            transition: Transition<in Bitmap?>?
                        ) {
                            val borderedBitmap = addCircularBorderToBitmap(originalBitmap, Color.GREEN, 10)
                            val marker = mMap.addMarker(MarkerOptions().position(latLng).title(turtle.name))
                            marker!!.setIcon(BitmapDescriptorFactory.fromBitmap(borderedBitmap))
                            marker.tag = turtle
                        }
                    })
            }

            boundsBuilder.include(latLng)
            mMap.setOnMarkerClickListener { marker ->
                if (marker.tag != null) {
                    selectedTurtle.postValue(marker.tag as Turtle?)
                }
                true
            }
        }

        val bounds: LatLngBounds = boundsBuilder.build()
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngBounds(
                bounds,
                resources.displayMetrics.widthPixels,
                resources.displayMetrics.heightPixels,
                300
            )
        )
    }

    private fun observeSelectedTurtle(){
        selectedTurtle.observe(this@MapsActivity){turtle ->
            if(turtle !== null){
                with(binding) {
                    turtleCardName.text = turtle.name
                    turtleCardLatinName.text = turtle.latinName
                    turtleCardStatus.text = turtle.status
                    turtleCardStatus.setTextColor(getResources().getColor(
                        if (turtle.status == "dilindungi") R.color.green_text else R.color.red_text
                    ))
                    Glide.with(this@MapsActivity)
                        .load(turtle.imageUrl).centerCrop()
                        .into(turtleCardImage)

                    turtleCard.visibility = View.VISIBLE
                }
            }else{
                binding.turtleCard.visibility = View.GONE
            }
        }
    }

    private fun addCircularBorderToBitmap(bitmap: Bitmap, borderColor: Int, borderWidth: Int): Bitmap {
        val diameter = bitmap.width.coerceAtMost(bitmap.height) + borderWidth * 2
        val center = diameter / 2f

        val borderPaint = Paint().apply {
            color = borderColor
            style = Paint.Style.STROKE
            strokeWidth = borderWidth.toFloat()
            isAntiAlias = true
        }

        val borderBitmap = Bitmap.createBitmap(diameter, diameter, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(borderBitmap)

        canvas.drawCircle(center, center, center - borderWidth, borderPaint)
        canvas.drawBitmap(bitmap, center - bitmap.width / 2f, center - bitmap.height / 2f, null)

        return borderBitmap
    }
}