package com.example.task

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_map.*

class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var map: GoogleMap


    private var latitude: Double= 0.toDouble()
    private var longitude: Double= 0.toDouble()
    private var uMarker:Marker?=null

    //location
    lateinit var  fusedLocationProviderClinent : FusedLocationProviderClient
    lateinit var  locationRequest : LocationRequest
    lateinit var  locationCallBack : LocationCallback

    private lateinit var uLastLocation : Location

    
    //declare another city, I chose the capital of tunisia which is about 120 kl from where İ live
    val othercity = LatLng(36.803323, 10.180948)

    //lateinit var mFusedLocationClient: FusedLocationProviderClient


    companion object {
        private  const val MY_PERMİSSION_CODE : Int =1000

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        //setting up the support fragment
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkLocationPermission()){
                buildLocationRequest()
                buildLocationCallBack()

                fusedLocationProviderClinent = LocationServices.getFusedLocationProviderClient(this)
                fusedLocationProviderClinent.requestLocationUpdates(locationRequest,locationCallBack,
                    Looper.myLooper())

            }
        }


    }
    private fun checkLocationPermission() :Boolean {

        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale( this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ))



                return false
        }
        else
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),MY_PERMİSSION_CODE)
        return true

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        when(requestCode){
            MY_PERMİSSION_CODE->if(grantResults.size> 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                //if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION==PackageManager.PERMISSION_GRANTED))
                if(ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                {
                    if(checkLocationPermission()){
                        map.isMyLocationEnabled=true
                    }

                }
            }
            else{
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()


            }
        }
    }

    private fun buildLocationCallBack() {
        locationCallBack = object  : LocationCallback(){
            override fun onLocationResult(p0: LocationResult?) {
                uLastLocation= p0!!.locations.get(p0!!.locations.size-1)
                if(uMarker != null)
                {
                    uMarker!!.remove()

                }
                //set up markers
                latitude= uLastLocation.latitude
                longitude = uLastLocation.longitude

                val latLng = LatLng(latitude,longitude)

                val marker1Options= MarkerOptions()
                    .position(latLng)
                    .title("your position")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))

                uMarker= map.addMarker(marker1Options)
                map.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                map.animateCamera(CameraUpdateFactory.zoomBy(5f))

                //add another marker
                val marker2Options= MarkerOptions()
                    .position(othercity)
                    .title("your destination")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))

                uMarker= map.addMarker(marker2Options)


                val distance = calculateDistance(latLng,othercity)
                distance_text.text = getString(R.string.route_distance_value)+distance
            }
        }


    }
    //this function takes two cities coordinates and calculates the distance in between
    private fun calculateDistance (lonlat1:LatLng,lonlat2:LatLng): String{

        //the one element array containing the result distance on the
        var arrayOfResult = FloatArray(1)

        //Location.distanceBetween(36.803323, 10.180948,36.803323, 10.180948,arrayOfResult)

        //we use the Location.dıstance between to calculate the dıstance between two cities
        Location.distanceBetween(lonlat1.longitude, lonlat1.latitude,lonlat2.longitude, lonlat2.latitude,arrayOfResult)
        //Log.i("distanceis",arrayOfResult[0].toString())

        //convert to kilometer
        val enKl : Float =arrayOfResult[0]/1000
        //Log.i("distanceisenKilo",enKl.toString())
        return enKl.toString()
    }


    private fun buildLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.priority=LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 3000
        locationRequest.smallestDisplacement = 10f

    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {}
        }
        else{
            map.isMyLocationEnabled=true
            map.uiSettings.isZoomControlsEnabled = true

        }

    }
}
