package com.example.a2lavea02.mapping;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import android.content.Intent;

public class SetLocationActivity extends AppCompatActivity implements OnClickListener {
    MapView mv;
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_location);

        Button setlocation = (Button) findViewById(R.id.btnSetLoc);
        setlocation.setOnClickListener(this);
    }
    public void onClick(View view)
    {
        EditText setlattext = (EditText)findViewById(R.id.sl1);
        EditText setlontext = (EditText)findViewById(R.id.sl2);
        double latitude = Double.parseDouble(setlattext.getText().toString());
        double longitude = Double.parseDouble(setlontext.getText().toString());

        Intent intent = new Intent();
        Bundle bundle = new Bundle();

        bundle.putDouble("com.example.a2lavea02.mapping.setlat", latitude);
        bundle.putDouble("com.example.a2lavea02.mapping.setlon", longitude);
        intent.putExtras(bundle);

        setResult(RESULT_OK, intent);
        //mv.getController().setCenter(new GeoPoint(latitude, longitude));
        finish();
    }
}

