package com.ramz.locationtracker;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;

import com.ramz.locationtracker.maputils.PolyStroke;
import com.ramz.locationtracker.utils.Constants;
import com.ramz.locationtracker.utils.GetLocationPoints;
import com.ramz.locationtracker.maputils.animation.MapAnimator;
import com.ramz.locationtracker.maputils.polyline.StrokedPolyline;
import com.ramz.locationtracker.maputils.polyline.StrokedPolylineOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by munnaz on 29/4/17.
 */

public class ShowRouteActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,GetLocationPoints.LocationPoints {

    GoogleMap googleMap;


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
    List<PatternItem> pattern = Arrays.<PatternItem>asList(
            new Dot(), new Gap(20));

    ArrayList<LatLng> pointsData=new ArrayList<>();
    @Override
    public void getPoints(ArrayList<LatLng> points) {
        pointsData.addAll(points);
        if(pointsData.size()>=2) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(pointsData.get(0));
            builder.include(pointsData.get((pointsData.size()-1)));
            LatLngBounds bounds = builder.build();
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 200);
            googleMap.addMarker(new MarkerOptions()
                    .position(pointsData.get(0))
                    .title("Start")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.green_small_pin)));
            googleMap.addMarker(new MarkerOptions()
                    .position(pointsData.get((pointsData.size()-1)))
                    .title("Stop")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.pink_pin)));

            googleMap.moveCamera(cu);
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(16), 2000, null);
            startAnim();
        }
        else
        {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(points.get(0), 18));
        }

         /*
       //Just Add uncomment this line and comment startAnim() if u dont need animation

        ensurePolyline();
        polyline.setPoints(points);

        */



    }

    @Override
    public void getSpot(ArrayList<String> spots) {
//        Toast.makeText(getApplicationContext(),"ypoooo22",1).show();


    }

    /*Add this if you dont need animation*/
    private void ensurePolyline() {
        if (polyline != null) {
            return;
        }

        polyline = PolyStroke.getInstance().getStrokedPolylineOptions(getApplicationContext()).addPolylineTo(googleMap);
    }


    private void startAnim(){
        if(googleMap != null) {
            MapAnimator.getInstance().animateRoute(googleMap, pointsData,getApplicationContext());
        } else {
            Toast.makeText(getApplicationContext(), "Map not ready", Toast.LENGTH_LONG).show();
        }
    }
}
