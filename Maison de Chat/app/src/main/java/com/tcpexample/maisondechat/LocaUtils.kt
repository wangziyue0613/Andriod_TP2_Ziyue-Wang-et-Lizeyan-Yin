package com.tcpexample.maisondechat

import com.google.android.gms.maps.model.LatLng

/*
* Obtenir la distance réelle en fonction de la latitude et de la longitude
* */
object LocaUtils {
    /**
     *
     * @param start
     * @param end
     * @return
     */
    fun getGoogleDistance(start: LatLng, end: LatLng): Double {
        val lat1 = Math.PI / 180 * start.latitude
        val lat2 = Math.PI / 180 * end.latitude
        val lon1 = Math.PI / 180 * start.longitude
        val lon2 = Math.PI / 180 * end.longitude
        val R = 6371.0 //
        val d = (Math.acos(
            Math.sin(lat1) * Math.sin(lat2) + (Math.cos(lat1) * Math.cos(lat2)
                    * Math.cos(lon2 - lon1))
        )
                * R)
        return d * 1000
    }
}