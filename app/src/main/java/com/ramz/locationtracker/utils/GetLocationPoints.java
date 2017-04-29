package com.ramz.locationtracker.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.ramz.locationtracker.database.RecuriterDb;
import com.ramz.locationtracker.services.BackgroundLocationService;

import java.util.ArrayList;

/**
 * Created by munnaz on 29/4/17.
 */

public class GetLocationPoints extends AsyncTask<String,String,String> {
    String id;
    Context context;
    ArrayList<LatLng>points=new ArrayList<>();
    ArrayList<String>spot=new ArrayList<>();
    LocationPoints mLocationPoints;
    public GetLocationPoints(String id, Context context,LocationPoints mLocationPoints)
    {
        this.id=id;
        this.context=context;
        this.mLocationPoints=mLocationPoints;
    }
    @Override
    protected String doInBackground(String... strings) {
        if(id!=null) {
            Log.d("over","going id");
            points = RecuriterDb.getInstance(context).getLocationPoints(id);

        }
        else {
            Log.d("over","going spot");
            spot= RecuriterDb.getInstance(context).getLocationData();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(!BackgroundLocationService.IS_SERVICE_RUNNING) {
            Log.d("db","closing");
            RecuriterDb.getInstance(context).close();
        }
        Log.d("over","Yaaa"+points.size());

        if(id!=null) {
            mLocationPoints.getPoints(points);
            Log.d("over","iff"+points.size());
        }
        else {
            mLocationPoints.getSpot(spot);
            Log.d("over","else"+points.size());
        }
    }
    public interface LocationPoints
    {
        void getPoints(ArrayList<LatLng> points);
        void getSpot(ArrayList<String> spots);
    }
}
