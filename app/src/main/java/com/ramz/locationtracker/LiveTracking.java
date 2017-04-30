package com.ramz.locationtracker;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
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
import com.ramz.locationtracker.model.LocationInfo;
import com.ramz.locationtracker.utils.Constants;
import com.ramz.locationtracker.utils.MainEventBus;
import com.ramz.locationtracker.maputils.polyline.StrokedPolyline;
import com.ramz.locationtracker.maputils.polyline.StrokedPolylineOptions;
import com.squareup.otto.Subscribe;

import java.util.List;


/**
 * Created by munnaz on 29/4/17.
 */

public class LiveTracking extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    GoogleMap googleMap;
    boolean initialLoading = false;
    private StrokedPolyline polyline;


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
        this.googleMap = googleMap;

    }


    @Override
    public void onResume() {
        super.onResume();
        try {
            MainEventBus.getInstance().register(this);
        } catch (Exception e) {

        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        try {
            MainEventBus.getInstance().unregister(this);
        } catch (Exception e) {

        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Subscribe
    public void getLocation(LocationInfo data) {
        if (googleMap != null) {
            LatLng locationInfo = new LatLng(data.lat, data.lng);
            if (!initialLoading) {
                initialLoading = true;
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationInfo, 18));
                googleMap.addMarker(new MarkerOptions()
                        .position(locationInfo)
                        .title("start_point")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.green_small_pin)));
                ensurePolyline();


            }

            List<LatLng> points = polyline.getPoints();
            points.add(locationInfo);
            polyline.setPoints(points);
        }

    }
    private void ensurePolyline() {
        if (polyline != null) {
            return;
        }

        int fillColor = ContextCompat.getColor(this, Constants.FILL_COLOR);
        int strokeColor = ContextCompat.getColor(this, Constants.STROKE_COLOR);

        StrokedPolylineOptions polylineOptions = new StrokedPolylineOptions()
                .width(Constants.POLYLINE_WIDTH_IN_PIXELS)
                .fillColor(fillColor)
                .strokeColor(strokeColor)
                .strokeWidth(Constants.POLYLINE_STROKE_WIDTH_IN_PIXELS);
        polyline = polylineOptions.addPolylineTo(googleMap);
    }

}
