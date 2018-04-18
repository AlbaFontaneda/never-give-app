package com.rigobertosl.nevergiveapp;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class EventsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;
    private GoogleMap mMap;
    private GooglePlace myPosition;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private boolean GPSenabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        myPosition = new GooglePlace();

        configureMyPosition();
    }

    /*---------- Listener class to get coordinates ------------- */
    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            myPosition.setLatitude(location.getLongitude());
            myPosition.setLongitude(location.getLongitude());
        }

        @Override
        public void onProviderDisabled(String provider) {
            Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(i);
        }

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    }

    /** EN CASO DE NO TENER PERMISOS **/
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                configureMyPosition();
                break;

            }default:
                break;
        }
    }

    public void configureMyPosition(){

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[] { Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET }, MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return;
        }

        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locationListener = new MyLocationListener();

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 10, locationListener);
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        /** LA APP PETA SI NO TIENES LA UBICACIÓN PUESTA **/
        /*
        try {
            GPSenabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {

        }

        if (!GPSenabled){
            AlertDialog.Builder dialog = new AlertDialog.Builder(getBaseContext());
            dialog.setMessage("Por favor, conecte el gps de su dispositivo.");
            dialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    getBaseContext().startActivity(myIntent);
                }
            });
            dialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(EventsActivity.this,
                            "No conseguimos determinar su posición.", Toast.LENGTH_LONG).show();
                }
            });
            dialog.show();
        }
        */

        myPosition.setLatitude(location.getLatitude());
        myPosition.setLongitude(location.getLongitude());

        LatLng myLocation = new LatLng(myPosition.getLatitude(), myPosition.getLongitude());

        mMap.addMarker(new MarkerOptions().position(myLocation).title("Marker in myLocation"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
    }
}
