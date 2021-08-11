package com.felipechauxlab.yourfarmerapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.yourfarmerapp.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class ProductMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var placeLocation: LatLng? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_map)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        getExtraLocation()
    }

    private fun getExtraLocation() {
        try {
            val params = intent.extras
            placeLocation = params?.getParcelable(getString(R.string.extra_place_location))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Add a marker in Sydney and move the camera
       // val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(placeLocation?.let {
            MarkerOptions().position(it).title("Marker in Sydney")
        })
        mMap.moveCamera(CameraUpdateFactory.newLatLng(placeLocation))
    }
}