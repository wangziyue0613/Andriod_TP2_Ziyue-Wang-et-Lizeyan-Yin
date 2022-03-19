package com.tcpexample.maisondechat

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.CameraUpdateFactory

import com.google.android.gms.maps.GoogleMap


import com.google.android.gms.maps.SupportMapFragment

import com.google.android.gms.maps.model.LatLng

import com.google.android.gms.maps.model.MarkerOptions

/*
* affichage de la carte
* */
class MapsActivity : FragmentActivity(), OnMapReadyCallback {
    private var mMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtenez le SupportMapFragment et soyez averti lorsque la carte est prête à être utilisée.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }


    /**
     * Manipule la carte une fois disponible.
     * Ce rappel est déclenché lorsque la carte est prête à être utilisée.
     * C'est ici que nous pouvons ajouter des marqueurs ou des lignes, ajouter des auditeurs ou déplacer la caméra. Dans ce cas,
     * nous ajoutons juste un marqueur près de Sydney, Australie.
     * Si les services Google Play ne sont pas installés sur l'appareil, l'utilisateur sera invité à installer
     * à l'intérieur de SupportMapFragment. Cette méthode ne sera déclenchée qu'une fois que l'utilisateur aura
     * installé les services Google Play et retourné à l'application.
     */
    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap

        // Ajouter un marqueur à Sydney et déplacer la caméra
        val sydney = LatLng(-34.0, 151.0)
        mMap!!.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}