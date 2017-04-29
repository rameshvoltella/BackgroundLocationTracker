package com.ramz.locationtracker;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.model.LatLng;
import com.ramz.locationtracker.adaptor.SpotAdaptor;
import com.ramz.locationtracker.services.BackgroundLocationService;
import com.ramz.locationtracker.utils.Constants;
import com.ramz.locationtracker.utils.GetLocationPoints;
import com.ramz.locationtracker.utils.RecyclerItemLongClickListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetLocationPoints.LocationPoints, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    public static final int PERMISSIONS_MULTIPLE_REQUEST = 123;
    public static final int REQUEST_CHECK_SETTINGS = 0x1;
    LocationRequest mLocationRequest;

    RecyclerView recyclerView;
    Button trackLocationBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        trackLocationBtn = (Button) findViewById(R.id.btn_info);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {

                requestPermissions(new String[]{

                                Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_MULTIPLE_REQUEST);
            } else {
                buildGoogleApiClient();

                new GetLocationPoints(null, getApplicationContext(), this).execute();
            }


        } else {
            buildGoogleApiClient();

            new GetLocationPoints(null, getApplicationContext(), this).execute();
        }


        recyclerView.addOnItemTouchListener(new RecyclerItemLongClickListener(getApplicationContext(), recyclerView, new RecyclerItemLongClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                startActivity(new Intent(getApplicationContext(), ShowRoot.class).putExtra(Constants._Id, spot.get(position)));

            }

            @Override
            public void onItemLongClick(View view, int position) {


            }
        }));


        if (!BackgroundLocationService.IS_SERVICE_RUNNING) {
            trackLocationBtn.setText("Start Tracking");
        } else {
            trackLocationBtn.setText("Stop Tracking");

        }

    }

    public void startService(View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
                requestPermissions(new String[]{

                                Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_MULTIPLE_REQUEST);
            } else

            {
//        startService(new Intent(this, BackgroundLocationService.class));
                Intent service = new Intent(MainActivity.this, BackgroundLocationService.class);
                if (!BackgroundLocationService.IS_SERVICE_RUNNING) {
                    service.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
                    BackgroundLocationService.IS_SERVICE_RUNNING = true;
                    trackLocationBtn.setText("Stop Tracking");

                } else {
                    service.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
                    BackgroundLocationService.IS_SERVICE_RUNNING = false;
                    trackLocationBtn.setText("Start Tracking");


                }
                startService(service);
            }
        } else {
            Intent service = new Intent(MainActivity.this, BackgroundLocationService.class);
            if (!BackgroundLocationService.IS_SERVICE_RUNNING) {
                service.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
                BackgroundLocationService.IS_SERVICE_RUNNING = true;

                trackLocationBtn.setText("Stop Tracking");
            } else {
                service.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
                BackgroundLocationService.IS_SERVICE_RUNNING = false;
                trackLocationBtn.setText("Start Tracking");


            }
            startService(service);
        }


    }

    public void liveTrack(View v) {
        Intent service = new Intent(MainActivity.this, BackgroundLocationService.class);
        if (!BackgroundLocationService.IS_SERVICE_RUNNING) {
            service.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
            BackgroundLocationService.IS_SERVICE_RUNNING = true;
            startService(service);

        }
        trackLocationBtn.setText("Stop Tracking");
        startActivity(new Intent(MainActivity.this, LiveTracking.class));

    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case PERMISSIONS_MULTIPLE_REQUEST:
                if (grantResults.length > 0) {
                    boolean locationPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
//                    boolean readExternalFile = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (locationPermission) {
                        // write your logic here
                        Toast.makeText(getApplicationContext(), "Start tracking", Toast.LENGTH_LONG).show();
                        new GetLocationPoints(null, getApplicationContext(), this).execute();
                        buildGoogleApiClient();


                    } else {
                        Toast.makeText(getApplicationContext(), "App is not Alive give permisson", Toast.LENGTH_LONG).show();

                    }
                }
                break;
        }
    }

    @Override
    public void getPoints(ArrayList<LatLng> points) {
    }

    ArrayList<String> spot = new ArrayList<>();

    @Override
    public void getSpot(ArrayList<String> spots) {
        spot.clear();
        spot.addAll(spots);
        recyclerView.setAdapter(new SpotAdaptor(spot, getApplicationContext()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
// Check for the integer request code originally supplied to startResolutionForResult().

            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case RESULT_OK:
                        break;
                    case RESULT_CANCELED:
                        Toast.makeText(getApplicationContext(), "Enable Location Otherwise tracking will not be accurate", Toast.LENGTH_LONG).show();
                        break;
                }
                break;
        }
    }

    GoogleApiClient mGoogleApiClient;

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        }
        settingsrequest();

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    public void settingsrequest() {
//		LocationRequest locationRequest = LocationRequest.create();
//		locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//		locationRequest.setInterval(30 * 1000);
//		locationRequest.setFastestInterval(5 * 1000);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        builder.setAlwaysShow(true); //this is the key ingredient

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
//				final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
//                        Toast.makeText(getApplicationContext(),"Sucess",1).show();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
//                            // Show the dialog by calling (),
//                            // and check the result in onActivityResult().
                            status.startResolutionForResult(MainActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });
    }

}
