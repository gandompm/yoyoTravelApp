package yoyo.app.android.com.yoyoapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.akexorcist.googledirection.DirectionCallback
import com.akexorcist.googledirection.GoogleDirection
import com.akexorcist.googledirection.constant.Language
import com.akexorcist.googledirection.constant.TransportMode
import com.akexorcist.googledirection.model.Direction
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task

class GoogleMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private var startLatLng: LatLng? = null
    private var finishLatLng: LatLng? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_map)


        val lat1 = intent.extras!!.getDouble("lat1")
        val long1 = intent.extras!!.getDouble("long1")
        val lat2 = intent.extras!!.getDouble("lat2")
        val long2 = intent.extras!!.getDouble("long2")
        startLatLng = LatLng(lat1, long1)
        finishLatLng = LatLng(lat2, long2)
        initMap()
    }

    private fun initMap() {
        Log.d(TAG, "initMap: initializing map")
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this@GoogleMapActivity)
    }

    private fun moveCamera(googleMap: GoogleMap, latLng: LatLng) {
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 6f))
    }

    override fun onMapReady(googleMap: GoogleMap) {

        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json))



        moveCamera(googleMap, startLatLng!!)

        val markerOptions = MarkerOptions()
        markerOptions.position(startLatLng!!)
        markerOptions.title("Start")


        val polyline = googleMap.addPolyline(
            PolylineOptions()
                .clickable(true)
                .add(
                    startLatLng,
                    finishLatLng
                ).geodesic(true)
        )

        polyline.endCap = RoundCap()
        polyline.width = 5f
        polyline.color = Color.RED
        polyline.jointType = JointType.ROUND
        polyline.startCap = RoundCap()
        polyline.endCap = CustomCap(
            BitmapDescriptorFactory.fromResource(R.drawable.arrow),
            16f
        )
    }

    override fun onBackPressed() {
        finish()
    }

    companion object {
        private val TAG = "MapActivity"
    }
}
