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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.ramz.locationtracker.model.LocationInfo;
import com.ramz.locationtracker.utils.MainEventBus;
import com.squareup.otto.Subscribe;


/**
 * Created by munnaz on 29/4/17.
 */

public class LiveTracking extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

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

    }


    @Override
    public void onResume() {
        super.onResume();
//        mapView.onResume();
        try{
            MainEventBus.getInstance().register(this);
        }
        catch (Exception e)
        {

        }

    }





    @Override
    public void onDestroy() {
        super.onDestroy();

        try{
            MainEventBus.getInstance().unregister(this);
        }
        catch (Exception e)
        {

        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    boolean initialLoading=false;

    @Subscribe
    public void getLocation( LocationInfo data)
    {
if(googleMap!=null) {
    LatLng locationInfo=new LatLng(data.lat, data.lng);
    if(!initialLoading)
    {
        initialLoading=true;
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationInfo, 18));

    }

    googleMap.addMarker(new MarkerOptions()
            .position(locationInfo)
            .title("newLocation")
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin)));
    PolylineOptions polylineOptions = new PolylineOptions();
    polylineOptions.color(Color.BLUE);
    polylineOptions.width(10);
    polylineOptions.add(locationInfo);
    googleMap.addPolyline(polylineOptions);
}

    }

}
