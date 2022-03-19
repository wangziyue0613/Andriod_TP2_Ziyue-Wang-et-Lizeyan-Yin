package com.tcpexample.maisondechat

import android.app.Activity
import android.content.Intent
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.model.*

import com.tcpexample.maisondechat.databinding.MyLocationResultBinding

import com.tcpexample.maisondechat.db.ImagePhotoBean

import com.tcpexample.maisondechat.db.RecordBean


import com.google.android.gms.maps.model.BitmapDescriptorFactory

import com.google.android.gms.maps.model.LatLng

import com.google.android.gms.maps.model.MarkerOptions
import java.io.File

import java.util.ArrayList


class MyLocationResultViewModel constructor(activity: Activity, binding: MyLocationResultBinding?) {
    private var binding: MyLocationResultBinding? = binding
    private var mvvmModel: MyLocationResultModel? = MyLocationResultModel(activity.application)
    private val list: List<RecordBean>? = ArrayList<RecordBean>()
    private var activity: Activity? = activity


    var mMap: GoogleMap? = null
    fun setmMap(mMap: GoogleMap?) {
        this.mMap = mMap
    }

    fun getImageData(name: String?) {
        val imagePhotoBeanList: List<ImagePhotoBean> =
            MyRepository.getAllImagePhotoBean(activity, name) as List<ImagePhotoBean>
        for (i in imagePhotoBeanList.indices) {
            val markerOptions = MarkerOptions()
            val bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(
                BitmapUtils.changeBitmapSize(
                    BitmapUtils.getBitmapByFile(File(imagePhotoBeanList[i].getPath()))!!
                )
            )
            markerOptions.icon(bitmapDescriptor)

//            markerOptions.
            markerOptions.position(
                LatLng(
                    imagePhotoBeanList[i].getWd()!!,
                    imagePhotoBeanList[i].getJd()!!
                )
            )
            //                            markerOptions.anchor(0.5f, 1.0f);
            val marker = mMap!!.addMarker(markerOptions)
            marker.tag = imagePhotoBeanList[i].getPath()
            mMap!!.setOnMarkerClickListener(OnMarkerClickListener { marker ->
                if (marker.tag == null) {
                    return@OnMarkerClickListener false
                }
                //                    Log.e("tag",marker.getTag().toString());
                activity!!.startActivity(
                    Intent(activity, MyPicImageDetailActivity::class.java)
                        .putExtra("imagePath", marker.tag.toString())
                )
                true
            })
        }
    }

}