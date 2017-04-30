/*******************************************************************************
 * Title: Database Class Handling Menu Details 
 * Authour:Ramesh M Nair (Co founder Voltella)
 *******************************************************************************/

package com.ramz.locationtracker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class LocationDb extends SQLiteOpenHelper {
    public LocationDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    // Declaring Variables

    private static final String DATABASE_NAME = "LocationDb_DB";
    private static final String DATABASE_RECRUITER_TABLE = "LocationTABLE";
    private static final String DATABASE_RECRUITER_ID_TABLE = "LocationIDTABLE";






    private static final int DATABASE_VERSION = 1;

//    private DbHelper ourHelper;


//    private SQLiteDatabase ourDatabase;
    public static String _LATITUDE ="lat_info";
    public static String _LONGITUDE ="lon_info";
    public static String _ID="Id_info";




    private static final String TABLE_INSERT = "create table "
            + DATABASE_RECRUITER_TABLE
            + "("+_ID+" text not null,"+  _LATITUDE+" text not null,"+ _LONGITUDE +" text not null);";
    private static final String TABLE_INSERT_ID = "create table "
            + DATABASE_RECRUITER_ID_TABLE
            + "("+_ID+" text not null);";



    // Insert data to table
    public void insertData(int id,double latitude,double longitude) {

            ContentValues cv = new ContentValues();
            cv.put(_ID, String.valueOf(id));
            cv.put( _LATITUDE, String.valueOf(latitude));
            cv.put(_LONGITUDE, String.valueOf(longitude));
            db.insert(DATABASE_RECRUITER_TABLE, null, cv);


    }
    public void insertSpot(int id) {
        ContentValues cv = new ContentValues();
        cv.put(_ID, String.valueOf(id));
        db.insert(DATABASE_RECRUITER_ID_TABLE, null, cv);


    }

    public ArrayList<String>getLocationData()
    {
        ArrayList<String>id=new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DATABASE_RECRUITER_ID_TABLE + " ", null);
        while (cursor.moveToNext())
        {
            id.add(cursor.getString(cursor
                    .getColumnIndex(_ID)));
        }
        cursor.close();
        return id;

    }

    public ArrayList<LatLng>getLocationPoints(String id)
    {
        ArrayList<LatLng>point=new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DATABASE_RECRUITER_TABLE + " WHERE "+_ID+" = '"+id+"'", null);

        while (cursor.moveToNext())
        {
            double lat =Double.parseDouble(cursor.getString(cursor
                            .getColumnIndex( _LATITUDE)));
            double lng =Double.parseDouble(cursor.getString(cursor
                    .getColumnIndex(_LONGITUDE)));
//            Log.d("over","mdata.add(new LatLng("+lat+","+lng+"));");

            point.add(new LatLng(lat,lng));
        }
        cursor.close();
        return point;

    }

    @Override
    public synchronized void close() {
        if (instance != null) {
            db.close();
            instance=null;
        }
    }
    private static LocationDb instance;
    private static SQLiteDatabase db;


    public static synchronized LocationDb getInstance(Context context) {
        if (instance == null) {
            instance = new LocationDb(context);
            db = instance.getWritableDatabase();

        }

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        // Execte The table

        db.execSQL(TABLE_INSERT);
        db.execSQL(TABLE_INSERT_ID);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub


    }

}