package com.rigobertosl.nevergiveapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;
import java.util.List;

public class EventsMainActivity extends AppCompatActivity implements OnMapReadyCallback {

    FloatingActionButton fab;
    private DataBaseContract db;
    GoogleMap mMap;
    FrameLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_main);

        db = new DataBaseContract(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                onBackPressed();
            }
        });
        layout = findViewById(R.id.map);

        final Button buscarEvento = (Button)findViewById(R.id.button_search_events);
        final Button crearEvento = (Button)findViewById(R.id.button_create_event);
        buscarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent setIntent = new Intent(EventsMainActivity.this, LoginActivity.class);
                setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                finish();
                startActivity(setIntent);
            }
        });
        crearEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent setIntent = new Intent(EventsMainActivity.this, LoginActivity.class);
                setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                finish();
                startActivity(setIntent);
            }
        });

        initializeMap();
    }

    private void initializeMap() {
        if (mMap == null) {
            FragmentManager fm = getSupportFragmentManager();
            SupportMapFragment supportMapFragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(layout.getId(), supportMapFragment).commit();
            if (supportMapFragment != null) {
                supportMapFragment.getMapAsync(EventsMainActivity.this);
            }
            // check if map is created successfully or not
            if (mMap == null) {
                //Toast.makeText(getApplicationContext(), "Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        //if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        //}
        //mMap.setMyLocationEnabled(true);
        List<Marker> markerList = new ArrayList<>();
        Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(40.3208737, -3.7130592)).title("Hello World"));
        markerList.add(marker);

        CameraPosition cameraPosition = new CameraPosition(new LatLng(marker.getPosition().latitude, marker.getPosition().latitude), 17f, 60, 0);
        //mMap.moveCamera(cameraPosition);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 15));
    }

    /** Sobrescripción del botón de atrás del propio móvil
     * Código extraido de: Ekawas.
     * Answered Jun 29 '10 at 16:00.
     * Edited by Arvindh Mani.
     * Edited Aug 10 '16 at 1:23.
     * Visitado a día 11/04/2018
     **/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /** Sobrescripción del botón de atrás del propio móvil
     * Código extraido de: Ekawas.
     * Answered Jun 29 '10 at 16:00.
     * Edited by Arvindh Mani.
     * Edited Aug 10 '16 at 1:23.
     * Visitado a día 11/04/2018
     **/
    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(EventsMainActivity.this, MainActivity.class);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
        startActivity(setIntent);
    }
}
