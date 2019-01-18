package com.example.coder87.tuitionmanager

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class Map : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val zoomLevel = 16.0f
        val loc = LatLng(23.790811,90.371273 )
        mMap.addMarker(MarkerOptions().position(loc).title("Marker in Sewrapara"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc,zoomLevel))
    }
}


