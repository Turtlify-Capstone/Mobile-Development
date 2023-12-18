package com.bangkit.turtlify.ui.maps

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Color.pack
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
import androidx.lifecycle.ViewModelProvider
import com.bangkit.turtlify.R
import com.bangkit.turtlify.data.network.model.FetchTurtlesResponse
import com.bangkit.turtlify.data.network.model.FetchTurtlesResponseItem
import com.bangkit.turtlify.databinding.ActivityMapsBinding
import com.bangkit.turtlify.utils.addCircularBorderToBitmap
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
import com.google.gson.annotations.SerializedName

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var viewModel: MapsViewModel
    private val boundsBuilder = LatLngBounds.Builder()
    private val selectedTurtle:MutableLiveData<FetchTurtlesResponseItem?> = MutableLiveData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MapsViewModel::class.java]
        viewModel.fetchTurtles()

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
        observeSelectedTurtle()

        mMap.setOnMapClickListener {
            selectedTurtle.value = null
        }
        binding.turtleCard.setOnClickListener{
            Log.d("SELECTED_TURTLE", selectedTurtle.value.toString())
        }
        viewModel.turtles.observe(this){turtles ->
            addManyMarkers(turtles)
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

    private fun addMarkerToMap(turtle: FetchTurtlesResponseItem, latLng: LatLng) {
        Glide.with(this)
            .asBitmap()
            .apply(RequestOptions().override(70, 70).circleCrop())
            .load(turtle.image!!.split(", ").first())
            .into(object : SimpleTarget<Bitmap?>() {
                override fun onResourceReady(
                    originalBitmap: Bitmap,
                    transition: Transition<in Bitmap?>?
                ) {
                    val borderedBitmap = addCircularBorderToBitmap(originalBitmap, Color.CYAN, 10)
                    addMarkerToMapWithBitmap(turtle, latLng, borderedBitmap)
                }
            })
    }

    private fun addMarkerToMapWithBitmap(turtle: FetchTurtlesResponseItem, latLng: LatLng, bitmap: Bitmap) {
        val marker = mMap.addMarker(
            MarkerOptions()
                .position(latLng)
                .title(turtle.namaLokal!!.split(",").first())
        )
        marker?.setIcon(BitmapDescriptorFactory.fromBitmap(bitmap))
        marker?.tag = turtle
    }

    private fun addManyMarkers(turtles: List<FetchTurtlesResponseItem>) {
        turtles.forEach { turtle ->
            val latLng = LatLng(turtle.latitude!!.toDouble(), turtle.longitude!!.toDouble())
            addMarkerToMap(turtle, latLng)
            boundsBuilder.include(latLng)
        }

        mMap.setOnMarkerClickListener { marker ->
            if (marker.tag != null) {
                selectedTurtle.postValue(marker.tag as FetchTurtlesResponseItem?)
            }
            true
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
                    turtleCardName.text = turtle.namaLokal!!.split(",").first()
                    turtleCardLatinName.text = turtle.namaLatin
                    if (turtle.statusKonversi!!.split(" ").contains("dilindungi")){
                        turtleCardStatus.text = "dilindungi"
                        turtleCardStatus.setTextColor(resources.getColor(R.color.green_text))
                    } else {
                        turtleCardStatus.text = "tidak dilindungi"
                        turtleCardStatus.setTextColor(resources.getColor(R.color.red_text))
                    }
                    Glide.with(this@MapsActivity)
                        .load(turtle.image!!.split(", ").first()).centerCrop()
                        .into(turtleCardImage)

                    turtleCard.visibility = View.VISIBLE
                }
            }else{
                binding.turtleCard.visibility = View.GONE
            }
        }
    }
}