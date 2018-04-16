package com.example.a2lavea02.mapping;

import android.app.AlertDialog;
import android.content.SharedPreferences;

import org.osmdroid.config.Configuration;

import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import android.location.LocationManager;
import android.location.LocationListener;
import android.location.Location;
import android.content.Context;
import android.widget.Toast;

import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import android.util.Log;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LocationListener {
    MapView mv;
    int TEST = 0;
    boolean doNotRepositionOnResume = false;
    ItemizedIconOverlay<OverlayItem> items;
    ItemizedIconOverlay.OnItemGestureListener<OverlayItem> markerGestureListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));

        mv = (MapView) findViewById(R.id.map1);
        mv.setBuiltInZoomControls(true);

        mv.getController().setZoom(16);

        mv.getController().setCenter(new GeoPoint(51.8361, -0.4577));

        markerGestureListener = new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
            public boolean onItemLongPress(int i, OverlayItem item) {
                Toast.makeText(MainActivity.this, item.getSnippet(), Toast.LENGTH_SHORT).show();
                return true;
            }

            public boolean onItemSingleTapUp(int i, OverlayItem item) {
                Toast.makeText(MainActivity.this, item.getSnippet(), Toast.LENGTH_SHORT).show();
                return true;
            }
        };
        items = new ItemizedIconOverlay<OverlayItem>(this, new ArrayList<OverlayItem>(), markerGestureListener);
        OverlayItem markyate = new OverlayItem("Markyate", "Village of Markyate", new GeoPoint(51.8360, -0.4606));
        markyate.setMarker(getResources().getDrawable(R.drawable.marker));
        items.addItem(markyate);
        items.addItems(csvtopoi());
        items.addItem(new OverlayItem("Luton", "Not so great town", new GeoPoint(51.8781, -0.4149)));
        mv.getOverlays().add(items);
        LocationManager mgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    public void onLocationChanged(Location newLoc) {
        mv.getController().setCenter(new GeoPoint(newLoc.getLatitude(), newLoc.getLongitude()));
    }


    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Provider" + provider + " disabled", Toast.LENGTH_SHORT).show();
    }

    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Provider" + provider + " enabled", Toast.LENGTH_SHORT).show();
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
        Toast.makeText(this, "Status Changed: " + status, Toast.LENGTH_SHORT).show();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.choosemap) {
            // react to the menu being selected.
            Intent intent = new Intent(this, MapChooseActivity.class);
            startActivityForResult(intent, 0);
            return true;
        } else if (item.getItemId() == R.id.setlocation) {
            Intent intent = new Intent(this, SetLocationActivity.class);
            startActivityForResult(intent, 1);
            return true;
        } else if (item.getItemId() == R.id.prefmap) {
            Intent intent = new Intent(this, Preference_Activity.class);
            startActivityForResult(intent, 2);
            return true;
        } else if (item.getItemId() == R.id.map) {
            Intent intent = new Intent(this, ListViewActivity.class);
            startActivityForResult(intent, 0);
            return true;
        }
        return false;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                TEST = 1;
                Bundle extras = intent.getExtras();
                boolean hikebikemap = extras.getBoolean("com.example.a2lavea02.mapping.hikebikemap");
                if (hikebikemap == true) {
                    mv.setTileSource(TileSourceFactory.HIKEBIKEMAP);
                } else {
                    mv.setTileSource(TileSourceFactory.MAPNIK);
                }
            }
        } else if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                TEST = 1;
                Bundle extras = intent.getExtras();
                double latitude = extras.getDouble("com.example.a2lavea02.mapping.setlat");
                double longitude = extras.getDouble("com.example.a2lavea02.mapping.setlon");
                mv.getController().setCenter(new GeoPoint(latitude, longitude));
                doNotRepositionOnResume = true;
            }
        }

    }

    public void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        double lat = Double.parseDouble(prefs.getString("lat", "50.9"));
        double lon = Double.parseDouble(prefs.getString("lon", "-1.4"));
        boolean autodownload = prefs.getBoolean("autodownload", true);
        String POICode = prefs.getString("POI", "H");
        String MapViewCodes = prefs.getString("MapView", "B");
        int zoom = Integer.parseInt(prefs.getString("zoom", "16"));
        mv.getController().setZoom(zoom);


        if (doNotRepositionOnResume == false) {
            if (POICode.equals("H")) {
                mv.getController().setCenter(new GeoPoint(lat, lon));// Set the map lat and lon to the "lat" and "lon" preferences
            } else if (POICode.equals("L")) {
                mv.getController().setCenter(new GeoPoint(51.0, 0.0));// set lat and lon to the lat and lon for London
            } else if (POICode.equals("T")) {
                mv.getController().setCenter(new GeoPoint(35.6895, 139.6917));
            } else if (POICode.equals("C")) {
                mv.getController().setCenter(new GeoPoint(35.1264, 33.3299));
            } else if (POICode.equals("N")) {
                mv.getController().setCenter(new GeoPoint(35.1814, 136.9064));
            }

            if (MapViewCodes.equals("B")) {
                mv.setTileSource(TileSourceFactory.MAPNIK);
            } else if (MapViewCodes.equals("HB")) {
                mv.setTileSource(TileSourceFactory.HIKEBIKEMAP);
            }
        }
        TEST = 0;
        doNotRepositionOnResume = false;
    }

    private ArrayList<OverlayItem> csvtopoi() {
        ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
        try {
            FileReader fr = new FileReader(Environment.getExternalStorageDirectory().getAbsolutePath() + "/poi.txt");
            BufferedReader reader = new BufferedReader(fr);
            String line = "";
            while ((line = reader.readLine()) != null) {

                String[] components = line.split(",");

                //The Crown,pub,nice pub,-1.4011,50.9319
                String title = components[0];
                String type = components[1];
                String description = components[2];
                String longstr = components[3];
                String latstr = components[4];
                Double lon = Double.parseDouble(longstr);
                Double lat = Double.parseDouble(latstr);

                OverlayItem item = new OverlayItem(title, description, new GeoPoint(lat, lon));
                items.add(item);
            }
        } catch (IOException e) {
            new AlertDialog.Builder(this).setMessage("Error Loading: " + e).setPositiveButton("Dismiss", null).show();
        }

        return items;
    }
}


