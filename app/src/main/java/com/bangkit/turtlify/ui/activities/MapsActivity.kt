package com.bangkit.turtlify.ui.activities

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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

data class TurtlePlace(
    val name: String,
    val imageUrl: String,
    val latitude: Double,
    val longitude: Double
)

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val boundsBuilder = LatLngBounds.Builder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
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
        val turtlePlace = listOf(
            TurtlePlace("Floating Market Lembang", "https://w7.pngwing.com/pngs/487/456/png-transparent-computer-icons-business-logo-youtube-cartoon-green-small-rocket-cartoon-character-painted-hand-thumbnail.png" , -6.8168954,107.6151046),
            TurtlePlace("The Great Asia Africa", "https://w7.pngwing.com/pngs/858/429/png-transparent-rocket-computer-icons-rocket-leaf-spacecraft-desktop-wallpaper.png" , -6.8331128,107.6048483),
            TurtlePlace("Rabbit Town", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSNSwc5P2dILCEaeIhmk4op8bvPgrYQIUnMrBibPdm3uM4JUI-gG9163WFvqWNIu5Ns-9k&usqp=CAU" ,-6.8668408,107.608081),
        )
        turtlePlace.forEach { turtle ->
            val latLng = LatLng(turtle.latitude, turtle.longitude)
            Glide.with(this)
                .asBitmap()
                .apply(RequestOptions().override(50, 50))
                .load(turtle.imageUrl)
                .into(object : SimpleTarget<Bitmap?>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap?>?
                    ) {
                        val marker = mMap.addMarker(MarkerOptions().position(latLng).title(turtle.name))
                        marker!!.setIcon(BitmapDescriptorFactory.fromBitmap(resource))
                        marker.tag = turtle
                    }
                })

            boundsBuilder.include(latLng)
            mMap.setOnMarkerClickListener { marker ->
                if (marker.tag != null) {
                    showTurtleFloatingCard(turtle)
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

    private fun showTurtleFloatingCard(turtlePlace: TurtlePlace){

    }
}