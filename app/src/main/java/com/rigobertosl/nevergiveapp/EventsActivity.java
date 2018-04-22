package com.rigobertosl.nevergiveapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Looper;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;

public class EventsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int MY_PERMISSIONS_REQUEST_CODE = 1;
    private final String[] PERMISSIONS = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET};
    private GoogleMap mMap;
    private GooglePlace myPosition;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private boolean ubicationPermissionEnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ubicationPermissionEnable = checkUbicationPermission();
        if (!ubicationPermissionEnable){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(PERMISSIONS, MY_PERMISSIONS_REQUEST_CODE);
            }
        }else {
            final LocationManager manager = (LocationManager) getSystemService( LocationManager.class );

            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                openDialogNoGPS();
            } else {
                MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);
            }
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        myPosition = new GooglePlace();

        locationManager = (LocationManager)getSystemService(LocationManager.class);
        locationListener = new MyLocationListener();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(PERMISSIONS, MY_PERMISSIONS_REQUEST_CODE);
            }
            return;
        }
        Looper looper = null;
        locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationListener, looper);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        myPosition.setLatitude(location.getLatitude());
        myPosition.setLongitude(location.getLongitude());

        LatLng myLocation = new LatLng(myPosition.getLatitude(), myPosition.getLongitude());
        CameraPosition cameraPosition = new CameraPosition.Builder().target(myLocation).zoom(15).tilt(70).build();
        CameraUpdate cameraUpdate  = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mMap.moveCamera(cameraUpdate);
        mMap.addMarker(new MarkerOptions().position(myLocation));
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
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}

    }

    /** EN CASO DE NO TENER PERMISOS **/
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case MY_PERMISSIONS_REQUEST_CODE: {
                if(grantResults[1] == 0){
                    finish();
                    startActivity(getIntent());
                }else{
                    openDialogUbicationPermissionDenegated();
                }
                break;

            }default:
                break;
        }
    }

    public boolean checkUbicationPermission(){
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void openDialogNoGPS() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(EventsActivity.this);
        final View dialogLayout = getLayoutInflater().inflate(R.layout.popup_gps, null);
        final AlertDialog dialog = builder.create();
        dialog.setView(dialogLayout);
        dialog.show();

        final Button continuar = (Button)dialogLayout.findViewById(R.id.continuar);
        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
                if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    dialog.cancel();
                    finish();
                    startActivity(getIntent());
                } else {
                    startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }
            }
        });
    }

    public void openDialogUbicationPermissionDenegated(){

        final AlertDialog.Builder builder = new AlertDialog.Builder(EventsActivity.this);
        final View dialogLayout = getLayoutInflater().inflate(R.layout.popup_permissions_denegated, null);
        final AlertDialog dialog = builder.create();
        dialog.setView(dialogLayout);
        dialog.show();

        final Button salir = (Button)dialogLayout.findViewById(R.id.salir);
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                returnMain();
            }
        });
    }

    public void returnMain(){
        Intent intent = new Intent(EventsActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
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
        Intent setIntent = new Intent(EventsActivity.this, MainActivity.class);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
        startActivity(setIntent);
    }
}

class GooglePlace {

    private String name;
    private double latitude, longitude;

    public GooglePlace() {
        this.name = "";
        this.latitude = 0;
        this.longitude = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
