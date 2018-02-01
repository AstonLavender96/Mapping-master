package com.example.a2lavea02.mapping;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements OnClickListener{
    MapView mv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mv = (MapView) findViewById(R.id.map1);
        mv.setBuiltInZoomControls(true);

        mv.getController().setZoom(16);

        mv.getController().setCenter(new GeoPoint(51.8361, -0.4577));

        Button b =(Button)findViewById(R.id.btn1);
        b.setOnClickListener(this);
    }
    public void onClick(View view)
    {
        EditText lattext = (EditText)findViewById(R.id.et1);
        EditText longtext = (EditText)findViewById(R.id.et2);
        double latitude = Double.parseDouble(lattext.getText().toString());
        double longitude = Double.parseDouble(longtext.getText().toString());
        mv.getController().setCenter(new GeoPoint(latitude, longitude));

    }

}
