package yoyo.app.android.com.yoyoapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.Language;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
public class GoogleMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private LatLng startLatLng;
    private LatLng finishLatLng;
    private static final String TAG = "MapActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);


        double lat1 = getIntent().getExtras().getDouble("lat1");
        double long1 = getIntent().getExtras().getDouble("long1");
        double lat2 = getIntent().getExtras().getDouble("lat2");
        double long2 = getIntent().getExtras().getDouble("long2");
        startLatLng = new LatLng(lat1,long1);
        finishLatLng = new LatLng(lat2,long2);
        initMap();
    }

    private void initMap(){
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(GoogleMapActivity.this);
    }

    private void moveCamera(GoogleMap googleMap, LatLng latLng){
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 6));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this,R.raw.style_json));



        moveCamera(googleMap, startLatLng);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(startLatLng);
        markerOptions.title("Start");


        Polyline polyline = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        startLatLng,
                        finishLatLng
                ).geodesic(true));

        polyline.setEndCap(new RoundCap());
        polyline.setWidth(5);
        polyline.setColor(Color.RED);
        polyline.setJointType(JointType.ROUND);
        polyline.setStartCap(new RoundCap());
        polyline.setEndCap(
                new CustomCap(BitmapDescriptorFactory.fromResource(R.drawable.arrow),
                        16));
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
