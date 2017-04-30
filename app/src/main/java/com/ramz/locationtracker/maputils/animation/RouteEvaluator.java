package com.ramz.locationtracker.maputils.animation;

import android.animation.TypeEvaluator;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by amal.chandran on 22/12/16.
 */

public class RouteEvaluator implements TypeEvaluator<LatLng> {
    @Override
    public LatLng evaluate(float t, LatLng startPoint, LatLng endPoint) {
        if(startPoint!=null&&endPoint!=null) {
            double lat = startPoint.latitude + t * (endPoint.latitude - startPoint.latitude);
            double lng = startPoint.longitude + t * (endPoint.longitude - startPoint.longitude);
            return new LatLng(lat,lng);
        }
        return new LatLng(0,0);
    }
}
