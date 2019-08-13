package com.rigobertosl.nevergiveapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rigobertosl.nevergiveapp.events.EventsMain;
import com.rigobertosl.nevergiveapp.firedatabase.AppFiredatabase;
import com.rigobertosl.nevergiveapp.firedatabase.FiredatabaseInterface;
import com.rigobertosl.nevergiveapp.objects.Event;
import com.rigobertosl.nevergiveapp.objects.Exercise;
import com.rigobertosl.nevergiveapp.objects.LatLong;
import com.rigobertosl.nevergiveapp.objects.Profile;
import com.rigobertosl.nevergiveapp.objects.TrainingTable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppFiredatabase
        implements NavigationView.OnNavigationItemSelectedListener {

    private DataBaseContract db;
    private SectionsPagerAdapter seleccionPagina;
    private ViewPager vistaPagina;

    private static final String DATABASE_NAME = "dbNeverGiveApp.db";
    private static final String PRELOADED_DATABASE_NAME = "preloaded.db";

    private HashMap<String, Event> eventList = new HashMap<>();
    final String TAG = "-------EVENTOS-------";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ////////////////////////////////// FIREDATABASE
/*
        Profile profile1 = new Profile("Javi");
        Profile p1 = new Profile("Alba");
        Profile profile2 = new Profile("javi@javi.com", "Javiivu", "24", "1.85", "86", "imageURL", 666666669, true);
        addDataToFirebase(usersKey, profile1);
        addDataToFirebase(usersKey, p1);
        addDataToFirebase(usersKey, profile2);
        //addDataToFirebase(usersKey, "user1");

        Profile user = new Profile("Blusslightyear");
        addDataToFirebase(usersKey, user);
        ArrayList<Event> eventList = new ArrayList<>();
        //HashMap<String, Event> eventList= new HashMap<String, Event>();

        Event evento1 = new Event("Tenis", "20", "30", "04", "05", "2009", "2", null, new LatLong(40.316877, -3.706114), user );
        Event evento2 = new Event( "Fútbol", "19", "00", "05", "05", "2009", "14", null, new LatLong(40.317572, -3.706876), user);
        Event evento3 = new Event("Pokemon Go", "17","15","08", "08", "2009", "5", null,  new LatLong(40.317703, -3.702198), user);
        Event evento4 = new Event( "Gimnasio","13","20","20", "10", "2009", "3", null,  new LatLong(40.316819, -3.704751), user);
        Event evento5 = new Event("Tirar piedras", "13","00","08", "11", "2009", "3", null,  new LatLong(42.343995, -3.697103), user);
        eventList.add(evento1);
        eventList.add(evento2);
        eventList.add(evento3);
        eventList.add(evento4);
        eventList.add(evento5);

        //For each de un HashMap
        for(Event evento : eventList){
            addDataToFirebase(eventsKey, evento);
        }

        loadEvents();
*/
        ArrayList<Event> sdfs = allEvents;
        ///////////////////////////////////////// -FIREDATABASE

        if(FoodResumeActivity.listKcal == null || FoodResumeActivity.listKcal.size() == 0) {
            new FoodsApi().execute();
        }

        setContentView(R.layout.activity_main);
        db = new DataBaseContract(this);
        db.open();
        if(!db.checkifTrainTableisEmpty()) {
            ArrayList<TrainingTable> allTrainTables = db.getAllTables();
            for (TrainingTable trainTable : allTrainTables) {
                ArrayList<Exercise> tableExercises = db.getAllExercisesFromTable(trainTable);
                if (tableExercises.size() == 0) db.deleteTable(trainTable.getId());
            }
        }

        SharedPreferences mPreferences = this.getSharedPreferences("first_time", Context.MODE_PRIVATE);
        Boolean firstTime = mPreferences.getBoolean("firstTime", true);
        if (firstTime) {
            try {
                String destPath = "/data/data/" + getPackageName() + "/databases/" + DATABASE_NAME;

                System.out.println("Traza: no existe el fichero");
                InputStream in = getAssets().open(PRELOADED_DATABASE_NAME);
                OutputStream out = new FileOutputStream(destPath);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
                in.close();
                out.flush();
                out.close();
                SharedPreferences.Editor editor = mPreferences.edit();
                editor.putBoolean("firstTime", false);
                editor.commit();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        db.close();

        //Finds ID
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout); //Layout para contener en el inicio el appbar y el menu desplegable
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view); //Layout del menu lateral desplegable
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); //Layout donde aparece el nombre de cada activity y las acciones
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs); //Layout donde ponemos los tabs

        setSupportActionBar(toolbar);

        //Funciones para el menu lateral desplegable
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        for (int i = 0; i<7; i++){
            Fragment f = new MainFragment();
            fragments.add(f);
        }

        seleccionPagina = new SectionsPagerAdapter(getSupportFragmentManager(), fragments);

        // Set up the ViewPager with the sections adapter.
        vistaPagina = (ViewPager) findViewById(R.id.container);
        vistaPagina.setAdapter(seleccionPagina);

        vistaPagina.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(vistaPagina));
    }
    /*********** FUNCIONES DE LA PANTALLA DE INICIO ******************/


    /*
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

        FirebaseUser user = mAuth.getCurrentUser();

        if (user == null) {
            signInAnonymously();
        } else  {
            Toast.makeText(this, "Hola, " + user.getUid(), Toast.LENGTH_LONG).show();
        }


    }

    private void signInAnonymously() {
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.d("USER", "signInAnonymously:success " + user.getUid());
                            Toast.makeText(MainActivity.this, "CREANDO A " + user.getUid(), Toast.LENGTH_LONG).show();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e("USER", "signInAnonymously:failure", task.getException());

                        }


                    }
                });
    }

    */

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //Función para dar funcionalidad a cada elemento del menu desplegable de la pantalla de inicio
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_login) {
            startNewActivity(LoginActivity.class);
        } else if (id == R.id.nav_entrenamiento) {
            startNewActivity(TrainingActivity.class);
        } else if (id == R.id.nav_calendario) {
            startNewActivity(FoodsActivity.class);
        } else if (id == R.id.nav_localizacion) {
            startNewActivity(Location.class);
        } else if (id == R.id.nav_eventos) {
            startNewActivity(EventsMain.class);
        } else if (id == R.id.nav_logros) {
            startNewActivity(AchievementsActivity.class);
        } else if (id == R.id.nav_reinicio) {

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final View dialogLayout = getLayoutInflater().inflate(R.layout.popup_alert, null);
            final AlertDialog dialog = builder.create();
            dialog.setView(dialogLayout);
            dialog.show();
            TextView textoAviso = dialogLayout.findViewById(R.id.textoAviso);
            textoAviso.setText(R.string.avisoMain);
            final Button volver = (Button)dialogLayout.findViewById(R.id.button_volver);
            final Button quedarse = (Button)dialogLayout.findViewById(R.id.button_quedarse);

            quedarse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.cancel();
                }
            });

            volver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.open();
                    db.resetDataBase();
                    db.resetAchievements();
                    db.close();
                    finish();
                    startActivity(getIntent());
                    dialog.cancel();
                }
            });
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    public void startNewActivity(Class<?> newActivity){
        Intent intent = new Intent(MainActivity.this, newActivity);
        startActivity(intent);
    }

    /**
     * Transiciones entre tabs y fragmentos
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> fragments;
        public SectionsPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            MainFragment fragment = (MainFragment) fragments.get(position);
            Bundle args = new Bundle();
            args.putInt("page_position", position);
            fragment.setArguments(args);
            return fragment;
        }
        @Override
        public int getCount() {
            return 7;
        }
    }

}