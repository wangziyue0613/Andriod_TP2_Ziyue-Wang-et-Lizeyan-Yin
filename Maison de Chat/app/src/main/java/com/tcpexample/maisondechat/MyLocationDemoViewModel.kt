package com.tcpexample.maisondechat

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.location.Location
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import com.tcpexample.maisondechat.db.AbstractAppDataBase
import com.tcpexample.maisondechat.db.ImagePhotoBean
import com.tcpexample.maisondechat.db.RecordBean
import com.tcpexample.maisondechat.db.RecordDao
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.tcpexample.maisondechat.databinding.ActivityMainBinding
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Date
import java.util.HashSet
import me.himanshusoni.gpxparser.GPXWriter
import me.himanshusoni.gpxparser.modal.GPX
import me.himanshusoni.gpxparser.modal.Track
import me.himanshusoni.gpxparser.modal.TrackSegment
import me.himanshusoni.gpxparser.modal.Waypoint
import pl.aprilapps.easyphotopicker.EasyImage
import pl.aprilapps.easyphotopicker.MediaFile
import java.io.File

/*
* données d'affichage du Map
* */
class MyLocationDemoViewModel constructor(activity12: Activity?, binding: ActivityMainBinding?): BaseObservable() {
    private var binding: ActivityMainBinding? = binding

    private var activity: Activity? = activity12

    fun MyLocationDemoViewModel() {
        this.binding = binding
        this.activity = activity
        recordDao
    }

    var mMap: GoogleMap? = null

    fun setmMap(mMap: GoogleMap?) {
        this.mMap = mMap
    }

    var easyImage: EasyImage? = null
    @JvmName("setEasyImage1")
    fun setEasyImage(easyImage: EasyImage?) {
        this.easyImage = easyImage
    }

    var path = ""
    var la = 0.0;
    var lo:Double = 0.0
    var latLngsData: MutableList<LatLng> = ArrayList()
    private var dataTime: String? = null;
    private  var endTime: String? = null
    private var time = 0
    private var recordDao: RecordDao? =  AbstractAppDataBase.getInstance(activity!!)?.getRecordDao()
    private val mHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                10008 -> if (_location != null) {
//                        la=la+Math.random()*(-0.0001-0.0001)+0.0001;
//                        lo=lo+Math.random()*(-0.0001-0.0001)+0.0001;
                    la = la
                    lo = lo
                    val latLng = LatLng(la, lo)
                    latLngsData.add(latLng)
                    mMap!!.addPolyline(
                        PolylineOptions().addAll(latLngsData)
                            .width(5f)
                            .color(Color.RED)
                            .geodesic(true)
                    )
                    //                            new
                    time++
                    sendEmptyMessageDelayed(10008, 1000)
                }
                else -> {
                }
            }
        }
    }

    var _location: Location? = null;
    /*
    * Obtenir des enregistrements de Map
    * */
    fun getData(view: View?) {
        if (_location == null) {
            Toast.makeText(activity, "Please start positioning first", Toast.LENGTH_SHORT).show()
            return
        }
        if (binding?.tvPlay?.getText().toString().trim().equals("stop tracking")) {
            binding?.tvPlay?.setText("start tracking")
            mMap!!.clear()
            binding?.tvReDesc?.setVisibility(View.GONE)
            mHandler.removeMessages(10008)
            val d = Date()
            val sbf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            endTime = sbf.format(d)
            var juliDta = 0.0
            for (i in 1 until latLngsData.size) {
                juliDta = juliDta + LocaUtils.getGoogleDistance(latLngsData[i - 1], latLngsData[i])
            }
            val recordBean = RecordBean()
            recordBean.setData(dataTime)
            recordBean.setDisKM(String.format("%.2f", juliDta) + "")
            recordBean.setDuration(time.toString() + "")
            val f = juliDta / time
            recordBean.setSpeed(String.format("%.2f", f))
            recordBean.setStartTime(dataTime)
            recordBean.setEndTime(endTime)
            try {
                val outputStream =
                    FileOutputStream(activity!!.cacheDir.toString() + "/" + dataTime + ".gpx")
                val gpxParser = GPXWriter()
                val gpx = GPX()
                val htrack = HashSet<Track>()
                val trackss = Track()
                val trackSegment = TrackSegment()
                val waypoints = ArrayList<Waypoint>()
                for (i in latLngsData.indices) {
                    val waypoint = Waypoint(latLngsData[i].latitude, latLngsData[i].longitude)
                    waypoints.add(waypoint)
                    trackSegment.waypoints = waypoints
                }
                trackSegment.waypoints = waypoints
                //                        list.add(trackSegment);
                trackss.addTrackSegment(trackSegment)
                htrack.add(trackss)
                gpx.tracks = htrack
                gpxParser.writeGPX(gpx, outputStream)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            recordBean.setPath("$dataTime.gpx")
            recordDao?.insertData(recordBean)
            latLngsData.clear()
        } else {
            mMap!!.setMinZoomPreference(18f)
            time = 0
            binding?.tvReDesc?.setVisibility(View.VISIBLE)
            binding?.tvPlay?.setText("stop tracking")
            _location = mMap!!.myLocation
            mMap!!.moveCamera(
                CameraUpdateFactory.newLatLng(
                    LatLng(
                        _location?.latitude!!,
                        _location?.longitude!!
                    )
                )
            )
            latLngsData.clear()
            mMap!!.clear()
            la = _location?.latitude!!
            lo = _location?.longitude!!
            val d = Date()
            val sbf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            dataTime = sbf.format(d)
            mHandler.sendEmptyMessageDelayed(10008, 1000)
        }

    }
/*
* Record de saut
* */
    fun getRecord(view: View?) {
        activity!!.startActivity(Intent(activity, RecordListActivity::class.java))
    }

    /*
    * Résultats de positionnement
    * */
    fun setLocation(location: Location?) {
        _location = location
    }
    /*
      * Sélectionner une image
      * */
    fun pickimage(view: View?) {
        if (_location == null) {
            Toast.makeText(activity, "Please start positioning first", Toast.LENGTH_SHORT).show()
            return
        }
        easyImage!!.openChooser(activity!!)
    }
    /*
        * Sélectionnez l'analyse des résultats d'image
        * */
    fun setMediaFiles(imageFiles: Array<MediaFile>?) {
        if (imageFiles != null && imageFiles.size > 0) {
            val file:File = imageFiles[0].file
            val markerOptions = MarkerOptions()
            val bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(
                BitmapUtils.changeBitmapSize(
                    BitmapUtils.getBitmapByFile(
                        file
                    )!!
                )
            )
            markerOptions.icon(bitmapDescriptor)
            val imagePhotoBean = ImagePhotoBean()
            imagePhotoBean.setJd(_location!!.longitude)
            imagePhotoBean.setWd(_location!!.latitude)
            imagePhotoBean.setName(dataTime)
            imagePhotoBean.setPath(file.path)
            MyRepository.insertImageData(activity, imagePhotoBean)
            //            imagePhotoDao.insertData(imagePhotoBean);

//                            imagePhotoDao.insertData();

//            markerOptions.
            markerOptions.position(LatLng(_location!!.latitude, _location!!.longitude))
            //                            markerOptions.anchor(0.5f, 1.0f);
            val marker = mMap!!.addMarker(markerOptions)
            marker.tag = file.path
            mMap!!.setOnMarkerClickListener { marker ->
                Log.e("tag", marker.tag.toString())
                activity!!.startActivity(
                    Intent(activity, MyPicImageDetailActivity::class.java)
                        .putExtra("imagePath", marker.tag.toString())
                )
                true
            }
        }
    }
}