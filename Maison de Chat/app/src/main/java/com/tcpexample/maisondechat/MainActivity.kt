package com.tcpexample.maisondechat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback
import com.google.android.gms.maps.GoogleMap.*
import com.google.android.gms.maps.OnMapReadyCallback
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener
import com.google.android.gms.maps.SupportMapFragment
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.tcpexample.maisondechat.databinding.ActivityMainBinding


import pl.aprilapps.easyphotopicker.DefaultCallback

import pl.aprilapps.easyphotopicker.EasyImage

import pl.aprilapps.easyphotopicker.MediaFile

import pl.aprilapps.easyphotopicker.MediaSource

/*
* La classe qui affiche la carte
* */
class MainActivity : AppCompatActivity(), OnMyLocationButtonClickListener,
    OnMyLocationClickListener,
    OnMapReadyCallback,
    OnRequestPermissionsResultCallback, OnMapLoadedCallback {
    private val LOCATION_PERMISSION_REQUEST_CODE = 1
    private var mPermissionDenied = false

    private var mMap: GoogleMap? = null
    private var myBinding: ActivityMainBinding? = null
    private var mvvmViewModel: MyLocationDemoViewModel? = null
    var easyImage: EasyImage? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myBinding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
        mvvmViewModel = MyLocationDemoViewModel(this, myBinding)
        myBinding!!.setViewModel(mvvmViewModel) //initialiser viewModel
        easyImage =  EasyImage.Builder(this)
            .setCopyImagesToPublicGalleryFolder(false) // Sets the name for images stored if setCopyImagesToPublicGalleryFolder = true
            //                .setFolderName("EasyImage sample")
            // Allow multiple picking
            .allowMultiple(true)
            .build()
        mvvmViewModel!!.setEasyImage(easyImage)
    }
/*
* Rappel pour la sélection d'images
* */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        easyImage?.handleActivityResult(
            requestCode,
            resultCode,
            data,
            this@MainActivity,
            object : DefaultCallback() {
                override fun onImagePickerError(error: Throwable, source: MediaSource) {
                    //Some error handling
                    error.printStackTrace()
                }
                override fun onMediaFilesPicked(imageFiles: Array<MediaFile>, source: MediaSource) {
                    mvvmViewModel!!.setMediaFiles(imageFiles)
                }
                override fun onCanceled(source: MediaSource) {
                    //Not necessary to remove any files manually anymore
                }
            })
    }
    /*Rappel pour l'achèvement du chargement de la carte
    * */
    override fun onMapReady(map: GoogleMap?) {
        mMap = map
        mvvmViewModel!!.setmMap(map)
        mMap!!.setOnMyLocationButtonClickListener(this)
        mMap!!.setOnMyLocationClickListener(this)
        mMap!!.setOnMapLoadedCallback(this)
        mMap!!.setOnMyLocationChangeListener { location -> mvvmViewModel!!.setLocation(location) }
        enableMyLocation()
    }
    /**
     * Active la couche Ma position si l'autorisation de localisation précise a été accordée.
     */
    private fun enableMyLocation() {
        if (mMap != null) {
            // Access to the location has been granted to the app.
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE
                ) //

                return
            }
            mMap!!.isMyLocationEnabled = true
        }
    }

    override fun onMyLocationButtonClick(): Boolean {
//        Toast.makeText(this, "MyLocation", Toast.LENGTH_SHORT).show();
        // Renvoyez false afin que nous ne consommons pas l'événement et que le comportement par défaut se produise toujours.
        // (la caméra s'anime à la position actuelle de l'utilisateur).
        return false
    }

    private var _location: Location? = null
    override fun onMyLocationClick(location: Location) {
        _location = location
        mvvmViewModel!!.setLocation(location)
    }
/*
* Rappel pour demande d'autorisation
* */
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return
        }
        enableMyLocation()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        if (mPermissionDenied) {
            // L'autorisation n'a pas été accordée, afficher la boîte de dialogue d'erreur.
            showMissingPermissionError()
            mPermissionDenied = false
        }
    }

    /**
     * Affiche une boîte de dialogue avec un message d'erreur expliquant que l'autorisation de localisation est manquante.
     */
    private fun showMissingPermissionError() {}

    override fun onMapLoaded() {}
}