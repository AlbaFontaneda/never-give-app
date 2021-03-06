package com.rigobertosl.nevergiveapp.events.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.rigobertosl.nevergiveapp.events.fragment.IOnBackPressed;
import com.rigobertosl.nevergiveapp.permissions.LocationPermissions;
import com.rigobertosl.nevergiveapp.login.activity.LoginActivity;
import com.rigobertosl.nevergiveapp.main.activity.MainActivity;
import com.rigobertosl.nevergiveapp.login.activity.ProfileActivity;
import com.rigobertosl.nevergiveapp.R;
import com.rigobertosl.nevergiveapp.events.fragment.EventsCreateFragment;
import com.rigobertosl.nevergiveapp.events.fragment.EventsHomeFragment;
import com.rigobertosl.nevergiveapp.events.fragment.EventsSignedFragment;
import com.rigobertosl.nevergiveapp.firedatabase.AppFiredatabase;

public class EventsActivity extends AppFiredatabase {

    /**************************************** Constants *******************************************/
    public final static int DEFAULT_ZOOM = 15;
    public final static int DEFAULT_TILT = 0;
    public static String PACKAGE_NAME;

    /************************************** Permissions *******************************************/
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean mPermissionDenied = false;

    /************************************* User Interface *****************************************/
    private FrameLayout contentView;
    public BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_main);

        PACKAGE_NAME = getApplicationContext().getPackageName();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                onBackPressed();
            }
        });

        contentView = (FrameLayout) findViewById(R.id.content_view);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        transaction.replace(R.id.content_view, new EventsHomeFragment());
                        transaction.commit();
                        return true;
                    case R.id.navigation_dashboard:
                        transaction.replace(R.id.content_view, new EventsCreateFragment());
                        transaction.commit();
                        return true;
                    case R.id.navigation_notifications:
                        transaction.replace(R.id.content_view, new EventsSignedFragment());
                        transaction.commit();
                        return true;
                }
                return false;
            }
        });
        navigation.setSelectedItemId(R.id.navigation_home);
    }

    public void changeFragment(int position){
        if(position == 1){
            navigation.setSelectedItemId(R.id.navigation_home);
        }else if(position == 2){
            navigation.setSelectedItemId(R.id.navigation_dashboard);
        }else if(position == 3){
            navigation.setSelectedItemId(R.id.navigation_notifications);
        }
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){

        switch (item.getItemId()){
            case R.id.edit:
                startNewActivity(this, ProfileActivity.class);
                break;
            case R.id.sign_out:
                signOut();
                startNewActivity(this, LoginActivity.class);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (LocationPermissions.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            //enableMyLocation();
            Toast.makeText(this, "Debe activar el GPS para detectar su ubicación. Puede tardar unos segundos.", Toast.LENGTH_SHORT).show();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        LocationPermissions.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

    /** Sobrescripción del botón de atrás del propio móvil. Puede que el fragmento tenga otra
     * funcionalidad diferente que tiene prioridad sobre la actividad.
     * Código extraido de: Maxime Jallu.
     * Answered Sep 26 '17 at 11:34
     * Edited Dec 14 '18 at 14:49
     * Visitado a día 25/08/2019
     **/
    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_view);
        if(!(fragment instanceof IOnBackPressed) || !((IOnBackPressed) fragment).onBackPressed()){
            Intent setIntent = new Intent(EventsActivity.this, MainActivity.class);
            setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            finish();
            startActivity(setIntent);
        }
    }
}
