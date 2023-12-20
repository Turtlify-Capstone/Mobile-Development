package com.bangkit.turtlify.ui.report

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.bangkit.turtlify.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.bangkit.turtlify.databinding.ActivityLocationSelectorBinding
import com.bangkit.turtlify.utils.vectorToBitmap
import com.google.android.gms.maps.model.Marker

class LocationSelectorActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityLocationSelectorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLocationSelectorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        val defaultLocation = LatLng(-6.8957643, 107.6338462)
        var existingMarker: Marker? = null

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 15f))

        mMap.setOnMapLongClickListener { latLng ->
            existingMarker?.remove()
            val markerOptions = MarkerOptions()
                .position(latLng)
                .snippet("Lat: ${latLng.latitude} Long: ${latLng.longitude}")
                .icon(vectorToBitmap(R.drawable.turtle_marker, resources))
            val marker = mMap.addMarker(markerOptions)
            existingMarker = marker

            binding.btnConfirmLocation.visibility = View.VISIBLE
            binding.btnConfirmLocation.setOnClickListener{
                val data = Intent()
                data.putExtra("latitude", latLng.latitude)
                data.putExtra("longitude", latLng.longitude)
                setResult(Activity.RESULT_OK, data)
                finish()
            }
        }
        getMyLocation()
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
}