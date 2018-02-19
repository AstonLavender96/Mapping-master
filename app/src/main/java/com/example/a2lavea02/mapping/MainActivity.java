package com.example.a2lavea02.mapping;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.content.Intent;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;

public class MainActivity extends AppCompatActivity{
    MapView mv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mv = (MapView) findViewById(R.id.map1);
        mv.setBuiltInZoomControls(true);

        mv.getController().setZoom(16);

        mv.getController().setCenter(new GeoPoint(51.8361, -0.4577));

    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item)
    {

        if(item.getItemId()== R.id.choosemap)
        {
            // react to the menu being selected.
            Intent intent = new Intent(this, MapChooseActivity.class);
            startActivityForResult(intent,0);
            return true;
        }
        else if(item.getItemId() == R.id.setlocation)
        {
            Intent intent = new Intent(this, SetLocationActivity.class);
            startActivityForResult(intent,1);
            return true;
        }
        return false;
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if(requestCode==0)
        {
            if(resultCode==RESULT_OK)
            {
                Bundle extras=intent.getExtras();
                boolean hikebikemap = extras.getBoolean("com.example.hikebikemap");
                if(hikebikemap==true)
                {
                    mv.setTileSource(TileSourceFactory.HIKEBIKEMAP);
                }
                else
                {
                    mv.setTileSource(TileSourceFactory.MAPNIK);
                }
            }
        }
        else if(requestCode==1)
        {
            if(resultCode==RESULT_OK)
            {
                Bundle extras=intent.getExtras();
                double latitude = extras.getDouble("com.example.setlat");
                double longitude = extras.getDouble("com.example.setlon");
                mv.getController().setCenter(new GeoPoint(latitude, longitude));

            }
        }
    }
    public void onStart()
    {
        super.onStart();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        double lat = Double.parseDouble( prefs.getString("lat", "50.9"));
        double lon = Double.parseDouble( prefs.getString("lon", "-1.4"));
        boolean autodownload = prefs.getBoolean("autodownload", true);
        String POICode = prefs.getString("POI", "0");
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+POICode);
        if(POICode.equals("0")){
                mv.getController().setCenter(new GeoPoint(lat, lon));// Set the map lat and lon to the "lat" and "lon" preferences
            }
            else if (POICode.equals("L")) {
            mv.getController().setCenter(new GeoPoint(51.0, 0.0));// set lat and lon to the lat and lon for London
            }
            else if (POICode.equals("T")) {
            mv.getController().setCenter(new GeoPoint(35.6895, 139.6917));
            }

    }


}
