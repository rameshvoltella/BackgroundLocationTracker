package com.ramz.locationtracker;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.ramz.locationtracker.utils.Constants;
import com.ramz.locationtracker.utils.GetLocationPoints;

import java.util.ArrayList;


/**
 * Created by munnaz on 29/4/17.
 */

public class ShowRouteActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,GetLocationPoints.LocationPoints {

GoogleMap googleMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);



    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {


    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap=googleMap;

        new GetLocationPoints(getIntent().getExtras().getString(Constants._Id),getApplicationContext(),this).execute();


    }


    @Override
    public void onResume() {
        super.onResume();
//        mapView.onResume();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();


    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void getPoints(ArrayList<LatLng> points) {
//        Toast.makeText(getApplicationContext(),"<<ypoooo"+points.size(),1).show();
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.BLUE);
        polylineOptions.width(10);
        polylineOptions.addAll(points);
        googleMap.addPolyline(polylineOptions);
        if(points.size()>0) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(points.get(0), 18));
        }



    }

    @Override
    public void getSpot(ArrayList<String> spots) {
//        Toast.makeText(getApplicationContext(),"ypoooo22",1).show();


    }
}
