package com.tcpexample.maisondechat

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback
import androidx.databinding.DataBindingUtil
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMapLoadedCallback
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.tcpexample.maisondechat.databinding.MyLocationResultBinding
import me.himanshusoni.gpxparser.GPXParser
import me.himanshusoni.gpxparser.modal.Track
import me.himanshusoni.gpxparser.modal.TrackSegment
import me.himanshusoni.gpxparser.modal.Waypoint
import java.io.FileInputStream
import java.lang.Exception
import java.util.ArrayList
/*
* 地图展示结果展示数据
* */
class MyLocationResultActivity: AppCompatActivity(), OnMapReadyCallback,
    OnRequestPermissionsResultCallback, OnMapLoadedCallback {

    /**
     * Code de demande pour la demande d'autorisation de localisation.
     *
     * @see .onRequestPermissionsResult
     */
    private val LOCATION_PERMISSION_REQUEST_CODE = 1

    /**
     * Indicateur indiquant si une autorisation demandée a été refusée après son retour dans
     * [.onRequestPermissionsResult].
     */
    private var mPermissionDenied = false

    private var mMap: GoogleMap? = null
    private var path: String? =
        null;
    private  var startTime:kotlin.String? = null;
    private  var endTime:kotlin.String? = null;
    private  var duration:kotlin.String? = null;
    private  var durationKm:kotlin.String? = null;
    private  var Speed:kotlin.String? = null

    private var id = 0

    //    private RecordDao recordDao;
    var alertdialog1: AlertDialog? = null


    var myBinding: MyLocationResultBinding? = null
    var mvvmViewModel: MyLocationResultViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //        setContentView(R.layout.my_location_result);
        myBinding = DataBindingUtil.setContentView(this, R.layout.my_location_result)

//        recordDao = AbstractAppDataBase.getInstance(MyLocationResultActivity.this).getRecordDao();
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
        mvvmViewModel = MyLocationResultViewModel(this, myBinding)
        myBinding!!.setViewModel(mvvmViewModel) //初始化viewModel
        path = intent.getStringExtra("path")
        startTime = intent.getStringExtra("startTime")
        endTime = intent.getStringExtra("endTime")
        duration = intent.getStringExtra("duration")
        durationKm = intent.getStringExtra("durationKm")
        Speed = intent.getStringExtra("Speed")
        id = intent.getIntExtra("id", 0)
        myBinding!!.tv1.setText(startTime)
        myBinding!!.tv2.setText(endTime)
        myBinding!!.tv3.setText(duration + "s")
        myBinding!!.tv4.setText(durationKm + "m")
        myBinding!!.tv5.setText(Speed + "m/s")
        myBinding!!.btnDele.setOnClickListener(View.OnClickListener {
            val alertdialogbuilder = AlertDialog.Builder(this@MyLocationResultActivity)
            alertdialogbuilder.setMessage("Are you sure delete this record")
            alertdialogbuilder.setPositiveButton(
                "yes"
            ) { dialog, which ->
                alertdialog1!!.dismiss()
                MyRepository.delRecordBean(
                    this@MyLocationResultActivity,
                    MyRepository.getRecordBean(this@MyLocationResultActivity, id)
                )
                //                        recordDao.DeleteData(recordDao.getRecordBeanByName(id));
                Toast.makeText(this@MyLocationResultActivity, "delete Success", Toast.LENGTH_SHORT)
                    .show()
                finish()
            }
            alertdialogbuilder.setNeutralButton(
                "no"
            ) { dialog, which -> alertdialog1!!.dismiss() }
            alertdialog1 = alertdialogbuilder.create()
            alertdialog1?.show()
        })
    }

    override fun onMapReady(map: GoogleMap?) {
        mMap = map
        mvvmViewModel!!.setmMap(mMap)
        mMap!!.setOnMapLoadedCallback(this)
        mMap!!.setMinZoomPreference(15f)
        //        enableMyLocation();
        setMapDatas()
    }

    /**
     * Active la couche Ma position si l'autorisation de localisation précise a été accordée.
     */
    private fun enableMyLocation() {
        if (mMap != null) {
            // L'accès à l'emplacement a été accordé à l'application.
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
                // ici pour demander les autorisations manquantes, puis en remplaçant
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // pour gérer le cas où l'utilisateur accorde l'autorisation. Voir la documentation
                // pour ActivityCompat#requestPermissions pour plus de détails.
                return
            }
            mMap!!.isMyLocationEnabled = true
        }
    }

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
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private fun showMissingPermissionError() {}

    /*
    * chargement finition
    * */
    override fun onMapLoaded() {}

/*
* Obtenir des données de map
* */
    fun setMapDatas() {
        mvvmViewModel!!.getImageData(startTime)
        val p = GPXParser()
        var `in`: FileInputStream? = null
        try {
            `in` =
                FileInputStream(Environment.getExternalStorageDirectory().toString() + "/" + path)
            val gpx = p.parseGPX(`in`)
            val tracks: Set<Track> = gpx.tracks
            val trackss = tracks.iterator().next()
            val list: List<TrackSegment> = trackss.trackSegments
            val waypoint12 = list[0].waypoints[0]
            mMap!!.moveCamera(
                CameraUpdateFactory.newLatLng(
                    LatLng(
                        waypoint12.latitude,
                        waypoint12.longitude
                    )
                )
            )
            val mMarkerRainbow: MutableList<Marker> = ArrayList()
            val numMarkersInRainbow = 12
            val marker = mMap!!.addMarker(
                MarkerOptions()
                    .position(LatLng(waypoint12.latitude, waypoint12.longitude))
                    .title("Start")
                    .icon(BitmapDescriptorFactory.defaultMarker(0f))
            )
            val waypoint121 = list[0].waypoints[list[0].waypoints.size - 1]
            val marker1 = mMap!!.addMarker(
                MarkerOptions()
                    .position(LatLng(waypoint121.latitude, waypoint121.longitude))
                    .title("end")
                    .icon(BitmapDescriptorFactory.defaultMarker(1f))
            )
            mMarkerRainbow.add(marker1)
            mMarkerRainbow.add(marker)
            for (i in list.indices) {
                val latLngs: MutableList<LatLng> = ArrayList()
                val listways: List<Waypoint> = list[i].waypoints
                for (j in listways.indices) {
                    val waypoint = listways[j]
                    val lati = waypoint.latitude
                    val longitude = waypoint.longitude
                    latLngs.add(LatLng(lati, longitude))
                }
                mMap!!.addPolyline(
                    PolylineOptions().addAll(latLngs)
                        .width(5f)
                        .color(Color.BLUE)
                        .geodesic(true)
                )
            }
            Log.e("sss", gpx.toString())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}