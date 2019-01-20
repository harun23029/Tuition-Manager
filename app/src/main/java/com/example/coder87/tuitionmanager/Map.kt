package com.example.coder87.tuitionmanager

import android.graphics.Color
import android.location.Geocoder
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.*




class Map : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    var yourLocation=""
    var studentsLocation=""
    var loc = LatLng(23.790811,90.371273 )
    var loc1 = LatLng(23.790811,90.371273 )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        getValues()
        getLatLong()
        printToast()
        val zoomLevel = 10.0f

        mMap.addMarker(MarkerOptions().position(loc).title("Your Location"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc,zoomLevel))

        mMap.addMarker(MarkerOptions().position(loc1).title("Student's Location"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc1,zoomLevel))
    }
    private fun getLatLong(){
        var geocoder=Geocoder(this, Locale.getDefault())

            var addressList = geocoder.getFromLocationName(yourLocation, 1)
            var address = addressList[0]
            loc=LatLng(address.latitude,address.longitude)
            addressList = geocoder.getFromLocationName(studentsLocation, 1)
            address = addressList[0]
            loc1=LatLng(address.latitude,address.longitude)
    }
    fun printToast(){

        val toast = Toast.makeText(this, "Please click on student's location and then direction to see route", Toast.LENGTH_LONG)
        toast.show()
    }
    fun getValues(){
        yourLocation=intent.getStringExtra(yourloc)
        studentsLocation=intent.getStringExtra(stuloc)
    }


}



