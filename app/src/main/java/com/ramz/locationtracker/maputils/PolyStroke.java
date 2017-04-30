package com.ramz.locationtracker.maputils;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.ramz.locationtracker.maputils.polyline.StrokedPolylineOptions;
import com.ramz.locationtracker.utils.Constants;

/**
 * Created by munnaz on 30/4/17.
 */

public class PolyStroke {
    public static PolyStroke instance=null;
    public static synchronized PolyStroke getInstance() {
        if (instance == null) {
            instance = new PolyStroke();

        }

        return instance;
    }
    public synchronized StrokedPolylineOptions getStrokedPolylineOptions(Context context)
    {

        int fillColor = ContextCompat.getColor(context, Constants.FILL_COLOR);
        int strokeColor = ContextCompat.getColor(context, Constants.STROKE_COLOR);

        return new StrokedPolylineOptions()
                .width(Constants.POLYLINE_WIDTH_IN_PIXELS)
                .fillColor(fillColor)
                .strokeColor(strokeColor)
                .strokeWidth(Constants.POLYLINE_STROKE_WIDTH_IN_PIXELS);
    }
}
