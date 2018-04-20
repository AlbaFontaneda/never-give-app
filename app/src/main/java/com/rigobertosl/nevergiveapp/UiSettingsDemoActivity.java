/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rigobertosl.nevergiveapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;

/**
 * This shows how UI settings can be toggled.
 */
public class UiSettingsDemoActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int MY_LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final int LOCATION_LAYER_PERMISSION_REQUEST_CODE = 2;
    private GoogleMap mMap;
    private UiSettings mUiSettings;

    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean mLocationPermissionDenied = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_settings_demo);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        mUiSettings = mMap.getUiSettings();

        //--------------------------------------------------------//
        // Pedir permisos primero
        if (!checkReady()) {
            return;
        }
        mUiSettings.setZoomControlsEnabled(true);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mUiSettings.setMyLocationButtonEnabled(true);
        } else {
            // Uncheck the box and request missing location permission.
            PermissionUtils.requestPermission(this, LOCATION_LAYER_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        }

        //--------------------------------------------------------//

        // Keep the UI Settings state in sync with the checkboxes.
        //mMap.setMyLocationEnabled(isChecked(R.id.mylocationlayer_toggle));
    }

    /**
     * Checks if the map is ready (which depends on whether the Google Play services APK is
     * available. This should be called prior to calling any methods on GoogleMap.
     */
    private boolean checkReady() {
        if (mMap == null) {
            Toast.makeText(this, "Cargando mapa...", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        if (requestCode == MY_LOCATION_PERMISSION_REQUEST_CODE) {
            // Enable the My Location button if the permission has been granted.
            if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                mUiSettings.setMyLocationButtonEnabled(true);
            } else {
                mLocationPermissionDenied = true;
            }

        } else if (requestCode == LOCATION_LAYER_PERMISSION_REQUEST_CODE) {
            // Enable the My Location layer if the permission has been granted.
            if (PermissionUtils.isPermissionGranted(permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION)) {
                mMap.setMyLocationEnabled(true);
                //mMyLocationLayerCheckbox.setChecked(true);
            } else {
                mLocationPermissionDenied = true;
            }
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mLocationPermissionDenied) {
            PermissionUtils.PermissionDeniedDialog.newInstance(false).show(getSupportFragmentManager(), "dialog");
            mLocationPermissionDenied = false;
        }
    }
}
