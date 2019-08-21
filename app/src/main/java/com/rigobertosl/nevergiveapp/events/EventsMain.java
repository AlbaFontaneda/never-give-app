package com.rigobertosl.nevergiveapp.events;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.rigobertosl.nevergiveapp.LoginActivity;
import com.rigobertosl.nevergiveapp.MainActivity;
import com.rigobertosl.nevergiveapp.ProfileActivity;
import com.rigobertosl.nevergiveapp.R;
import com.rigobertosl.nevergiveapp.firedatabase.AppFiredatabase;

public class EventsMain extends AppFiredatabase {

    public final static int DEFAULT_ZOOM = 15;
    public final static int DEFAULT_TILT = 0;
    public static String PACKAGE_NAME;

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
                Intent intent = new Intent(EventsMain.this, MainActivity.class);
                //Para matar la actividad anterior
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //Para leer la nueva actividad (volver al main)
                startActivity(intent);
            }
        });

        contentView = (FrameLayout) findViewById(R.id.content_view);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);

        BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
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
                        transaction.replace(R.id.content_view, new EventsSearchFragment());
                        transaction.commit();
                        return true;
                }
                return false;
            }
        };
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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
            case R.id.sign_out:
                signOut();
                startNewActivity(this, LoginActivity.class);

        }
        return super.onOptionsItemSelected(item);
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
        Intent setIntent = new Intent(EventsMain.this, MainActivity.class);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
        startActivity(setIntent);
    }
}
